package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Veit on 06.02.2016.
 */
public class SkeletonControlledObject extends MoveableObject {
    private static final int STEPS = 500;
    public Type collectedType;
    protected boolean moveUp, moveDown, moveLeft, moveRight;
    protected Animation movingUpAnimation;
    protected Animation movingDownAnimation;
    protected Animation movingRightAnimation;
    protected Animation movingLeftAnimation;
    World world;
    //    private TextureRegion[] regions = new TextureRegion[12];
    private Vector3 touchCoordinates = new Vector3(0, 0, 0);
    private int heading; // 1 - UP, 2 - Right, 3 - Down, 4 - Left
    private TextureRegion frame;
    private float movementCounter;
    private int stepCounter, maxSteps;

    public SkeletonControlledObject(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
        this.world = world;

        this.maxSteps = STEPS;
        this.speed = 10f;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().
                loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingRightAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingLeftAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
//        this.texture = world.gameplayScreen.parentGame.getAssMan().get("gameplay/spritesheet.png");
//        for (int i = 0; i<3; i++){
//            for (int j = 0; j<4; j++){
//                regions[i+(j*3)]= new TextureRegion(texture, i*46, j*64, 46, 64);
//            }
//        }

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput();
        handleMovement(delta);
    }

    @Override
    void handleMovement(Float delta) {
        calcDirection();
//        this.position.set(bounds.getPosition(new Vector2()).nor().scl(speed));
        Vector2 newPosition = new Vector2(this.position).add(direction.nor().scl(speed));
        bounds.setPosition(newPosition);
        for (int i = 0; i < world.walls.length; i++) {
            for (int j = 0; j < world.walls[0].length; j++) {
                if (world.walls[i][j] != null && world.walls[i][j].overlaps(this.bounds)) {
                    bounds.setPosition(this.position);
                    return;
                }
            }
        }

        this.position.add(direction.nor().scl(speed));
        if (!direction.nor().isZero()) {
            if (direction.x > 0) {
                heading = 2;
            } else if (direction.x < 0) {
                heading = 4;
            }
            if (direction.y > 0) {
                heading = 1;
            } else if (direction.y < 0) {
                heading = 3;
            }
            movement = Movement.MOVING;
            movementCounter += MathUtils.random(1f);
            stepCounter += 1;
            checkRandomEncounter();
            checkToothHit();
            checkBossFight();
            checkGameFinish();
        } else {
            movement = Movement.IDLE;
        }
    }

    private void checkBossFight() {
        if (!world.boss.isDead() && world.whiteTeethCount == world.maxWhite && world.yellowTeethCount == world.maxYellow) {
            float dst = position.dst(world.boss.position);
            System.out.println("Distance: " + dst);
            if (dst < 180) {
                Array<Integer> boss = new Array<Integer>();
                boss.add(F_Enemy.NONE);
                boss.add(F_Enemy.NONE);
                boss.add(F_Enemy.BOSS);
                world.gameplayScreen.parentGame.getScreenManager().encounter(boss);
            }
        }
    }

    protected void checkToothHit() {
        Iterator<GameObject> iterator = world.gameObjects.iterator();
        GameObject next;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (this.collectedType == next.getType() && next.bounds.overlaps(this.bounds)) {
                iterator.remove();
                world.addTooth(next.getType());
                return;
            }
        }
    }

    private void checkRandomEncounter() {
        if ((int) movementCounter == (int) MathUtils.random((float) maxSteps)) {
            movementCounter = 0;
            maxSteps = STEPS;
            Array<Integer> enemies = createRandomeEncounterMobGroup();
            world.gameplayScreen.parentGame.getScreenManager().encounter(enemies);
        } else if (stepCounter >= maxSteps) {
            stepCounter = 0;
            movementCounter = 0;
            maxSteps -= 100;
        }
    }

    private void checkGameFinish() {
        if (world.goal.state == Goal.STATE_ACTIVE && world.goal.bounds.overlaps(this.bounds)) {
            world.finishGame();
        }
    }

    private Array<Integer> createRandomeEncounterMobGroup() {
        Array<Integer> mobs = new Array<Integer>();
        int numMobs = MathUtils.random(1, 3);
        for (int i = 0; i < numMobs; i++) {
            mobs.add(getRandomMob());
        }
        return mobs;
    }

    private Integer getRandomMob() {
        List<Integer> allMobs = new ArrayList<Integer>();
        allMobs.add(F_Enemy.Bitch);
        allMobs.add(F_Enemy.UNICORN);
        allMobs.add(F_Enemy.PIXIE);
        return allMobs.get(MathUtils.random(0, 2));
    }

    public void clipCollision(Rectangle bounds, Vector2 movement) {
        Rectangle newbounds = new Rectangle(bounds.x + movement.x, bounds.y + movement.y, bounds.width, bounds.height);

        int sx, sy, ex, ey, ux, uy;
        if (movement.x > 0) {
            sx = (int) Math.floor(bounds.x);
            ex = (int) Math.ceil(newbounds.x + bounds.width) + 1;
        } else {
            sx = (int) Math.ceil(bounds.x + bounds.width);
            ex = (int) Math.floor(newbounds.x) - 1;
        }

        if (movement.y > 0) {
            sy = (int) Math.floor(bounds.y);
            ey = (int) Math.ceil(newbounds.y + bounds.height) + 1;
        } else {
            sy = (int) Math.ceil(bounds.y + bounds.height);
            ey = (int) Math.floor(newbounds.y) - 1;
        }

        Color c = new Color(0, 0, 1, 1);
        boolean displayDebug = false;

        sx = Math.max(Math.min(sx, world.walls.length - 1), 0);
        sy = Math.max(Math.min(sy, world.walls[0].length - 1), 0);
        ex = Math.max(Math.min(ex, world.walls.length), -1);
        ey = Math.max(Math.min(ey, world.walls[0].length), -1);
        ux = ex - sx > 0 ? 1 : -1;
        uy = ey - sy > 0 ? 1 : -1;

        for (int x = sx; x != ex; x += ux) {
            for (int y = sy; y != ey; y += uy) {
                Rectangle r = world.walls[x][y];
                if (r != null) {
                    if (r.overlaps(newbounds)) {
                        float x1, x2, y1, y2;

                        if (movement.x > 0) {
                            x1 = bounds.x + bounds.width;
                            x2 = r.x;
                        } else {
                            x1 = bounds.x;
                            x2 = r.x + r.width;
                        }

                        if (movement.y > 0) {
                            y1 = bounds.y + bounds.height;
                            y2 = r.y;
                        } else {
                            y1 = bounds.y;
                            y2 = r.y + r.height;
                        }

                        float d1 = (x2 - x1) / movement.x;
                        float d2 = (y2 - y1) / movement.y;

                        if (d1 >= 0 && d1 <= 1) {
                            // collision in x direction
                            movement.x = 0;
                        }

                        if (d2 >= 0 && d2 <= 1) {
                            // collision in y direction
                            movement.y = 0;
                        }

                        newbounds.x = bounds.x + movement.x;
                        newbounds.y = bounds.y + movement.y;
                    }
                }
            }
        }
        this.bounds = newbounds;
    }


    /**
     * Calculates the direction Vector
     */
    void calcDirection() {
        direction = new Vector2(0, 0);
        if (moveUp && !moveDown) {
            direction.y = 1;
        } else if (!moveUp && moveDown) {
            direction.y = -1;
        }

        if (moveLeft && !moveRight) {
            direction.x = -1;
        } else if (!moveLeft && moveRight) {
            direction.x = 1;
        }
        moveDown = moveUp = moveRight = moveLeft = false;
    }

    void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight = true;
        }

        if (Gdx.input.justTouched()) {
//            touch(world.gameplayScreen.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1)));
        }
    }

    /**
     * Your typical render function
     *
     * @param delta
     * @param spriteBatch heading must be set accordingly: 1 - UP, 2 - Right, 3 - Down, 4 - Left
     */
    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        switch (heading) {
            case 1:
                frame = movingUpAnimation.getKeyFrame(stateTime, true);
                break;
            case 2:
                frame = movingRightAnimation.getKeyFrame(stateTime, true);
                break;
            case 3:
                frame = movingDownAnimation.getKeyFrame(stateTime, true);
                break;
            case 4:
                frame = movingLeftAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                frame = idleAnimation.getKeyFrame(stateTime, true);
        }
        spriteBatch.draw(frame, position.x - dimension.x / 2f, position.y - dimension.y / 2f);
    }

    public void touch(Vector3 touchCoordinates) {
        this.touchCoordinates = touchCoordinates;
    }
}
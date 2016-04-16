package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Veit on 06.02.2016.
 */
public class SkeletonControlledObject extends MoveableObject {
    protected boolean moveUp, moveDown, moveLeft, moveRight;
    //    private TextureRegion[] regions = new TextureRegion[12];
    private Vector3 touchCoordinates = new Vector3(0, 0, 0);
    private int heading; // 1 - UP, 2 - Right, 3 - Down, 4 - Left
    private Animation idleAnimation;
    private Animation movingUpAnimation;
    private Animation movingDownAnimation;
    private Animation movingSideAnimation;
    private TextureRegion frame;
    private World world;

    public SkeletonControlledObject(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
        this.world = world;

        this.speed = 10f;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().
                loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
        this.movingSideAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/movingAnimation_Down.png", 0.3f, 45, 64);
//        this.texture = world.gameplayScreen.parentGame.getAssetManager().get("gameplay/spritesheet.png");
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
        } else {
            movement = Movement.IDLE;
        }
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
                frame = movingUpAnimation.getKeyFrame(movingTime, true);
                break;
            case 2:
                frame = movingSideAnimation.getKeyFrame(movingTime, true);
                break;
            case 3:
                frame = movingDownAnimation.getKeyFrame(movingTime, true);
                break;
            case 4:
                frame = movingSideAnimation.getKeyFrame(movingTime, true);
                break;
            default:
                frame = idleAnimation.getKeyFrame(movingTime, true);
        }
        spriteBatch.draw(frame, position.x - dimension.x / 2f, position.y - dimension.y / 2f);
    }

    public void touch(Vector3 touchCoordinates) {
        this.touchCoordinates = touchCoordinates;
    }
}
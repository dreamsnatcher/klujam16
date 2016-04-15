package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by lschmoli on 4/15/16.
 */
public class DPlayerOne extends SkeletonControlledObject {

    public DPlayerOne(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension, world);
        initPhysics(world.b2dWorld);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        b2Body.setTransform(position, 0);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);
    }

    protected void initPhysics(com.badlogic.gdx.physics.box2d.World b2dWorld) {

        //create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);

        //create body in world
        b2Body = b2dWorld.createBody(bodyDef);

        //create shape
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(dimension.x / 2);

        //create fixture to attach shape to body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        b2Body.createFixture(fixtureDef);
//        b2Body.setLinearDamping(0.5f);
//        b2Body.setAngularDamping(1f);
//        b2Body.setBullet(true);
//        b2Body.setSleepingAllowed(false);

        circleShape.dispose(); //clean up!!
        b2Body.setUserData(this);
    }

    @Override
    void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveDown = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveUp = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveLeft = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveRight = true;
        }
    }
}
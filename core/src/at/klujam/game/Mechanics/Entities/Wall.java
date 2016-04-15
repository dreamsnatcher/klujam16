package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Wall extends GameObject {
    public Wall(Vector2 position, Vector2 dimension, World world) {
        super(position,dimension, world);
        initPhysics(world.b2dWorld);
    }

    private void initPhysics(com.badlogic.gdx.physics.box2d.World b2dWorld) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(position.x, position.y));
        bodyDef.type = BodyDef.BodyType.StaticBody;

        b2Body = b2dWorld.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(new Vector2(0, 1), new Vector2(dimension.x, 0));

        edgeShape.setVertex0(new Vector2(-dimension.x, dimension.y));
        edgeShape.setVertex3(new Vector2(2 * dimension.x, dimension.y));
        edgeShape.setHasVertex0(true);
        edgeShape.setHasVertex3(true);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        fixtureDef.friction = 1f;

        b2Body.createFixture(fixtureDef);

    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {

    }
}

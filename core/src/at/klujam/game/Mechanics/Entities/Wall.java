package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Wall extends GameObject {

    public Wall(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {

    }
}

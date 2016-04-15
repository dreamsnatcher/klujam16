package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/15/16.
 */
public class DPlayerTwo extends DPlayerOne {

    public DPlayerTwo(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension, world);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight = true;
        }
    }
}

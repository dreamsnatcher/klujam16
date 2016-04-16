package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/15/16.
 */
public class DPlayerOne extends SkeletonControlledObject {

    public static final int SPEED = 50;

    public DPlayerOne(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension, world);
        bounds = new Rectangle(position.x, position.y, scale.x * Constants.TILE_SIZE, scale.y * Constants.TILE_SIZE);
        type = Type.Player;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);
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
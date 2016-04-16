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
public class DPlayerTwo extends DPlayerOne {

    public DPlayerTwo(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension, world);
        bounds = new Rectangle(position.x, position.y, scale.x * Constants.TILE_SIZE * 0.75f, scale.y * 0.8f * Constants.TILE_SIZE);
        type = Type.Player;
        this.speed = 5f;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/gobo_2_00", 5, 0.3f);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().
                loadAnimation("gameplay/gobo_1_back_00", 6, 0.3f);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/gobo_2_00", 5, 0.2f);
        this.movingSideAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/gobo_1_right_00", 5, 0.3f);
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

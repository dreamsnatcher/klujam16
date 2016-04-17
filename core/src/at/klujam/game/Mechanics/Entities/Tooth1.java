package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/17/16.
 */
public class Tooth1 extends GameObject {

    public Tooth1(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);

        bounds = new Rectangle(position.x, position.y, scale.x * 0.5f * Constants.TILE_SIZE, scale.y * 0.7f * Constants.TILE_SIZE);
        type = Type.Tooth1;

        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/tooth_0_00", 4, 0.3f);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);
    }
}

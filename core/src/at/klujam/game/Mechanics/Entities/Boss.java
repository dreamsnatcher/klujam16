package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/17/16.
 */
public class Boss extends GameObject {

    public Boss(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
        bounds = new Rectangle(position.x, position.y, scale.x * Constants.TILE_SIZE, scale.y * Constants.TILE_SIZE);
        type = Type.Boss;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/tooth_fairy_small_00", 6, 0.3f);
    }
}

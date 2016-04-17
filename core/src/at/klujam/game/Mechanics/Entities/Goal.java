package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/17/16.
 */
public class Goal extends GameObject {

    public Goal(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
        bounds = new Rectangle(position.x, position.y, scale.x * Constants.TILE_SIZE, scale.y * Constants.TILE_SIZE);
        type = Type.Goal;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/portal_00", 7, 0.3f);
    }
}

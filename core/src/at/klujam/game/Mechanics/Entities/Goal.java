package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/17/16.
 */
public class Goal extends GameObject {

    public static final int STATE_IDLE = 0;
    public static final int STATE_ACTIVE = 1;

    World world;
    public int state;

    public Goal(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
        this.world = world;
        bounds = new Rectangle(position.x, position.y, scale.x * Constants.TILE_SIZE, scale.y * Constants.TILE_SIZE);
        type = Type.Goal;
        setState(STATE_IDLE);
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/portal_00", 7, 0.3f);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        if (state == STATE_ACTIVE) {
            tr = idleAnimation.getKeyFrame(stateTime, true);
        }
        spriteBatch.draw(tr, position.x - dimension.x / 2f, position.y - dimension.y / 2f);
    }

    public void setState(int state) {
        this.state = state;
        if (state == STATE_IDLE) {
            tr = new TextureRegion(world.gameplayScreen.parentGame.getAssMan().get("gameplay/portal_inactive.png", Texture.class));
        }
    }
}

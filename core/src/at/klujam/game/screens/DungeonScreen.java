package at.klujam.game.screens;

import at.klujam.game.Game;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by lknoch on 15.04.16.
 */
public class DungeonScreen extends GameplayScreen {
    private final Box2DDebugRenderer debugRenderer;

    public DungeonScreen(Game game) {
        super(game);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(world!=null) {
            debugRenderer.render(world.b2dWorld, cam.combined);
        }
    }
}

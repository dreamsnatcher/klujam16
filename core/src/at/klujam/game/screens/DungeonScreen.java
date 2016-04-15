package at.klujam.game.screens;

import at.klujam.game.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by lknoch on 15.04.16.
 */
public class DungeonScreen extends GameplayScreen {
    private final Box2DDebugRenderer debugRenderer;
    final OrthographicCamera b2Cam;


    public DungeonScreen(Game game) {
        super(game);
        debugRenderer = new Box2DDebugRenderer();
        b2Cam = new OrthographicCamera(Game.GAME_WIDTH * Game.SCALING_TO_B2_WORLD, Game.GAME_HEIGHT * Game.SCALING_TO_B2_WORLD);

        b2Cam.position.set(b2Cam.viewportWidth / 2f, b2Cam.viewportHeight / 2f, 0);
        b2Cam.update();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        b2Cam.update();
        debugRenderer.render(world.b2dWorld, b2Cam.combined);
    }
}

package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.util.Collisions;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by lknoch on 15.04.16.
 */
public class DungeonScreen extends GameplayScreen {
    final OrthographicCamera b2Cam;
    private final Box2DDebugRenderer debugRenderer;
    float unitScale;
    private TiledMap tMap;
    private OrthogonalTiledMapRenderer tMapRenderer;
    private String level;

    public DungeonScreen(Game game, String level) {
        this(game);
        this.level = level;
        this.tMap = (new TmxMapLoader()).load("level/" + level + ".tmx");
        this.tMapRenderer = new OrthogonalTiledMapRenderer(this.tMap, unitScale, this.batch);

        Collisions.create(world, tMap, unitScale);
    }

    public DungeonScreen(Game game) {
        super(game);
        debugRenderer = new Box2DDebugRenderer();
        unitScale = 1f / Constants.TILE_SIZE;
        b2Cam = new OrthographicCamera(Game.GAME_WIDTH * Game.SCALING_TO_B2_WORLD, Game.GAME_HEIGHT * Game.SCALING_TO_B2_WORLD);
        b2Cam.position.set(b2Cam.viewportWidth / 2f, b2Cam.viewportHeight / 2f, 0);
        b2Cam.update();
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        b2Cam.update();
        debugRenderer.render(world.b2dWorld, b2Cam.combined);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        b2Cam.viewportWidth = ((Game.GAME_HEIGHT / (float) height) * width) * Game.SCALING_TO_B2_WORLD; //calculate aspect ratio
        b2Cam.update();
    }
}

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
    float unitScale;
    private TiledMap tMap;
    private String level;

    public DungeonScreen(Game game, String level) {
        this(game);
        this.level = level;
        this.tMap = (new TmxMapLoader()).load("level/" + level + ".tmx");

        Collisions.create(world, tMap, unitScale);
    }

    public DungeonScreen(Game game) {
        super(game);
        unitScale = 1f / Constants.TILE_SIZE;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}

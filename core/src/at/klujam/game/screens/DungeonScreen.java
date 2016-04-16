package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.util.Constants;
import at.klujam.game.util.GameObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by lknoch on 15.04.16.
 */
public class DungeonScreen extends GameplayScreen {
    float unitScale;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMap tMap;
    private String level;

    public DungeonScreen(Game game, String level) {
        this(game);
        this.level = level;
        this.tMap = (new TmxMapLoader()).load("level/" + level + ".tmx");
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tMap, unitScale, guiBatch);
        GameObjects.create(world, tMap, unitScale);
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

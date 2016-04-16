package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.util.Constants;
import at.klujam.game.util.GameObjects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by lknoch on 15.04.16.
 */
public class DungeonScreen extends GameplayScreen {
    float unitScale;
    OrthogonalTiledMapRenderer tMapRenderer;
    TiledMap tMap;
    private String level;
    private int LAYER_FLOOR;

    public DungeonScreen(Game game, String level) {
        this(game);
        this.level = level;

        this.tMap = new TmxMapLoader().load("level/" + level + ".tmx");
        this.tMapRenderer = new OrthogonalTiledMapRenderer(tMap);
        this.tMapRenderer.setView(cam);
        // figure out which layer has which id, idiotic
        for (int i = 0; i < tMap.getLayers().getCount(); i++) {
            MapLayer layer = tMap.getLayers().get(i);
            if (layer.getName().equals("Kachelebene 1")) {
                LAYER_FLOOR = i;
                break;
            }
        }
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

    @Override
    protected void renderTiles() {
        // render tiles
        tMapRenderer.setView(cam);
        tMapRenderer.render(new int[] {LAYER_FLOOR});
    }
}

package at.klujam.game.util;

import at.klujam.game.Mechanics.Entities.Wall;
import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/16/16.
 */
public final class Collisions {

    public static void create(World world, TiledMap tMap, float unitScale) {
        // load collision map
        TiledMapTileLayer layer = (TiledMapTileLayer) tMap.getLayers().get("col");
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) {
                    world.addGameObject(new Wall(new Vector2(x, y).scl(unitScale), new Vector2(1,1), world));
                }
            }
        }
    }
}

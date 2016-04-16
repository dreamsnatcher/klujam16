package at.klujam.game.util;

import at.klujam.game.Mechanics.Entities.DPlayerOne;
import at.klujam.game.Mechanics.Entities.DPlayerTwo;
import at.klujam.game.Mechanics.Entities.Tooth1;
import at.klujam.game.Mechanics.Entities.Tooth2;
import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lschmoli on 4/16/16.
 */
public final class GameObjects {

    public static void create(World world, TiledMap tMap, float unitScale) {
        // load objects from map
        MapObjects objects = tMap.getLayers().get("objects").getObjects();
        MapProperties playerProps = objects.get("player").getProperties();
//        MapProperties goalProps = objects.get("goal").getProperties();
        DPlayerOne player = new DPlayerOne(new Vector2(playerProps.get("x", Float.class), playerProps.get("y", Float.class)), new Vector2(1, 1), world);
        world.setPlayer(player);
        DPlayerTwo playerTwo = new DPlayerTwo(new Vector2(player.position).add(0, -50), new Vector2(1, 1), world);
        world.setPlayerTwo(playerTwo);
//        Goal goal = new Goal(new Vector2(goalProps.get("x", Float.class) / TILE_SIZE, goalProps.get("y", Float.class)  / TILE_SIZE));
//        entities.add(goal);

        // load collision map
        TiledMapTileLayer layer = (TiledMapTileLayer) tMap.getLayers().get("walls");
        world.walls = new Rectangle[layer.getWidth()][layer.getHeight()];
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) {
                    world.walls[x][y] = new Rectangle(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
//                    world.addGameObject(new Wall(new Vector2(x, y).scl(Constants.TILE_SIZE), new Vector2(1, 1).scl(unitScale), world));
                }
            }
        }

        // create objects
        MapProperties object;
        for (int i = 0; i < objects.getCount(); i++) {
            object = objects.get(i).getProperties();
            String type = object.get("type", String.class);
            if (type.equals("tooth1")) {
                Tooth1 tooth1 = new Tooth1(new Vector2(object.get("x", Float.class), object.get("y", Float.class)), new Vector2(1, 1), world);
                world.addGameObject(tooth1);
            } else if (type.equals("tooth2")) {
                Tooth2 tooth2 = new Tooth2(new Vector2(object.get("x", Float.class), object.get("y", Float.class)), new Vector2(1, 1), world);
                world.addGameObject(tooth2);
            }
        }
    }
}

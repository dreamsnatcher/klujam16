package at.klujam.game.util;

import at.klujam.game.Mechanics.Entities.*;
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
        MapProperties props = objects.get("player").getProperties();
//        MapProperties goalProps = objects.get("goal").getProperties();
        DPlayerOne player = new DPlayerOne(new Vector2(props.get("x", Float.class), props.get("y", Float.class)), new Vector2(1, 1), world);
        world.setPlayer(player);
        DPlayerTwo playerTwo = new DPlayerTwo(new Vector2(player.position).add(0, -50f), new Vector2(1, 1), world);
        world.setPlayerTwo(playerTwo);
        props = objects.get("goal").getProperties();
        Goal goal = new Goal(new Vector2(props.get("x", Float.class), props.get("y", Float.class)), new Vector2(1, 1), world);
        world.setGoal(goal);
        Boss boss = new Boss(new Vector2(goal.position.x, goal.position.y - 100f), new Vector2(1, 1), world);
        world.setBoss(boss);

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
                world.maxWhite += 1;
            } else if (type.equals("tooth2")) {
                Tooth2 tooth2 = new Tooth2(new Vector2(object.get("x", Float.class), object.get("y", Float.class)), new Vector2(1, 1), world);
                world.addGameObject(tooth2);
                world.maxYellow += 1;
            }
        }
    }
}

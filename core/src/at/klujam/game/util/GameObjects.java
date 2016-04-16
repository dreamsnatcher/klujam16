package at.klujam.game.util;

import at.klujam.game.Mechanics.Entities.DPlayerOne;
import at.klujam.game.Mechanics.Entities.Wall;
import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
        world.addGameObject(player);
//        Goal goal = new Goal(new Vector2(goalProps.get("x", Float.class) / TILE_SIZE, goalProps.get("y", Float.class)  / TILE_SIZE));
//        entities.add(goal);

        // load collision map
        TiledMapTileLayer layer = (TiledMapTileLayer) tMap.getLayers().get("walls");
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                if (layer.getCell(x, y) != null) {
                    world.addGameObject(new Wall(new Vector2(x ,y).scl(Constants.TILE_SIZE) , new Vector2(1, 1).scl(unitScale), world));
                }
            }
        }

        // create objects
//        for (int i = 0; i < objects.getCount(); i++) {
//            MapProperties object = objects.get(i).getProperties();
//            String type = object.get("type", String.class);
//            if (type.equals("enemy1")) {
//                Enemy enemy = new Enemy(object.get("x", Float.class), object.get("y", Float.class));
//                enemy.position.scl(unitScale);
//                entities.add(enemy);
//                enemies.add(enemy);
//            } else if (type.equals("enemy2")) {
//                Enemy enemy = new Enemy2(object.get("x", Float.class), object.get("y", Float.class));
//                enemy.position.scl(unitScale);
//                entities.add(enemy);
//                enemies.add(enemy);
//            } else if (type.equals("pille")) {
//                Pill pill = new Pill(object.get("x", Float.class), object.get("y", Float.class));
//                pill.position.scl(unitScale);
//                pill.bounds.x /= Constants.TILE_SIZE;
//                pill.bounds.y /= Constants.TILE_SIZE;
//                entities.add(pill);
//            } else if (type.equals("axe")) {
//                Axe axe = new Axe(object.get("x", Float.class), object.get("y", Float.class));
//                axe.position.scl(1f / TILE_SIZE);
//                axe.bounds.x /= TILE_SIZE;
//                axe.bounds.y /= TILE_SIZE;
//                entities.add(axe);
//            } else if (type.equals("switch")) {
//                Switch doorswitch = new Switch(object.get("x", Float.class), object.get("y", Float.class));
//                doorswitch.position.scl(1f / TILE_SIZE);
//                doorswitch.name = object.get("name", String.class);
//                entities.add(doorswitch);
//            } else if (type.equals("door_h")) {
//                Door door = new Door(object.get("x", Float.class), object.get("y", Float.class));
//                door.switchname = object.get("switch", String.class);
//                door.position.scl(1f / TILE_SIZE);
//                door.position.x = (float) Math.floor(door.position.x + 0.5f);
//                door.position.y = (float) Math.floor(door.position.y + 0.5f);
//                door.bounds.x = door.position.x;
//                door.bounds.y = door.position.y;
//                door.bounds.width = 1;
//                door.bounds.height = 2;
//                entities.add(door);
//                walls[(int) door.position.x][(int) door.position.y] = new Rectangle(door.position.x, door.position.y, 1, 1);
//            }
//            if (type.equals("door_v")) {
//                DoorVertical door = new DoorVertical(object.get("x", Float.class), object.get("y", Float.class));
//                door.position.scl(1f / TILE_SIZE);
//                door.position.x = (float) Math.floor(door.position.x + 0.5f);
//                door.position.y = (float) Math.floor(door.position.y + 0.5f);
//                door.bounds.x = door.position.x;
//                door.bounds.y = door.position.y;
//                door.bounds.width = 1;
//                door.bounds.height = 1;
//                entities.add(door);
//                walls[(int) door.position.x][(int) door.position.y] = new Rectangle(door.position.x, door.position.y, 1, 1);
//            }
//        }
    }
}

package at.klujam.game.Mechanics;

import at.klujam.game.Mechanics.Entities.DPlayerOne;
import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.Mechanics.Entities.SkeletonControlledObject;
import at.klujam.game.Mechanics.Entities.Wall;
import at.klujam.game.screens.GameplayScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Veit on 06.02.2016.
 */
public class World {

    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    SkeletonControlledObject player;
    private SpriteBatch spriteBatch;

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;

        //Add SkeletonControlledObject
        player = new DPlayerOne(new Vector2(0f, 0f), new Vector2(10, 10), this);
        gameObjects.add(player);
        gameObjects.add(new Wall(new Vector2(20, 20), new Vector2(6.4f, 6.4f), this));

    }

    public void update(float delta) {
        for (GameObject go : gameObjects) {
            go.update(delta);
        }
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (GameObject go : gameObjects) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();
    }

    public void addGameObject(GameObject object) {
        this.gameObjects.add(object);
    }

    public void touch(Vector3 touchCoords) {
        player.touch(touchCoords);
    }
}

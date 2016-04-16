package at.klujam.game.Mechanics;

import at.klujam.game.Mechanics.Entities.DPlayerOne;
import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.screens.GameplayScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Veit on 06.02.2016.
 */
public class World {

    public static final float CAM_DAMP = 4;

    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public DPlayerOne player;
    private SpriteBatch spriteBatch;

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;
    }

    public void update(float delta) {
        System.out.println("Gameobjects: " + gameObjects.size);
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

    public void setPlayer(DPlayerOne player) {
        this.player = player;
    }
}

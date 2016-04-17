package at.klujam.game.Mechanics;

import at.klujam.game.Mechanics.Entities.DPlayerOne;
import at.klujam.game.Mechanics.Entities.DPlayerTwo;
import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.Mechanics.Entities.Goal;
import at.klujam.game.screens.GameplayScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Veit on 06.02.2016.
 */
public class World {

    public static final float CAM_DAMP = 4;

    public Rectangle[][] walls;
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public DPlayerOne player;
    public DPlayerTwo playerTwo;
    public int maxWhite, maxYellow;
    public int whiteTeethCount;
    public int yellowTeethCount;
    public Goal goal;

    public World(GameplayScreen gameplayScreen) {
        this.gameplayScreen = gameplayScreen;
        this.gameObjects = new Array<GameObject>();
    }

    public void update(float delta) {
        player.update(delta);
        playerTwo.update(delta);
        for (GameObject go : gameObjects) {
            go.update(delta);
        }
        goal.update(delta);
    }

    public void render(float delta, SpriteBatch batch) {
        batch.begin();
        player.render(delta, batch);
        playerTwo.render(delta, batch);
        for (GameObject go : gameObjects) {
            go.render(delta, batch);
        }
        goal.render(delta, batch);
        batch.end();
    }

    public void addTooth(GameObject.Type type) {
        if (type == GameObject.Type.Tooth1) {
            whiteTeethCount += 1;
        } else if (type == GameObject.Type.Tooth2) {
            yellowTeethCount += 1;
        }
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


    public void setPlayerTwo(DPlayerTwo playerTwo) {
        this.playerTwo = playerTwo;
    }

    public synchronized int getNumTooth() {
        synchronized (this) {
            return whiteTeethCount;
        }
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}

package at.klujam.game.Mechanics;

import at.klujam.game.Mechanics.Entities.*;
import at.klujam.game.ScreenManager;
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
    public Boss boss;

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
        if (!boss.isDead() && whiteTeethCount == maxWhite && yellowTeethCount == maxYellow) {
            boss.update(delta);
        }
    }

    public void render(float delta, SpriteBatch batch) {
        batch.begin();
        player.render(delta, batch);
        playerTwo.render(delta, batch);
        for (GameObject go : gameObjects) {
            go.render(delta, batch);
        }
        goal.render(delta, batch);
        if (!boss.isDead() && whiteTeethCount == maxWhite && yellowTeethCount == maxYellow) {
            boss.render(delta, batch);
        }
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

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public void finishGame() {
        gameplayScreen.parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
        gameplayScreen.parentGame.getSoundManager().playSpeech("GameWon");
    }
}

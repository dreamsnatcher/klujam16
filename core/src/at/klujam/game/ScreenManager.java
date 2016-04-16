package at.klujam.game;

import at.klujam.game.screens.*;
import com.badlogic.gdx.Screen;

/**
 * Created by Mathias Lux, mathias@juggle.at, on 04.02.2016.
 */
public class ScreenManager {
    private Screen currentScreen, lastScreen;
    private ScreenState currentState, lastState;
    private Game parentGame;

    public ScreenManager(Game game) {
        this.parentGame = game;
        currentScreen = new LoadingScreen(game);
        currentState = ScreenState.Loading;
        lastState = ScreenState.None;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public ScreenState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ScreenState state) {
        if (state != currentState) { // only if state changes.
            currentState = state;
            if (state == ScreenState.Menu) {
                currentScreen = new MenuScreen(parentGame);
            } else if (state == ScreenState.Credits) {
                currentScreen = new CreditsScreen(parentGame);
            } else if (state == ScreenState.Game) {
                parentGame.getSoundManager().playMusic("song1");
                currentScreen = new DungeonScreen(parentGame, "Dungeon-new");
                //currentScreen = new GameplayScreen(parentGame);
            } else if (state == ScreenState.Fighting) {
                currentScreen = new FightingSceneScreen(parentGame);
            } else if (state == ScreenState.Intro) {
                currentScreen = new IntroScreen(parentGame);
            }
        }
    }

    public void changeScreen(ScreenState newState) {
        if (lastState != ScreenState.None && lastState == newState) {
            currentScreen = lastScreen;
            currentState = newState;
            lastState = ScreenState.None;
            lastScreen.dispose();
        } else {
            lastState = currentState;
            lastScreen = currentScreen;
            setCurrentState(newState);
        }
    }

    public Game getParentGame() {
        return parentGame;
    }

    public void setParentGame(Game parentGame) {
        this.parentGame = parentGame;
    }

    public enum ScreenState {None, Loading, Menu, Game, Credits, Fighting, Intro, Help, GameOver}
}

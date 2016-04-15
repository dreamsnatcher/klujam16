package at.klujam.game;

import com.badlogic.gdx.Screen;

import at.klujam.game.screens.CreditsScreen;
import at.klujam.game.screens.GameplayScreen;
import at.klujam.game.screens.LoadingScreen;
import at.klujam.game.screens.MenuScreen;

/**
 * Created by Mathias Lux, mathias@juggle.at, on 04.02.2016.
 */
public class ScreenManager {
    public enum ScreenState {Loading, Menu, Game, Credits, Help, GameOver};
    private Screen currentScreen;
    private ScreenState currentState;
    private Game parentGame;

    public ScreenManager(Game game) {
        this.parentGame = game;
        currentScreen = new LoadingScreen(game);
        currentState = ScreenState.Loading;
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
                currentScreen = new GameplayScreen(parentGame);
            }
        }
    }

    public Game getParentGame() {
        return parentGame;
    }

    public void setParentGame(Game parentGame) {
        this.parentGame = parentGame;
    }
}

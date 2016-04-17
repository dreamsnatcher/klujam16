package at.klujam.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import at.klujam.game.Game;
import at.klujam.game.ScreenManager;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class MenuScreen extends ScreenAdapter {

    private static final String EXIT_STRING = "Filthy Peasant!";
    private static final String START_STRING = "Enter Dungeon";
    private static final String CREDIT_STRING = "Hall of Creators";

    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private Game parentGame;

    Texture backgroundImage;
    BitmapFont menuFont;

    String[] menuStrings = {START_STRING, CREDIT_STRING, EXIT_STRING};
    int currentMenuItem = 0;

    float offsetLeft = Game.GAME_WIDTH / 8, offsetTop = Game.GAME_WIDTH / 8, offsetY = Game.GAME_HEIGHT / 8;


    public MenuScreen(Game game) {
        this.parentGame = game;

        backgroundImage = parentGame.getAssMan().get("menu/menu_background.jpg");
        menuFont = parentGame.getAssMan().get("fonts/celtic.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        handleInput();
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw bgImage ...
        batch.draw(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        // draw Strings ...
        for (int i = 0; i < menuStrings.length; i++) {
            if (i == currentMenuItem) menuFont.setColor(Color.CHARTREUSE);
            else menuFont.setColor(Color.WHITE);
            menuFont.draw(batch, menuStrings[i], offsetLeft, Game.GAME_HEIGHT - offsetTop - i * offsetY);
        }
        batch.end();
    }

    private void handleInput() {
        // keys ...
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentMenuItem = (currentMenuItem + 1) % menuStrings.length;
            parentGame.getSoundManager().playEvent("rage");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            currentMenuItem = (currentMenuItem - 1) % menuStrings.length;
            parentGame.getSoundManager().playEvent("rage");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (menuStrings[currentMenuItem].equals(EXIT_STRING)) {
                Gdx.app.exit();
                parentGame.getSoundManager().playEvent("explode");
            } else if (menuStrings[currentMenuItem].equals(CREDIT_STRING)) {
                parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
            } else if ((menuStrings[currentMenuItem].equals(START_STRING))){
                parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Game);
            }
        }
        // touch
        if (Gdx.input.justTouched()) {
            Vector3 touchWorldCoords = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
            // find the menu item ..
            for (int i = 0; i < menuStrings.length; i++) {
                if (touchWorldCoords.x > offsetLeft) {
                    float pos = Game.GAME_HEIGHT - offsetTop - i * offsetY;
                    if (touchWorldCoords.y < pos && touchWorldCoords.y > pos-menuFont.getLineHeight()) {
                        // it's there
                        if (menuStrings[i].equals(EXIT_STRING)) {
                            Gdx.app.exit();
                        } else if (menuStrings[i].equals(START_STRING)) {
                            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Intro);
                        } else if (menuStrings[i].equals(CREDIT_STRING)) {
                            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
                        }
                    }
                }

            }
        }
    }


}

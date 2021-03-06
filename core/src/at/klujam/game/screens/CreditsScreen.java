package at.klujam.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import at.klujam.game.Game;
import at.klujam.game.ScreenManager;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class CreditsScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private Game parentGame;

    Texture backgroundImage, gradientTop, gradientBottom;
    BitmapFont creditsFont;

    String[] credits = ("Game Design:\n"+
            "Veit Frick\n"+
            "Lukas Knoch\n"+
            "Lorenz Schmoliner\n"+
            "Chrsitian Zellot\n"+
            "Armin Lippitz\n"+
            " \n"+
            "Programming:\n"+
            "Veit Frick\n"+
            "Lukas Knoch\n"+
            "Lorenz Schmoliner\n"+
   "\n"+
            "Graphic Design:\n"+
            "Chrsitian Zellot\n"+
            "\n"+
            "Sound Engineering:\n"+
            "Mathias Lux\n"+
            "Armin Lippitz\n"+
            "Philipp Zellot\n"+
            "\n"+
            "Story & Level Design:\n"+
            "Armin Lippitz\n"+
            "\n"+
            "Voice Actors:\n"+
            "Mathias Kostwein- Narrator & Unibrow the Unethical Unicorn\n"+
            "Armin Lippitz - Hokus the Holy\n"+
            "Mathias Lux - Pokus the Poetic\n"+
            "Alexandra Wissiak - Tootsie the Toasted Tooth Fairy\n"+
            "Kristina Wogatai - Buttercup the Bitchy Butterfly").split("\\n");





    private float moveY;


    public CreditsScreen(Game game) {
        this.parentGame = game;

        backgroundImage = parentGame.getAssMan().get("menu/menu_background.jpg");
        gradientTop = parentGame.getAssMan().get("credits/gradient_top.png");
        gradientBottom = parentGame.getAssMan().get("credits/gradient_bottom.png");

        creditsFont = parentGame.getAssMan().get("fonts/celtic.fnt");

        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        moveY += delta*100;
        handleInput();
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw bgImage
        batch.draw(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // draw moving text:
        for (int i = 0; i < credits.length; i++) {
            creditsFont.draw(batch, credits[i], Game.GAME_WIDTH/8, moveY - i*creditsFont.getLineHeight()*1.5f);
        }


        // draw gradient
        batch.draw(gradientBottom, 0, 0, Game.GAME_WIDTH, gradientBottom.getHeight());
        batch.draw(gradientTop, 0, Game.GAME_HEIGHT-gradientTop.getHeight(), Game.GAME_WIDTH, gradientTop.getHeight());

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
    }


}

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
public class IntroScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private Game parentGame;

    Texture backgroundImage, gradientTop, gradientBottom;
    BitmapFont creditsFont;

    String[] credits = ("Teeth are the most important and valuable resource in Ever-no-neverwhere,\n" +
            "the mythical land of fairies, pixies, goblins, unicorns, and more.\n"+
            "The dust of ground up children’s teeth is used as food and provides the creatures of the mythical world with their magical powers at the same time.\n"+
            "Tootsie the Toasted Tooth Fairy and queen of Ever-no-neverwhere is supposed to divide the teeth equally among all residents of the realm.\n"+
            "However, she has recently began smoking exorbitant amounts of weed and decided to keep all teeth for herself to extort obedience from all other residents by starving them out.\n"+
            "She has also hired a gang of loathsome, loyal minions to guard the invaluable resource in her castle.\n"+
            "Desperate for magical energy Gorm the Gormless Goblin King has sent out two of his most trusted and brave warrior-mages Hokus the Holy and Pokus the Poetic to gather provisions of teeth for winter.\n"+
            "While making their way through the evil fairy’s realm they are captured and thrown into the castle dungeon.\n"+
            "They will have to break out and try to collect as many teeth as possible in the process, so the goblins can survive the cold months.\n"+
            "On their dangerous path they will have to overcome various of the fairy’s minions,\n"+
            "like Buttercup the Bit**y Butterfly, Unibrow the Unethical Unicorn, or Pinkie the Pixilated Pixie.\n"+
            "Help them beat their adversaries and safe the goblin nation.").split("\\n");
//
//
//
//            "GameSkelet0n by Mathias Lux\n" +
//            "All assets are public d0main\n" +
//            "Cl0ne and adapt t0 y0ur will\n" +
//            "\n" +
//            "H0pe it helps ;)").split("\\n");
    private float moveY;


    public IntroScreen(Game game) {
        this.parentGame = game;

        backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
        gradientTop = parentGame.getAssetManager().get("credits/gradient_top.png");
        gradientBottom = parentGame.getAssetManager().get("credits/gradient_bottom.png");

        creditsFont = parentGame.getAssetManager().get("fonts/celtic.fnt");

        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        moveY += delta*50;
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
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Game);
        }
    }


}

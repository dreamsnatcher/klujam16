package at.klujam.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.klujam.game.Game;
import at.klujam.game.ScreenManager;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class LoadingScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private Game parentGame;
    private Texture loadingSheet;
    private TextureRegion[] loadingFrames;

    private int animationFrame = 0;
    private float animationFrameShownFor = 0.05f; // how long is each frame shown ..
    private float animationFrameShownAlready = 0f;

    public LoadingScreen(Game game) {
        this.parentGame = game;
        // this is the only asset not loaded by the AssetManager.
        loadingSheet = new Texture(Gdx.files.internal("loading/preloader_strip.png"));
        loadingFrames = TextureRegion.split(loadingSheet, 2051/7, loadingSheet.getHeight())[0];

        // Create camera taht projects the game onto the actual screen size.
        cam = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (parentGame.getAssMan().update()) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        // determine the current frame:
        animationFrameShownAlready += delta;
        if (animationFrameShownAlready > animationFrameShownFor) {
            animationFrame = (animationFrame + 1) % loadingFrames.length;
            animationFrameShownAlready = 0f;
        }

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(loadingFrames[animationFrame], Game.GAME_WIDTH/2 - loadingFrames[animationFrame].getRegionWidth()/2, Game.GAME_HEIGHT/2-loadingFrames[animationFrame].getRegionHeight()/2);
        batch.end();
    }


}

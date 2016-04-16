package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.Mechanics.World;
import at.klujam.game.ScreenManager;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class GameplayScreen extends ScreenAdapter {

    private static final boolean DEBUG = false;
    final SpriteBatch guiBatch;
    final SpriteBatch gameBatch;
    public Game parentGame;
    public OrthographicCamera cam, camGui;
    ShapeRenderer sr;
    World world;
    private BitmapFont font;

    public GameplayScreen(Game game) {
        this.parentGame = game;
        this.world = new World(this);
        guiBatch = new SpriteBatch();
        gameBatch = new SpriteBatch();
        sr = new ShapeRenderer();

        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.zoom -= 0.5;
        cam.update();

        camGui = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        camGui.position.set(0, 0, 0);
        camGui.setToOrtho(false); //flip y-axis
        font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"),
                Gdx.files.internal("fonts/default.png"), false);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // camera:
        cameraFollow(delta);
        handleInput();
        cam.update();

        gameBatch.setProjectionMatrix(cam.combined);
        // render collision layer
        sr.setProjectionMatrix(cam.combined);

        world.update(delta);
        renderTiles();
        world.render(delta, gameBatch);

        renderGUI(guiBatch);
        // draw entity bounds
        if (DEBUG) {
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(0, 1, 0, 1);
            sr.rect(world.player.bounds.x, world.player.bounds.y, world.player.bounds.width, world.player.bounds.height);
            sr.rect(world.playerTwo.bounds.x, world.playerTwo.bounds.y, world.playerTwo.bounds.width, world.playerTwo.bounds.height);
            for (int i = 0; i < world.walls.length; i++) {
                for (int j = 0; j < world.walls[0].length; j++) {
                    Rectangle bounds = world.walls[i][j];
                    if (bounds == null) continue;
                    sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
                }
            }
            sr.end();
        }
    }

    public void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(camGui.combined);
        batch.begin();
        font.draw(batch, "Something", 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "something else", 130, Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    protected void renderTiles(){
        System.out.println("GamePlayScreen.renderTiles called");
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
            parentGame.getScreenManager().changeScreen(ScreenManager.ScreenState.Fighting);
        }
    }

    @Override
    public void resize(int width, int height) {
//        cam.viewportWidth = (Game.GAME_HEIGHT / (float) height) * width; //calculate aspect ratio
//        cam.update();
//        cam.viewportWidth = 30f;
//        cam.viewportHeight = 30f * height / width;
//        cam.update();
        cam.setToOrtho(false, width/ Constants.TILE_SIZE,height/Constants.TILE_SIZE);
    }

    public void cameraFollow(float deltaTime) {
        Vector2 dist = new Vector2(world.player.position).sub(cam.position.x, cam.position.y);
        cam.position.add(dist.x * deltaTime * World.CAM_DAMP, dist.y * deltaTime * World.CAM_DAMP, 0);
//        cam.position.set(world.player.position, 0);
    }
}
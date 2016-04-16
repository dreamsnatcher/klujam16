package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.Mechanics.World;
import at.klujam.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mathias Lux, mathias@juggle.at,  on 04.02.2016.
 */
public class GameplayScreen extends ScreenAdapter {

    private BitmapFont font;
    final SpriteBatch batch;
    public Game parentGame;
    public OrthographicCamera cam, camGui;
    World world;

    public GameplayScreen(Game game) {
        this.parentGame = game;
        this.world = new World(this);
        batch = new SpriteBatch();

        // Create camera that projects the game onto the actual screen size.
        cam = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        cam.setToOrtho(false, Gdx.graphics.getWidth() / (float) Constants.TILE_SIZE, Gdx.graphics.getHeight() / (float) Constants.TILE_SIZE);
//        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.zoom += 2;
        cam.update();

        camGui = new OrthographicCamera(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        camGui.position.set(0, 0, 0);
        camGui.setToOrtho(false); //flip y-axis
        font = new BitmapFont(Gdx.files.internal("fonts/default.fnt"),
                Gdx.files.internal("fonts/default.png"), false);

    }

    @Override
    public void render(float delta) {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // camera:
        cameraFollow(delta);
        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);
        world.render(delta);
        renderGUI(batch);
    }

    public void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(camGui.combined);
        batch.begin();
        font.draw(batch, "Something", 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "something else", 130, Gdx.graphics.getHeight() - 10);
        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.zoom -= 0.02;
        }
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = (Game.GAME_HEIGHT / (float) height) * width; //calculate aspect ratio
        cam.update();
    }
    public void cameraFollow(float deltaTime) {
        Vector2 dist = new Vector2(world.player.position).sub(cam.position.x, cam.position.y);
        cam.position.add(dist.x * deltaTime * World.CAM_DAMP, dist.y * deltaTime * World.CAM_DAMP, 0);
    }

}

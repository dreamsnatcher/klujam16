package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Veit on 15.04.2016.
 */
public class FightingSceneScreen extends GameplayScreen {

    private final InputMultiplexer multiplexer;
    private final TextButton playerOneAbilities;
    TextButton playerOneBackButton;
    Skin buttonSkins;
    BitmapFont font;
    FightWorld fightingWorld;
    Stage stage;

    int buttonHeigth = 64;
    int buttonWidth = 256;

    int spaceBetweenButtonsH = (int)((Game.GAME_WIDTH - (4*buttonWidth))/6);
    int spaceBetweenButtonsV = (int)(((Game.GAME_HEIGHT/3) - (4*buttonHeigth))/5);


    Array<TextButton> playerOneAbilitiesButtonGroup = new Array<TextButton>();
    Array<TextButton> playerOneBaseButtonGroup= new Array<TextButton>();
    Array<TextButton> allButtonsPlayerOne = new Array<TextButton>();


    public FightingSceneScreen(Game game) {
        super(game);
        stage = new Stage(new StretchViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT));
        fightingWorld = new FightWorld(this);
        buttonSkins = new Skin();
        buttonSkins.add("button_UP", new Texture("buttons/button_wood_idle.png"));
        buttonSkins.add("button_DOWN", new Texture("buttons/button_wood_active.png"));
        font = parentGame.getAssetManager().get("menu/Ravie_42.fnt");
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonSkins.getDrawable("button_UP");
        buttonStyle.down = buttonSkins.getDrawable("button_DOWN");
        buttonStyle.font = font;


        playerOneAbilities = new TextButton("Abilities", buttonStyle);
        playerOneAbilities.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setButtonGroup(playerOneAbilitiesButtonGroup);
            }
        });
        allButtonsPlayerOne.add(playerOneAbilities);
        playerOneBaseButtonGroup.add(playerOneAbilities);
        playerOneAbilities.setPosition(spaceBetweenButtonsH, 3 * spaceBetweenButtonsV + 2 * buttonHeigth);

        int count = 0;
        for (final F_Ability ability:fightingWorld.playerOne.abilities) {
            TextButton textButton = new TextButton(ability.name, buttonStyle);
            textButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("Button pressed"); //TODO
                    ability.useOn(null);
                }
            });
            int i = count % 2;
            if(i==0) i= 1;
            textButton.setPosition(i *spaceBetweenButtonsH + (count%2) * (buttonWidth+ spaceBetweenButtonsH), (((count/2)+2) * spaceBetweenButtonsV)+ ((count/2)+1) *buttonHeigth);
            System.out.println(ability.name);
            System.out.println(textButton.getX());
            System.out.println(textButton.getY());
            stage.addActor(textButton);
            playerOneAbilitiesButtonGroup.add(textButton);
            allButtonsPlayerOne.add(textButton);
            count++;
        }


        playerOneBackButton = new TextButton("Back", buttonStyle);
        playerOneBackButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                setButtonGroup(playerOneBaseButtonGroup);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button pressed"); //TODO
            }
        });
        playerOneAbilitiesButtonGroup.add(playerOneBackButton);
        allButtonsPlayerOne.add(playerOneBackButton);
        playerOneBackButton.setPosition(2*spaceBetweenButtonsH + buttonWidth, spaceBetweenButtonsV);


        setButtonGroup(playerOneBaseButtonGroup);

        for (TextButton button: allButtonsPlayerOne){
            stage.addActor(button);
        }
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(stage);
    }

    private void setButtonGroup(Array<TextButton> buttonGroup) {
        for (TextButton button: allButtonsPlayerOne){
            button.setVisible(false);
            button.setDisabled(false);
        }
        for (TextButton button: buttonGroup){
            button.setVisible(true);
            button.setDisabled(true);
        }

    }

    @Override
    public void render(float delta) {
        handleInput();
        // camera:
        cam.update();
        guiBatch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fightingWorld.update(delta);
        fightingWorld.render(delta);
        stage.act(delta);
        stage.draw();

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
        }
    }
}

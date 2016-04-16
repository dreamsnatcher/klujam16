package at.klujam.game.screens;

import at.klujam.game.Game;
import at.klujam.game.Mechanics.Actors.Mask;
import at.klujam.game.Mechanics.Entities.F_Enemy;
import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.F_Player_One;
import at.klujam.game.Mechanics.Entities.F_Player_Two;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.ScreenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veit on 15.04.2016.
 */
public class FightingSceneScreen extends GameplayScreen {

    private static final int SELECT_ABILITY = 0;
    private static final int SELECT_ENEMY = 1;
    private static final int ENEMY_TURN_STARTED = 2;
    private final InputMultiplexer multiplexer;
    private final TextButton playerOneAbilities;
    private final TextButton playerTwoAbilities;
    private final ArrayList<F_Entity> entities;
    private final TextButton.TextButtonStyle buttonStyleSelected;
    private final TextButton.TextButtonStyle buttonStyle;
    TextButton playerOneBackButton;
    int statePlayerOne = SELECT_ABILITY;
    int statePlayerTwo = SELECT_ABILITY;
    TextButton playerTwoBackButton;
    Skin buttonSkins;
    BitmapFont font;
    FightWorld fightingWorld;
    Stage stage;

    int buttonHeigth = 64;
    int buttonWidth = 256;

    int spaceBetweenButtonsH = (int)((Game.GAME_WIDTH - (4*buttonWidth))/6);
    int spaceBetweenButtonsV = (int)(((Game.GAME_HEIGHT/3) - (4*buttonHeigth))/5);


    Array<TextButton> playerOneAbilitiesButtonGroup = new Array<TextButton>();
    Array<TextButton> playerTwoAbilitiesButtonGroup = new Array<TextButton>();
    Array<TextButton> playerOneBaseButtonGroup= new Array<TextButton>();
    Array<TextButton> playerTwoBaseButtonGroup= new Array<TextButton>();
    Array<TextButton> allButtonsPlayerOne = new Array<TextButton>();
    Array<TextButton> allButtonsPlayerTwo = new Array<TextButton>();
    private int button_selected_p1;
    private Array<TextButton> currentButtonsPlayerOne;
    private Array<TextButton> currentButtonsPlayerTwo;
    private int button_selected_p2 = 0;
    private int currentEnemyPlayerOne = -1;
    private int currentEnemyPlayerTwo = -1;
    private Mask lower_mask;
    private List<F_Entity> party;


    public FightingSceneScreen(Game game) {
        super(game);
        stage = new Stage(new StretchViewport(Game.GAME_WIDTH, Game.GAME_HEIGHT));
        fightingWorld = new FightWorld(this);
        buttonSkins = new Skin();
        buttonSkins.add("button_UP", new Texture("buttons/button_wood_idle.png"));
        buttonSkins.add("button_DOWN", new Texture("buttons/button_wood_active.png"));
        font = parentGame.getAssMan().get("fonts/celtic.fnt");
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonSkins.getDrawable("button_UP");
        buttonStyle.down = buttonSkins.getDrawable("button_DOWN");
        buttonStyle.font = font;


        buttonStyleSelected = new TextButton.TextButtonStyle();
        buttonStyleSelected.up = buttonSkins.getDrawable("button_DOWN");
        buttonStyleSelected.down = buttonSkins.getDrawable("button_DOWN");
        buttonStyleSelected.font = font;

        lower_mask = new Mask(new Vector2(0,0),new Vector2(stage.getWidth(),stage.getHeight()/4f),fightingWorld);

        fightingWorld.playerOne = new F_Player_One(new Vector2((stage.getWidth()/15f), stage.getHeight()/4f), fightingWorld);
        fightingWorld.playerTwo = new F_Player_Two(new Vector2((stage.getWidth()/6f)*3, stage.getHeight()/4f), fightingWorld);
        fightingWorld.f_entities.add(fightingWorld.playerOne);
        fightingWorld.f_entities.add(fightingWorld.playerTwo);
        fightingWorld.f_entities.add(lower_mask);

        party = new ArrayList<F_Entity>();
        party.add(fightingWorld.playerOne);
        party.add(fightingWorld.playerTwo);


        playerOneAbilities = new TextButton("Abilities", buttonStyle);
        playerOneAbilities.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setButtonGroupPlayerOne(playerOneAbilitiesButtonGroup, FightingSceneScreen.this.allButtonsPlayerOne);
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
                    ability.useOn(entities.get(currentEnemyPlayerOne));
                    setButtonGroupPlayerOne(new Array<TextButton>(), playerOneAbilitiesButtonGroup);
                    statePlayerOne = ENEMY_TURN_STARTED;
                    currentEnemyPlayerOne = -1;

                }
            });
            int i = count % 2;
            if(i==0) i= 1;
            textButton.setPosition(i *spaceBetweenButtonsH + (count%2) * (buttonWidth+ spaceBetweenButtonsH), (((count/2)+2) * spaceBetweenButtonsV)+ ((count/2)+1) *buttonHeigth);
            stage.addActor(textButton);
            playerOneAbilitiesButtonGroup.add(textButton);
            allButtonsPlayerOne.add(textButton);
            count++;
        }


//        playerOneBackButton = new TextButton("Back", buttonStyle);
//        playerOneBackButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                setButtonGroupPlayerOne(playerOneBaseButtonGroup, allButtonsPlayerOne);
//            }
//        });
//        playerOneAbilitiesButtonGroup.add(playerOneBackButton);
//        allButtonsPlayerOne.add(playerOneBackButton);
//        playerOneBackButton.setPosition(2*spaceBetweenButtonsH + buttonWidth, spaceBetweenButtonsV);


        setButtonGroupPlayerOne(playerOneBaseButtonGroup, allButtonsPlayerOne);

        for (TextButton button: allButtonsPlayerOne){
            stage.addActor(button);
        }

        //////////////////////////////////////player 2
        int xOffsetPlayerTwo = Game.GAME_WIDTH/2;

        playerTwoAbilities = new TextButton("Abilities", buttonStyle);
        playerTwoAbilities.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setButtonGroupPlayerTwo(playerTwoAbilitiesButtonGroup, FightingSceneScreen.this.allButtonsPlayerTwo);
            }
        });
        allButtonsPlayerTwo.add(playerTwoAbilities);
        playerTwoBaseButtonGroup.add(playerTwoAbilities);
        playerTwoAbilities.setPosition(xOffsetPlayerTwo+spaceBetweenButtonsH, 3 * spaceBetweenButtonsV + 2 * buttonHeigth);

        count = 0;
        for (final F_Ability ability:fightingWorld.playerTwo.abilities) {
            TextButton textButton = new TextButton(ability.name, buttonStyle);
            textButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    ability.useOn(entities.get(currentEnemyPlayerTwo));
                    statePlayerTwo = ENEMY_TURN_STARTED;
                    setButtonGroupPlayerTwo(new Array<TextButton>(), playerTwoAbilitiesButtonGroup);
                    currentEnemyPlayerTwo =  -1;
                }
            });
            int i = count % 2;
            if(i==0) i= 1;
            textButton.setPosition(xOffsetPlayerTwo + (i *spaceBetweenButtonsH + (count%2) * (buttonWidth+ spaceBetweenButtonsH)), (((count/2)+2) * spaceBetweenButtonsV)+ ((count/2)+1) *buttonHeigth);
            stage.addActor(textButton);
            playerTwoAbilitiesButtonGroup.add(textButton);
            allButtonsPlayerTwo.add(textButton);
            count++;
        }


//        playerTwoBackButton = new TextButton("Back", buttonStyle);
//        playerTwoBackButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                System.out.println("Button pressed"); //TODO
//                setButtonGroupPlayerTwo(playerTwoBaseButtonGroup, FightingSceneScreen.this.allButtonsPlayerTwo);
//
//            }
//
//        });
//        playerTwoAbilitiesButtonGroup.add(playerTwoBackButton);
//        allButtonsPlayerTwo.add(playerTwoBackButton);
//        playerTwoBackButton.setPosition(xOffsetPlayerTwo + (2*spaceBetweenButtonsH + buttonWidth), spaceBetweenButtonsV);


        setButtonGroupPlayerTwo(playerTwoBaseButtonGroup, allButtonsPlayerTwo);

        for (TextButton button: allButtonsPlayerTwo){
            stage.addActor(button);
        }
        /////


        /////////////////////////////////////////////////////////////////////////entities

        entities = new ArrayList<F_Entity>();
        entities.add(fightingWorld.playerOne);
        entities.add(fightingWorld.playerTwo);
        float heightOpponents = (stage.getHeight() / 1.9f);
        entities.add(new F_Enemy(new Vector2(stage.getWidth()/10f*2, heightOpponents),fightingWorld, F_Enemy.Bitch));
        entities.add(new F_Enemy(new Vector2(stage.getWidth()/10f*3, heightOpponents),fightingWorld,F_Enemy.PIXIE));
        entities.add(new F_Enemy(new Vector2(stage.getWidth()/10f*4, heightOpponents),fightingWorld,F_Enemy.UNICORN));

        for (F_Entity f : entities) {
            fightingWorld.f_entities.add(f);
        }


        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(stage);
    }

    private void setButtonGroupPlayerOne(Array<TextButton> buttonGroup, Array<TextButton> textButtons) {
        currentButtonsPlayerOne = buttonGroup;
        for (TextButton button: textButtons){
            button.setVisible(false);
            button.setDisabled(false);
        }

        for (TextButton button: buttonGroup){
            button.setVisible(true);
            button.setDisabled(true);
        }

    }



    private void setButtonGroupPlayerTwo(Array<TextButton> buttonGroup, Array<TextButton> textButtons) {
        currentButtonsPlayerTwo = buttonGroup;
        for (TextButton button: textButtons){
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
            parentGame.getSoundManager().playEvent("blip");
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            PlayerOneSelectNextButton();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            PlayerOneSelectPreviousButton();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            PlayerTwoSelectNextButton();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            PlayerTwoSelectPreviousButton();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            parentGame.getSoundManager().playEvent("blip");
            PlayerOneEnter();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            parentGame.getSoundManager().playEvent("hit");
            PlayerTwoEnter();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
            parentGame.getScreenManager().changeScreen(ScreenManager.ScreenState.Game);
        }

        if(currentEnemyPlayerOne>=0) {
            SelectForPlayerOne(entities.get(currentEnemyPlayerOne));
        }else{
            SelectForPlayerOne(null);
        }
        if(currentEnemyPlayerTwo>=0) {

            SelectForPlayerTwo(entities.get(currentEnemyPlayerTwo));
        }else{
            SelectForPlayerTwo(null);
        }

        if(statePlayerOne == ENEMY_TURN_STARTED && statePlayerTwo== ENEMY_TURN_STARTED){
            DoEnemyAttack();
            setButtonGroupPlayerOne(playerOneBaseButtonGroup, allButtonsPlayerOne);
            setButtonGroupPlayerTwo(playerTwoBaseButtonGroup, allButtonsPlayerTwo);
            statePlayerOne = SELECT_ABILITY;
            statePlayerTwo = SELECT_ABILITY;


        }
    }

    private void DoEnemyAttack() {
        for(F_Entity entity : entities){
            if(entity instanceof F_Enemy){
                ((F_Enemy)entity).attack(party);
            }
        }
    }

    private void SelectForPlayerTwo(F_Entity f_enemy) {
        for (F_Entity enemy: entities) {
            enemy.SelectPlayerTwo(false);
        }
        if(f_enemy!=null){
            f_enemy.SelectPlayerTwo(true);
        }
    }

    private void SelectForPlayerOne(F_Entity f_enemy) {
        for (F_Entity enemy: entities) {
            enemy.SelectPlayerOne(false);
        }
        if(f_enemy!=null){
            f_enemy.SelectPlayerOne(true);
        }
    }

    private void PlayerTwoEnter() {
        if(statePlayerTwo == SELECT_ABILITY) {
            InputEvent event = new InputEvent();
            event.setType(InputEvent.Type.touchUp);
            statePlayerTwo = SELECT_ENEMY;
            //currentEnemyPlayerTwo = 0;
            currentButtonsPlayerTwo.get(button_selected_p2%currentButtonsPlayerTwo.size).fire(event);
        }else if(statePlayerTwo == SELECT_ENEMY){
            statePlayerTwo = SELECT_ABILITY;
        }
    }

    private void PlayerOneEnter() {
        if(statePlayerOne == SELECT_ABILITY) {
            InputEvent event = new InputEvent();
            event.setType(InputEvent.Type.touchUp);
            statePlayerOne = SELECT_ENEMY;
            //currentEnemyPlayerOne = 0;
            currentButtonsPlayerOne.get(button_selected_p1%currentButtonsPlayerOne.size).fire(event);
        }else if(statePlayerOne == SELECT_ENEMY){
            statePlayerOne = SELECT_ABILITY;
        }
    }

    private void PlayerTwoSelectPreviousButton() {
        if(statePlayerTwo == SELECT_ABILITY) {
            currentButtonsPlayerTwo.get(button_selected_p2 % currentButtonsPlayerTwo.size).setStyle(buttonStyle);
            button_selected_p2--;
            if (button_selected_p2 < 0) {
                button_selected_p2 = currentButtonsPlayerTwo.size - 1;
            }
            currentButtonsPlayerTwo.get(button_selected_p2 % currentButtonsPlayerTwo.size).setStyle(buttonStyleSelected);
        }else if(statePlayerTwo == SELECT_ENEMY){
            currentEnemyPlayerTwo = currentEnemyPlayerTwo-- <= 0 ? entities.size()-1:currentEnemyPlayerTwo--;
        }
    }

    private void PlayerTwoSelectNextButton() {
        if(statePlayerTwo == SELECT_ABILITY) {
            currentButtonsPlayerTwo.get(button_selected_p2 % currentButtonsPlayerTwo.size).setStyle(buttonStyle);
            button_selected_p2++;
            button_selected_p2 = button_selected_p2 % currentButtonsPlayerTwo.size;
            currentButtonsPlayerTwo.get(button_selected_p2 % currentButtonsPlayerTwo.size).setStyle(buttonStyleSelected);
        }else if(statePlayerTwo == SELECT_ENEMY){
            currentEnemyPlayerTwo = (currentEnemyPlayerTwo+1) % entities.size();
        }
    }


    private void PlayerOneSelectPreviousButton() {
        if(statePlayerOne == SELECT_ABILITY) {
            currentButtonsPlayerOne.get(button_selected_p1 % currentButtonsPlayerOne.size).setStyle(buttonStyle);
            button_selected_p1--;
            if (button_selected_p1 < 0) {
                button_selected_p1 = currentButtonsPlayerOne.size - 1;
            }
            currentButtonsPlayerOne.get(button_selected_p1 % currentButtonsPlayerOne.size).setStyle(buttonStyleSelected);
        }else if(statePlayerOne == SELECT_ENEMY){
            currentEnemyPlayerOne = currentEnemyPlayerOne--<=0? entities.size()-1:currentEnemyPlayerOne--;
        }
    }

    private void PlayerOneSelectNextButton() {
        if(statePlayerOne == SELECT_ABILITY) {
            currentButtonsPlayerOne.get(button_selected_p1 % currentButtonsPlayerOne.size).setStyle(buttonStyle);
            button_selected_p1++;
            currentButtonsPlayerOne.get(button_selected_p1 % currentButtonsPlayerOne.size).setStyle(buttonStyleSelected);
            button_selected_p1 = button_selected_p1 % currentButtonsPlayerOne.size;
        }else if(statePlayerOne == SELECT_ENEMY){
            currentEnemyPlayerOne = (currentEnemyPlayerOne+1) % entities.size();
        }
    }
}

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
import com.badlogic.gdx.graphics.Color;
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
    //private final TextButton playerOneAbilities;
    //private final TextButton playerTwoAbilities;
    private final ArrayList<F_Entity> entities;
    private final TextButton.TextButtonStyle buttonStyleSelected;
    private final TextButton.TextButtonStyle buttonStyle;
    TextButton playerOneBackButton;
    int statePlayerOne = SELECT_ENEMY;
    int statePlayerTwo = SELECT_ENEMY;
    TextButton playerTwoBackButton;
    Skin buttonSkins;
    BitmapFont font;
    FightWorld fightingWorld;
    Stage stage;

    int buttonHeigth = 64;
    int buttonWidth = 256;

    int spaceBetweenButtonsH = (int)((Gdx.graphics.getWidth() - (4*buttonWidth))/6);
    int spaceBetweenButtonsV = (int)(((Gdx.graphics.getHeight()/3) - (4*buttonHeigth))/5);


    Array<TextButton> playerOneAbilitiesButtonGroup = new Array<TextButton>();
    Array<TextButton> playerTwoAbilitiesButtonGroup = new Array<TextButton>();
    //Array<TextButton> playerOneBaseButtonGroup= new Array<TextButton>();
    //Array<TextButton> playerTwoBaseButtonGroup= new Array<TextButton>();
    Array<TextButton> allButtonsPlayerOne = new Array<TextButton>();
    Array<TextButton> allButtonsPlayerTwo = new Array<TextButton>();
    private int button_selected_p1;
    private Array<TextButton> currentButtonsPlayerOne;
    private Array<TextButton> currentButtonsPlayerTwo;
    private int button_selected_p2 = 0;
    private int currentEnemyPlayerOne = 2;
    private int currentEnemyPlayerTwo = 2;
    private Mask lower_mask;
    private Mask full_mask;
    private List<F_Entity> party;
    private boolean fightFinished;
    private int currentEnemy;
    private boolean currentEnemyFinished;
    private final float WAITENEMYTIME = 3f;
    private float waittimer = 0;
    private Array<F_Enemy> enemies;


    public FightingSceneScreen(Game game) {
        super(game);
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
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
        buttonStyleSelected.fontColor = Color.CYAN;

        full_mask = new Mask(new Vector2(0,Gdx.graphics.getHeight()/4f),new Vector2(Gdx.graphics.getWidth(),(Gdx.graphics.getHeight())),fightingWorld,Mask.ROOM);
        lower_mask = new Mask(new Vector2(0,0),new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/3f),fightingWorld,Mask.CARPET);

        fightingWorld.playerOne = new F_Player_One(new Vector2(((Gdx.graphics.getWidth()/10f) * 2), Gdx.graphics.getHeight()/3f), fightingWorld);
        fightingWorld.playerTwo = new F_Player_Two(new Vector2((Gdx.graphics.getWidth()/10f)* 7, Gdx.graphics.getHeight()/3f), fightingWorld);
        fightingWorld.f_entities.add(fightingWorld.playerOne);
        fightingWorld.f_entities.add(fightingWorld.playerTwo);
        fightingWorld.f_entities.add(full_mask);
        fightingWorld.f_entities.add(lower_mask);

        party = new ArrayList<F_Entity>();
        party.add(fightingWorld.playerOne);
        party.add(fightingWorld.playerTwo);


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
                    applyAbilityPlayerOne(ability);
                }
            });
            int i = count % 2;
            if(i==0) i= 1;
            textButton.setPosition(i * spaceBetweenButtonsH + (count%2) * (buttonWidth+ spaceBetweenButtonsH), (((count/2)+2) * spaceBetweenButtonsV)+ ((count/2)+1) *buttonHeigth);
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


        setButtonGroupPlayerOne(playerOneAbilitiesButtonGroup, allButtonsPlayerOne);

        for (TextButton button: allButtonsPlayerOne){
            stage.addActor(button);
        }

        //////////////////////////////////////player 2
        int xOffsetPlayerTwo = Gdx.graphics.getWidth()/2;


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
                    applyAbilityPlayerTwo(ability);
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


        setButtonGroupPlayerTwo(playerTwoAbilitiesButtonGroup, allButtonsPlayerTwo);

        for (TextButton button: allButtonsPlayerTwo){
            stage.addActor(button);
        }
        /////


        /////////////////////////////////////////////////////////////////////////entities

        entities = new ArrayList<F_Entity>();
        entities.add(fightingWorld.playerOne);
        entities.add(fightingWorld.playerTwo);
        float heightOpponents = ((Gdx.graphics.getHeight() / 10f)*8);
        entities.add(new F_Enemy(new Vector2(Gdx.graphics.getWidth()/10f*2, heightOpponents),fightingWorld, F_Enemy.Bitch));
        entities.add(new F_Enemy(new Vector2(Gdx.graphics.getWidth()/10*5, heightOpponents),fightingWorld,F_Enemy.PIXIE));
        entities.add(new F_Enemy(new Vector2(Gdx.graphics.getWidth()/10*8, heightOpponents),fightingWorld,F_Enemy.UNICORN));

        for (F_Entity f : entities) {
            fightingWorld.f_entities.add(f);
        }


        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(stage);
    }

    private void applyAbilityPlayerOne(F_Ability ability) {
        if(currentEnemyPlayerOne<0 || currentEnemyPlayerOne>= entities.size()){
            currentEnemyPlayerOne = 0;
        }
        ability.useOn(getEmenyPlayerOne());
        setButtonGroupPlayerOne(new Array<TextButton>(), playerOneAbilitiesButtonGroup);
        statePlayerOne = ENEMY_TURN_STARTED;
    }

    /**
     * Applies an abimilty to the currently selected enemy
     * @param ability
     */
    private void applyAbilityPlayerTwo(F_Ability ability) {
        if(currentEnemyPlayerTwo < 0 || currentEnemyPlayerTwo>= entities.size()){
            currentEnemyPlayerTwo = 0;
            fightingWorld.playerTwo.SetStateText(Color.RED, "Attack Failed!", 4f);
        }
        ability.useOn(getTargetPlayerTwo());
        statePlayerTwo = ENEMY_TURN_STARTED;
        setButtonGroupPlayerTwo(new Array<TextButton>(), playerTwoAbilitiesButtonGroup);
    }

    private F_Entity getTargetPlayerTwo() {
        return entities.get(currentEnemyPlayerTwo);
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



    private void setButtonGroupPlayerTwo(Array<TextButton> newButtons, Array<TextButton> oldButtons) {
        currentButtonsPlayerTwo = newButtons;
        for (TextButton button: oldButtons){
            button.setVisible(false);
            button.setDisabled(false);
        }

        for (TextButton button: newButtons){
            button.setVisible(true);
            button.setDisabled(true);
        }

    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        // camera:
        cam.update();
        guiBatch.setProjectionMatrix(cam.combined);


        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fightingWorld.update(delta);
        fightingWorld.render(delta);
        for(F_Entity e: entities){
            if(e.isDead()){
                if(e instanceof F_Enemy){
                    e.position.y += 500*delta;
                } else {
                    e.position.y -= 500*delta;
                }
            }
        }
        stage.act(delta);
        stage.draw();

    }

    private void handleInput(float delta) {
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

        if(statePlayerOne == SELECT_ENEMY && currentEnemyPlayerOne >= 0) {
            SelectForPlayerOne(getEmenyPlayerOne());
        }else{
            SelectForPlayerOne(null);
        }

        if(statePlayerTwo == SELECT_ENEMY && currentEnemyPlayerTwo >= 0) {

            SelectForPlayerTwo(getTargetPlayerTwo());
        }else{
            SelectForPlayerTwo(null);
        }


        if(statePlayerOne == ENEMY_TURN_STARTED && statePlayerTwo== ENEMY_TURN_STARTED){
            enemies = new Array<F_Enemy>();
            for (F_Entity ent:entities ) {
                if(ent instanceof F_Enemy ){
                    enemies.add((F_Enemy)ent);
                }
            }
            DoEnemyAttack(delta);

        }
        int enemyCount = 0;
        for (F_Entity ent:entities ) {
            if(ent instanceof F_Enemy  && !ent.isDead()) {
                enemyCount++;
            }

        }
        if(enemyCount == 0){
            parentGame.getScreenManager().changeScreen(ScreenManager.ScreenState.Game);
        }
    }





    private F_Entity getEmenyPlayerOne() {
        return entities.get(currentEnemyPlayerOne);


    }

    private void DoEnemyAttack(float delta) {
        System.out.println("Waittimer: " + waittimer);
        System.out.println("Enemies: " + enemies.size);
            if(waittimer<=0){
                if(!enemies.get(currentEnemy).isDead()) {
                    enemies.get(currentEnemy).attack(party);
                    waittimer += 1; //rein gehen
                }else {
                    waittimer+=5; //Überspringen
                }
            }
            else if (waittimer>0 && waittimer<=WAITENEMYTIME){
                waittimer+=delta;
            }
            else if(waittimer>WAITENEMYTIME){
                if(currentEnemy+1 < enemies.size){
                    currentEnemy++;
                    waittimer=0;
                }
                else{
                    setButtonGroupPlayerOne(playerOneAbilitiesButtonGroup, allButtonsPlayerOne);
                    setButtonGroupPlayerTwo(playerTwoAbilitiesButtonGroup, allButtonsPlayerTwo);
                    if(!fightingWorld.playerOne.isDead())
                        statePlayerOne = SELECT_ENEMY;
                    if(!fightingWorld.playerTwo.isDead())
                        statePlayerTwo = SELECT_ENEMY;
                    waittimer = 0;
                    currentEnemy = 0;
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
        if(!fightingWorld.playerTwo.isDead()) {
            if (statePlayerTwo == SELECT_ABILITY) {
                F_Ability ability = fightingWorld.playerTwo.abilities.get(button_selected_p2);
                applyAbilityPlayerTwo(ability);
            } else if (statePlayerTwo == SELECT_ENEMY) {
                statePlayerTwo = SELECT_ABILITY;
            }
        }
    }

    private void PlayerOneEnter() {
        if(!fightingWorld.playerOne.isDead()) {
            if (statePlayerOne == SELECT_ABILITY) {
                F_Ability ability = fightingWorld.playerOne.abilities.get(button_selected_p1);
                applyAbilityPlayerOne(ability); //TODO get selected ability
            } else if (statePlayerOne == SELECT_ENEMY) {
                statePlayerOne = SELECT_ABILITY;
            }
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
            if(getTargetPlayerTwo().isDead()){
                PlayerTwoSelectPreviousButton();
            }
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
            if(getTargetPlayerTwo().isDead()){
                PlayerTwoSelectNextButton();
            }
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
            if(getEmenyPlayerOne().isDead()){
                PlayerOneSelectPreviousButton();
            }
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
            if(getEmenyPlayerOne().isDead()){
                PlayerOneSelectNextButton();
            }
        }
    }
}

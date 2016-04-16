package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.Mechanics.States.F_Dead;
import at.klujam.game.Mechanics.States.F_State;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veit on 15.04.2016.
 */
public abstract class F_Entity{

    public int hitpoints = 100;
    public float resistence = 0;
    public float baseDamage = 1;
    public List<F_Ability> abilities = new ArrayList<F_Ability>();
    boolean selectedByOne = false;
    boolean selectedByTwo = false;
    private Texture selector1Textur;
    private Texture selector2Textur;

    Vector2 position;
    Rectangle bounds;
    FightWorld world;
    Texture texture;
    Array<F_State> states;
    public int armor;
    protected F_Entity forcedEntity;
    private BitmapFont numberFont;


    public F_Entity(Vector2 position, FightWorld world) {
        this.position = position;
        this.world = world;
        states = new Array<F_State>();
        selector1Textur = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/selected1.png");
        selector2Textur = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/selected2.png");
        numberFont = world.fightingSceneScreen.parentGame.getAssMan().get("fonts/celtic.fnt");
    }

    public void removeState(F_State state){
        this.states.removeValue(state, false);
    }

    public void addState(F_State state){
        if(state.stackable){
            states.add(state);
        }
        else if(!states.contains(state, false)){
            states.add(state);
        }
    }

    public void update(float delta){
        for (F_State state: states) {
            state.update(delta);
        }
    };

    public void render(float delta, SpriteBatch spriteBatch) {

        spriteBatch.draw(texture, position.x, position.y);
        if(selectedByTwo){
            spriteBatch.draw(selector1Textur, position.x + texture.getWidth()/2 + 9, position.y + texture.getHeight());

        }
        if (selectedByOne){
            spriteBatch.draw(selector2Textur, position.x + texture.getWidth()/2 - 9, position.y + texture.getHeight());
        }
        if(hitpoints < 10){
            numberFont.setColor(Color.RED);
        }else{
            numberFont.setColor(Color.GREEN);

        }
        numberFont.draw(spriteBatch,Integer.toString(hitpoints),position.x,position.y- numberFont.getXHeight() +10);
    }

    public void inflict_damage(float damage){
        if(damage-armor>0) {
            this.hitpoints -= (damage-armor);
        }
        if(this.hitpoints<=0){
            this.addState(new F_Dead(0,this,world));
        }
    }

    public void nextRound(){
        //TODO
    }


    public void SelectPlayerOne(boolean b) {
        selectedByOne = b;
    }

    public void SelectPlayerTwo(boolean b) {
        selectedByTwo = b;
    }

    //TODO @LUKAS KNOCH
    public void forceTarget(F_Entity origin) {
        forcedEntity = origin;
    }


    public void heal(float v){
        this.hitpoints += v;
    }
}

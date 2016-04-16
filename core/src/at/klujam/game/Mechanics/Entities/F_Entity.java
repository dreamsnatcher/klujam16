package at.klujam.game.Mechanics.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.Mechanics.States.F_Dead;
import at.klujam.game.Mechanics.States.F_State;
import at.klujam.game.Mechanics.States.State;
import at.klujam.game.Mechanics.World;

/**
 * Created by Veit on 15.04.2016.
 */
public abstract class F_Entity{

    public int hitpoints = 100;
    public float resistence = 0;
    public float baseDamage = 1;
    public Array<F_Ability> abilities = new Array<F_Ability>();

    Vector2 position;
    Rectangle bounds;
    FightWorld world;
    Texture texture;
    Array<F_State> states;
    public int armor;


    public F_Entity(Vector2 position, FightWorld world) {
        this.position = position;
        this.world = world;
        states = new Array<F_State>();
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

    //TODO @LUKAS KNOCH
    public void forceTarget(F_Entity origin) {

    }

    public void heal(float v){
        this.hitpoints += v;
    }
}

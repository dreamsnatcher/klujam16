package at.klujam.game.Mechanics.States;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.GameObject;

/**
 * Created by Veit on 08.02.2016.
 */
public abstract class F_State {
    float stateTime;
    F_Entity parentObject;
    float maxStateTime;
    boolean hasMaxStateTime;
    public boolean stackable;

    public F_State(float stateTime, F_Entity parentObject, float maxStateTime, boolean stackable) {
        this.stateTime = stateTime;
        this.parentObject = parentObject;
        this.maxStateTime = maxStateTime;
        this.hasMaxStateTime = true;
        this.stackable = stackable;
    }

    public F_State(float stateTime, F_Entity parentObject, boolean stackable) {
        this.stateTime = stateTime;
        this.parentObject = parentObject;
        this.hasMaxStateTime = false;
        this.stackable = stackable;
    }

    public void update(float delta){
        this.stateTime += delta;
        if(this.hasMaxStateTime){
            if(this.maxStateTime<this.stateTime){
                this.remove();
            }
        }
    }

    public void remove() {
        parentObject.removeState(this);
    }

    public void render(){

    }
}

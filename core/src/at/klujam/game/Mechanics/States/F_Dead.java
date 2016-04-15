package at.klujam.game.Mechanics.States;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.Mechanics.FightWorld;

/**
 * Created by Veit on 15.04.2016.
 */
public class F_Dead extends F_State {
    FightWorld world;

    public F_Dead(float stateTime, F_Entity parentObject, FightWorld world) {
        super(stateTime,parentObject,false);
        this.world = world;
    }

    @Override
    public void update(float delta){
        super.update(delta);
    }
}

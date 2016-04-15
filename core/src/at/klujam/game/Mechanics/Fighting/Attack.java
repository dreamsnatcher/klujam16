package at.klujam.game.Mechanics.Fighting;

import com.badlogic.gdx.math.Vector2;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.States.F_State;
import at.klujam.game.Mechanics.World;

/**
 * Created by Veit on 15.04.2016.
 */
public class Attack extends F_Ability {

    public Attack(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){
        target.inflict_damage(origin.baseDamage);
    }
}

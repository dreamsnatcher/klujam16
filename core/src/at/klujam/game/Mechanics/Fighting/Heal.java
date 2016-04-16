package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;

/**
 * Created by Veit on 15.04.2016.
 */
public class Heal extends F_Ability {

    public Heal(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){

        target.heal(origin.baseDamage/2);

    }
}

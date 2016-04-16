package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;

/**
 * Created by Veit on 15.04.2016.
 */
public class HeavyAttack extends F_Ability {

    public HeavyAttack(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){
        if(Math.random()>0.6)
            target.inflict_damage((int)(origin.baseDamage*3.5));

    }
}

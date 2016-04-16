package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Veit on 15.04.2016.
 */
public class Attack extends F_Ability {

    public Attack(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){

        target.inflict_damage(origin.baseDamage);
        target.SetStateText(Color.RED, "-"+origin.baseDamage,3);

    }
}

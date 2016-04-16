package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Veit on 15.04.2016.
 */
public class Heal extends F_Ability {

    public Heal(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){

        float v = origin.baseDamage / 2;
        target.heal(v);
        target.SetStateText(Color.GREEN, "+"+v, 3f);
    }
}

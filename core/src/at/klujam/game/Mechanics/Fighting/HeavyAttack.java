package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Veit on 15.04.2016.
 */
public class HeavyAttack extends F_Ability {

    public HeavyAttack(String name, FightWorld fworld, F_Entity origin, int mod) {
        super(name, fworld, origin, mod);
    }

    public void useOn(F_Entity target){
        String text = "Miss";

        if(Math.random()>0.55) {
            int damage = (int) (origin.baseDamage * 2.5 + mod);
            target.inflict_damage(damage);
            text = "-"+damage;
        }
        target.SetStateText(Color.RED, text,3f);

    }
}

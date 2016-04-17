package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Veit on 15.04.2016.
 */
public class Insult extends F_Ability {

    public Insult(String name, FightWorld fworld, F_Entity origin, int mod) {
        super(name, fworld, origin, mod);
    }

    public void useOn(F_Entity target){
        target.forceTarget(origin);
        target.SetStateText(Color.RED, "You swine!" , 2);
        origin.armor+=mod+1;
        origin.SetStateText(Color.GOLD,"+"+(mod+1),3f);
    }
}

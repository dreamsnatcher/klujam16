package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Veit on 15.04.2016.
 */
public class Insult extends F_Ability {

    public Insult(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){
        target.forceTarget(origin);
        target.SetStateText(Color.RED, "Argh!" , 2);
        origin.armor++;
        origin.SetStateText(Color.BLUE,"+1",3f);
    }
}

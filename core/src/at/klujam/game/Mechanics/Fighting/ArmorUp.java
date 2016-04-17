package at.klujam.game.Mechanics.Fighting;

import com.badlogic.gdx.graphics.Color;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.F_Player_One;
import at.klujam.game.Mechanics.Entities.F_Player_Two;
import at.klujam.game.Mechanics.FightWorld;

/**
 * Created by Veit on 15.04.2016.
 */
public class ArmorUp extends F_Ability {

    public ArmorUp(String name, FightWorld fworld, F_Entity origin) {
        super(name, fworld, origin);
    }

    public void useOn(F_Entity target){

        target.armor += (origin.baseDamage-1);
        target.SetStateText(Color.BLUE, "+" + (origin.baseDamage-1), 3);
        if(origin instanceof F_Player_One){

            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack");
        }
        else if(origin instanceof F_Player_Two){

            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_armor");
        }


    }
}

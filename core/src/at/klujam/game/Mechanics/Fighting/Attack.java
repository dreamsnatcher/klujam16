package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.F_Player_One;
import at.klujam.game.Mechanics.Entities.F_Player_Two;
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
        target.SetStateText(Color.RED, "-" + origin.baseDamage, 3);
        if(origin instanceof F_Player_One){
            System.out.println("HALT DEIN MAUL");
            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack");
        }
        else if(origin instanceof F_Player_Two){
            System.out.println("HALT MEIN MAUL");
            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_attack");
        }


    }
}

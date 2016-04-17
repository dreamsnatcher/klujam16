package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.F_Player_One;
import at.klujam.game.Mechanics.Entities.F_Player_Two;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

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
            switch(MathUtils.random(1,5)){
                case 1: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack_1"); break;
                case 2: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack_2"); break;
                case 3: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack_3"); break;
                case 4: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack_4"); break;
                case 5: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_attack_5"); break;
            }
        }
        else if(origin instanceof F_Player_Two){
            switch(MathUtils.random(1,4)){
                case 1: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_attack_1"); break;
                case 2: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_attack_2"); break;
                case 3: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_attack_3"); break;
                case 4: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_attack_4"); break;
            }
        }


    }
}

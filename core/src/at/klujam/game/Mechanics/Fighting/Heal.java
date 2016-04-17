package at.klujam.game.Mechanics.Fighting;

import at.klujam.game.Mechanics.Entities.F_Enemy;
import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.F_Player_One;
import at.klujam.game.Mechanics.Entities.F_Player_Two;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

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
        target.SetStateText(Color.GREEN, "+" + v, 3f);



        if(origin instanceof F_Player_One){
            switch(MathUtils.random(1, 2)){
                case 1: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_heal_1"); break;
                case 2: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("pokus_heal_2"); break;
            }
        }
        else if(origin instanceof F_Player_Two){
            switch(MathUtils.random(1,1)){
                case 1: fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("hokus_heal_1"); break;
            }
        }
        else if(origin instanceof F_Enemy){
            System.out.println("Enemy speech " + ((F_Enemy)origin).type);
            if(((F_Enemy)origin).type == 0){
                switch (MathUtils.random(1, 1)) {
                    case 1:
                        System.out.println("Buttercup Heal should be played");
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("buttercup_heal_1");
                        break;
                }
            }
            if(((F_Enemy)origin).type == 1) {
                switch (MathUtils.random(1, 1)) {
                    case 1:
                        System.out.println("Tootsie Heal should be played");
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("tootsie_heal_1");
                        break;
                }
                if (((F_Enemy) origin).type == 2) {
                    switch (MathUtils.random(1, 1)) {
                        case 1:
                            System.out.println("Unibrow Heal should be played");
                            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("unibrow_heal_1");
                            break;
                    }
                }
            }

        }



    }
}

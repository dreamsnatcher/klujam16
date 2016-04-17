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
public class Attack extends F_Ability {

    public Attack(String name, FightWorld fworld, F_Entity origin, int mod) {
        super(name, fworld, origin, mod);
    }

    public void useOn(F_Entity target){

        target.inflict_damage(origin.baseDamage/2 + mod);
        target.SetStateText(Color.RED, "-" + origin.baseDamage/2 + mod, 3);

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
        else if(origin instanceof F_Enemy){
            System.out.println("Enemy speech " + ((F_Enemy)origin).type);
            if(((F_Enemy)origin).type == 0){
                System.out.println("Buttercup attack should be played");
                switch (MathUtils.random(1, 5)) {
                    case 1:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("buttercup_attack_1");
                        break;
                    case 2:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("buttercup_attack_2");
                        break;
                    case 3:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("buttercup_attack_3");
                        break;
                    case 4:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("buttercup_attack_4");
                        break;
                    case 5:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("buttercup_attack_5");
                        break;
                }
            }
            if(((F_Enemy)origin).type == 1) {
                System.out.println("Tootise attack should be played");
                switch (MathUtils.random(1, 3)) {
                    case 1:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("tootsie_attack_1");
                        break;
                    case 2:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("tootsie_attack_2");
                        break;
                    case 3:
                        fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("tootsie_attack_3");
                        break;
                }
                if (((F_Enemy) origin).type == 2) {
                    System.out.println("Uniebrow attack should be played");
                    switch (MathUtils.random(1, 5)) {
                        case 1:
                            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("unibrow_attack_1");
                            break;
                        case 2:
                            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("unibrow_attack_2");
                            break;
                        case 3:
                            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("unibrow_attack_3");
                            break;
                        case 4:
                            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("unibrow_attack_4");
                            break;
                        case 5:
                            fworld.fightingSceneScreen.parentGame.getSoundManager().playSpeech("unibrow_attack_5");
                            break;
                    }
                }
            }

        }


    }
}

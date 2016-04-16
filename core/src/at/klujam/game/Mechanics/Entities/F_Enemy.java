package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.Mechanics.Fighting.Heal;
import at.klujam.game.Mechanics.Fighting.HeavyAttack;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Random;

/**
 * Created by lknoch on 16.04.16.
 */
public class F_Enemy extends F_Entity {

    public static final int Bitch = 0;
    public static final int PIXIE = 1;
    public static final int UNICORN = 2;
    private Random randomGenerator;


    public F_Enemy(Vector2 position, FightWorld world, int type) {
        super(position, world);
        this.abilities.add(new Attack("Attack",world,this));
        this.abilities.add(new Attack("Attack",world,this));
        this.abilities.add(new HeavyAttack("Attack",world,this));
        this.abilities.add(new Heal("Insult",world,this));

        switch (type){
            case Bitch:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/bitch_butterfly.png");
                randomGenerator = new Random();
                baseDamage = 4 + randomGenerator.nextInt(10);
                break;
            case UNICORN:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/unicorn.png");
                randomGenerator = new Random();
                baseDamage = 7 + randomGenerator.nextInt(3);
                break;
            case PIXIE:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/pixie_pixelated.png");
                randomGenerator = new Random();
                baseDamage = 3 + randomGenerator.nextInt(2);
                break;
        }
    }


    public void attack(List<F_Entity> party) {
        F_Ability f_ability = getRandom(abilities);
        if(f_ability instanceof Heal && forcedEntity != null){
            f_ability.useOn(this);
        }

        if (forcedEntity == null) {
            forcedEntity = getRandom(party);
        }

        f_ability.useOn(forcedEntity);
        forcedEntity = null;

    }

    private <T> T getRandom(List<T> party) {
        int index = randomGenerator.nextInt(party.size());
        return  party.get(index);
    }
}

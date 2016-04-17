package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.ArmorUp;
import at.klujam.game.Mechanics.Fighting.Attack;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.Mechanics.Fighting.Heal;
import at.klujam.game.Mechanics.Fighting.HeavyAttack;
import at.klujam.game.Mechanics.Fighting.Penetrate;

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
    public static final int BOSS = 4;
    public static final Integer NONE = 3;
    public int type;

    private Random randomGenerator;


    public F_Enemy(Vector2 position, FightWorld world, int type) {
        super(position, world);
        this.type = type;
        switch (type) {
            case Bitch:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/bitch_butterfly_big_000.png");
                animation = world.fightingSceneScreen.parentGame.getAnimator().loadAnimation("gameplay/bitch_butterfly_big_00", 4, 0.3f);
                randomGenerator = new Random();
                baseDamage = 4 + randomGenerator.nextInt(10);
                this.hitpoints = 30+randomGenerator.nextInt(10);
                this.abilities.add(new Attack("Attack", world, this,1));
                this.abilities.add(new HeavyAttack("HAttack", world, this,1));
                this.abilities.add(new HeavyAttack("HAttack", world, this,1));
                this.abilities.add(new Penetrate("Penetrate", world, this,1));
                break;
            case UNICORN:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/unibrow_big_000.png");
                animation = world.fightingSceneScreen.parentGame.getAnimator().loadAnimation("gameplay/unibrow_big_00", 4, 0.3f);
                randomGenerator = new Random();
                this.hitpoints = 10+randomGenerator.nextInt(40);
                baseDamage = 7 + randomGenerator.nextInt(3);
                this.abilities.add(new Attack("Attack", world, this,1));
                this.abilities.add(new Attack("Attack", world, this,1));
                this.abilities.add(new ArmorUp("Armor", world, this,1));
                this.abilities.add(new Heal("Heal", world, this,1));
                this.abilities.add(new Heal("Heal", world, this,1));
                this.abilities.add(new Heal("Heal", world, this,1));
                break;
            case PIXIE:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/pixie_pixelated_big_000.png");
                dyingAnimation = world.fightingSceneScreen.parentGame.getAnimator().loadAnimation("gameplay/pixie_pixelated_big_dying_00", 6, 0.3f);
                animation = world.fightingSceneScreen.parentGame.getAnimator().loadAnimation("gameplay/pixie_pixelated_big_00",3,0.3f);
                randomGenerator = new Random();
                this.hitpoints = 20+randomGenerator.nextInt(5);
                baseDamage = 3 + randomGenerator.nextInt(2);
                this.abilities.add(new Penetrate("Penetrate", world, this,8));
                this.abilities.add(new Penetrate("Penetrate", world, this,7));
                this.abilities.add(new Attack("Attack", world, this,1));
                this.abilities.add(new Attack("Heal", world, this,1));
                break;
            case BOSS:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/tooth_fairy_000.png");
                animation = world.fightingSceneScreen.parentGame.getAnimator().loadAnimation("gameplay/tooth_fairy_00", 6, 0.3f);
                randomGenerator = new Random();
                baseDamage = 10 + randomGenerator.nextInt(5);
                this.abilities.add(new Attack("Attack", world, this,4));
                this.abilities.add(new Attack("Attack", world, this,4));
                this.abilities.add(new HeavyAttack("Attack", world, this,1));
                this.abilities.add(new Heal("Insult", world, this,1));
                hitpoints = 130;
                break;
        }
    }


    public void attack(List<F_Entity> party) {
        F_Ability f_ability = getRandom(abilities);
        if (f_ability instanceof Heal && forcedEntity == null) {
            f_ability.useOn(this);
            return;
        }

        if (forcedEntity == null) {
            forcedEntity = getRandom(party);
        }

        f_ability.useOn(forcedEntity);
        forcedEntity = null;

    }

    private <T> T getRandom(List<T> party) {
        int index = randomGenerator.nextInt(party.size());
        return party.get(index);
    }
}

package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;
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
        this.abilities.add(new Attack("Insult",world,this));
        this.abilities.add(new Attack("Insult1",world,this));
        this.abilities.add(new Attack("Charm",world,this));
        randomGenerator = new Random();

        switch (type){
            case Bitch:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/bitch_butterfly.png");
                break;
            case UNICORN:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/unicorn.png");
                break;
            case PIXIE:
                this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/pixie_pixelated.png");
                break;
        }
    }


    public void attack(List<F_Entity> party) {
        if (forcedEntity == null) {
            forcedEntity = getRandom(party);
        }

        getRandom(abilities).useOn(forcedEntity);
        forcedEntity = null;

    }

    private <T> T getRandom(List<T> party) {
        int index = randomGenerator.nextInt(party.size());
        return  party.get(index);
    }
}

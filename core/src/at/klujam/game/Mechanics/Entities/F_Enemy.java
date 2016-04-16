package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Created by lknoch on 16.04.16.
 */
public class F_Enemy extends F_Entity {

    public static final int Bitch = 0;
    public static final int PIXIE = 1;
    public static final int UNICORN = 2;


    public F_Enemy(Vector2 position, FightWorld world, int type) {
        super(position, world);
        this.abilities.add(new Attack("Attack",world,this));
        this.abilities.add(new Attack("Insult",world,this));
        this.abilities.add(new Attack("Insult1",world,this));
        this.abilities.add(new Attack("Charm",world,this));

        switch (type){
            case Bitch:
                this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/bitch_butterfly.png");
                break;
            case UNICORN:
                this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/unicorn.png");
                break;
            case PIXIE:
                this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/pixie_pixelated.png");
                break;
        }
    }


    public void attack(List<F_Entity> party) {
        //TODO randome
    }
}

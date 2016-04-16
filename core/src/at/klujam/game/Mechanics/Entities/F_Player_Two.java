package at.klujam.game.Mechanics.Entities;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Veit on 15.04.2016.
 */
public class F_Player_Two extends F_Entity{

    public F_Player_Two(Vector2 position, FightWorld world) {
        super(position, world);
        this.abilities.add(new Attack("Attack",world,this));
        this.abilities.add(new Attack("Charm",world,this));
        this.abilities.add(new Attack("Insult",world,this));
        this.abilities.add(new Attack("DoStuff",world,this));
        this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/goblin_2.png");
    }
}

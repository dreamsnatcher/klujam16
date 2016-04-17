package at.klujam.game.Mechanics.Entities;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;
import at.klujam.game.Mechanics.Fighting.Heal;
import at.klujam.game.Mechanics.Fighting.HeavyAttack;
import at.klujam.game.Mechanics.Fighting.Insult;
import at.klujam.game.Mechanics.Fighting.Penetrate;

import com.badlogic.gdx.math.Vector2;


/**
 * Created by Veit on 15.04.2016.
 */
public class F_Player_One extends F_Entity{

    public F_Player_One(Vector2 position, FightWorld world) {
        super(position, world);
        baseDamage = 20;
        armor = 2;
        this.abilities.add(new Attack("Attack",world,this,1));
        this.abilities.add(new Insult("Insult",world,this,1));
        this.abilities.add(new Penetrate("Pierce",world,this,1));
        this.abilities.add(new HeavyAttack("Wraaagh",world,this,1));

        this.texture = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/gobo_1_big_000.png");
        animation = world.fightingSceneScreen.parentGame.getAnimator().loadAnimation("gameplay/gobo_1_big_00", 2, 0.3f);
    }
}

package at.klujam.game.Mechanics.Entities;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;


/**
 * Created by Veit on 15.04.2016.
 */
public class F_Player_One extends F_Entity{

    public F_Player_One(Vector2 position, FightWorld world) {
        super(position, world);
        this.abilities.add(new Attack("Attack",world,this));
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);
    }
}

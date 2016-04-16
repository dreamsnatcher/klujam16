package at.klujam.game.Mechanics.Actors;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lknoch on 16.04.16.
 */
public class Mask extends F_Entity{
    public static final int ROOM = 0;
    public static final int CARPET = 0;
    private final Texture texture;
    private final Vector2 position;
    private final Vector2 dimension;
    private final FightWorld fightWorld;
    private final int type;

    public Mask(Vector2 position, Vector2 dimension, FightWorld fightWorld,int type) {
        super(position,fightWorld);
        this.position = position;
        this.dimension = dimension;
        this.fightWorld = fightWorld;
        this.type = type;
        if(type == ROOM){
            texture = fightWorld.fightingSceneScreen.parentGame.getAssMan().get("graphics/room.png");
        }else {
            texture = fightWorld.fightingSceneScreen.parentGame.getAssMan().get("graphics/paper.png");
        }
    }


    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
                spriteBatch.draw(texture, position.x, position.y, dimension.x, dimension.y);
        }
}

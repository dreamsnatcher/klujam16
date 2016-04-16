package at.klujam.game.Mechanics.Actors;

import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lknoch on 16.04.16.
 */
public class Mask extends GameObject{
    private final Texture texture;
    private final Vector2 position;
    private final Vector2 dimension;
    private final FightWorld fightWorld;

    public Mask(Vector2 position, Vector2 dimension, FightWorld fightWorld) {
        super(position,dimension);
        this.position = position;
        this.dimension = dimension;
        this.fightWorld = fightWorld;
        texture = fightWorld.fightingSceneScreen.parentGame.getAssMan().get("gameplay/floor.png");
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        for(int i = (int) position.x; i< dimension.x+position.x; i+=(dimension.x/(float)texture.getWidth())) {
            for (int j = (int) position.y; j < dimension.y + position.y; j += (dimension.y / (float) texture.getHeight())) {
                spriteBatch.draw(texture, i, j, (dimension.x / (float) texture.getWidth()), (dimension.y / (float) texture.getHeight()));
            }
        }
    }
}

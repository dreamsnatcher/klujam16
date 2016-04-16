package at.klujam.game.Mechanics.Actors;

import at.klujam.game.Mechanics.FightWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by lknoch on 16.04.16.
 */
public class Mask extends Actor {
    private final Texture texture;
    private final Vector2 position;
    private final Vector2 dimension;
    private final FightWorld fightWorld;

    public Mask(Vector2 position, Vector2 dimension, FightWorld fightWorld) {
        this.position = position;
        this.dimension = dimension;
        this.fightWorld = fightWorld;
        texture = fightWorld.fightingSceneScreen.parentGame.getAssMan().get("gameplay/floor.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(int i = (int) position.x; i< dimension.x+position.x; i+=(dimension.x/(float)texture.getWidth())){
            for(int j = (int) position.y; j< dimension.y+position.y; j+=(dimension.y/(float) texture.getHeight())) {
                batch.draw(texture,i,j,(dimension.x/(float)texture.getWidth()),(dimension.y/(float) texture.getHeight()));
            }
        }
    }
}

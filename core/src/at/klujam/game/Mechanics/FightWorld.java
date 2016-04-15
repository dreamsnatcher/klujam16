package at.klujam.game.Mechanics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.screens.FightingSceneScreen;
import at.klujam.game.screens.GameplayScreen;

/**
 * Created by Veit on 15.04.2016.
 */
public class FightWorld {
    private SpriteBatch spriteBatch;
    public Array<F_Entity> f_entities;
    public FightingSceneScreen fightingSceneScreen;

    public FightWorld(FightingSceneScreen fightingSceneScreen) {
        spriteBatch = new SpriteBatch();
        f_entities = new Array<F_Entity>();
        this.fightingSceneScreen=fightingSceneScreen;

        //Add SkeletonControlledObject
        //skeletonControlledObject = new SkeletonControlledObject(new Vector2(0f,0f),this);
        //f_entities.add(skeletonControlledObject);

    }

    public void update(float delta) {
        for (F_Entity go: f_entities) {
            go.update(delta);
        }
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (F_Entity go: f_entities) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();
    }

    // public void touch(Vector3 touchCoords) {
    //    skeletonControlledObject.touch(touchCoords);
    //}
}

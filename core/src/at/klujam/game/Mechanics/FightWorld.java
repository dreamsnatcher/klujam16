package at.klujam.game.Mechanics;

import at.klujam.game.Mechanics.Entities.F_Entity;
import at.klujam.game.Mechanics.Entities.F_Player_One;
import at.klujam.game.Mechanics.Entities.F_Player_Two;
import at.klujam.game.screens.FightingSceneScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Veit on 15.04.2016.
 */
public class FightWorld {
    private SpriteBatch spriteBatch;
    public Array<F_Entity> f_entities;
    public FightingSceneScreen fightingSceneScreen;
    public F_Entity playerOne;
    public F_Entity playerTwo;

    public FightWorld(FightingSceneScreen fightingSceneScreen) {
        spriteBatch = new SpriteBatch();
        f_entities = new Array<F_Entity>();
        this.fightingSceneScreen=fightingSceneScreen;


        //Add SkeletonControlledObject
        //skeletonControlledObject = new SkeletonControlledObject(new Vector2(0f,0f),this);
        //f_entities.add(skeletonControlledObject);
        playerOne = new F_Player_One(new Vector2(100, 400), this);
        f_entities.add(playerOne);
        playerTwo = new F_Player_Two(new Vector2(200, 400), this);
        f_entities.add(playerTwo);

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

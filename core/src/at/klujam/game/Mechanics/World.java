package at.klujam.game.Mechanics;

import at.klujam.game.Mechanics.Entities.GameObject;
import at.klujam.game.Mechanics.Entities.SkeletonControlledObject;
import at.klujam.game.Mechanics.Entities.Wall;
import at.klujam.game.screens.GameplayScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Veit on 06.02.2016.
 */
public class World {
    private SpriteBatch spriteBatch;
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    public com.badlogic.gdx.physics.box2d.World b2dWorld;
    SkeletonControlledObject skeletonControlledObject;

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;
        b2dWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0),false); //TODO if performance problems, do sleep

        //Add SkeletonControlledObject
        skeletonControlledObject = new SkeletonControlledObject(new Vector2(0f,0f),this);
        gameObjects.add(skeletonControlledObject);
        gameObjects.add(new Wall(new Vector2(200,200),new Vector2(64,64),this));

    }

    public void update(float delta) {
        for (GameObject go: gameObjects) {
            go.update(delta);
        }
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (GameObject go: gameObjects) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();
    }

    public void touch(Vector3 touchCoords) {
        skeletonControlledObject.touch(touchCoords);
    }
}

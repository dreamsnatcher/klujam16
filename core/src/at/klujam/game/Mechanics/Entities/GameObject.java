package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.States.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Veit on 06.02.2016.
 */
public abstract class GameObject {

    Vector2 position;
    Rectangle bounds;
    Texture texture;
    Array<State> states;
    Vector2 dimension, scale;

    public GameObject(Vector2 position, Vector2 dimension) {
        this.position = position;
        this.dimension = dimension;
        this.scale = new Vector2(1, 1);
        states = new Array<State>();
    }

    public void removeState(State state) {
        this.states.removeValue(state, false);
    }

    public void addState(State state) {
        if (state.stackable) {
            states.add(state);
        } else if (!states.contains(state, false)) {
            states.add(state);
        }
    }

    public void update(float delta) {
        for (State state : states) {
            state.update(delta);
        }
    }

    public abstract void render(float delta, SpriteBatch spriteBatch);
}

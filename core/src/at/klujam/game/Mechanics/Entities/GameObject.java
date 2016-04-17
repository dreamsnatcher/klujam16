package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.States.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Veit on 06.02.2016.
 */
public abstract class GameObject {

    public Vector2 position;
    public Rectangle bounds;
    protected Animation idleAnimation;
    TextureRegion tr;
    Array<State> states;
    Vector2 dimension, scale;
    Type type;
    Float stateTime;

    public GameObject(Vector2 position, Vector2 dimension) {
        this.position = position;
        this.dimension = dimension;
        this.scale = new Vector2(1, 1);
        states = new Array<State>();
        stateTime = 0f;
    }

    public Type getType() {
        return type;
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
        if (((double) stateTime + (double) delta) >= Float.MAX_VALUE) {
            stateTime = 0f;
        }
        stateTime += delta;
        for (State state : states) {
            state.update(delta);
        }
    }

    public void render(float delta, SpriteBatch spriteBatch) {
        if (idleAnimation != null) {
            tr = idleAnimation.getKeyFrame(stateTime, true);
        }
        spriteBatch.draw(tr, position.x - dimension.x / 2f, position.y - dimension.y / 2f);
    }

    public enum Type {
        Wall,
        Player,
        Tooth1,
        Tooth2,
        Goal,
        Boss
    }
}

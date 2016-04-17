package at.klujam.game.Mechanics.Entities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Veit on 07.02.2016.
 */
public abstract class MoveableObject extends GameObject {
    public Vector2 direction;
    Float speed;
    Movement movement;

    public MoveableObject(Vector2 position, Vector2 dimension) {
        super(position, dimension);
        movement = Movement.IDLE;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
    }

    void handleMovement(Float delta) {
        this.position.add(direction.nor().scl(speed * delta));
        if (!(direction.nor().scl(speed * delta).isZero())) {
            changeMovementTo(Movement.MOVING);
        } else {
            changeMovementTo(Movement.IDLE);
        }
    }

    public void changeMovementTo(Movement movement) {
        if (this.movement != movement) {
            this.movement = movement;
            stateTime = 0f;
        }
    }

    public enum Movement {
        IDLE, MOVING
    }
}

package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall extends GameObject {

    public Wall(Vector2 position, Vector2 dimension, World world) {
        super(position, dimension);
        texture = world.gameplayScreen.parentGame.getAssMan().get("walls/wall_line_1.png");
        bounds = new Rectangle(position.x, position.y, scale.x, scale.y);
        type = Type.Wall;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, position.x - dimension.x / 2f, position.y - dimension.y / 2f);
    }
}

package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.Attack;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lknoch on 16.04.16.
 */
public class F_Enemy extends F_Entity {

    public static final int Bitch = 0;
    public static final int PIXIE = 1;
    public static final int UNICORN = 2;
    boolean selectedByOne = false;
    boolean selectedByTwo = false;
    private Texture selector1Textur;
    private Texture selector2Textur;

    public F_Enemy(Vector2 position, FightWorld world, int type) {
        super(position, world);
        this.abilities.add(new Attack("Attack",world,this));
        this.abilities.add(new Attack("Insult",world,this));
        this.abilities.add(new Attack("Insult1",world,this));
        this.abilities.add(new Attack("Charm",world,this));
        selector1Textur = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/selected1.png");
        selector2Textur = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/selected2.png");
        switch (type){
            case Bitch:
                this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/bitch_butterfly.png");
                break;
            case UNICORN:
                this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/unicorn.png");
                break;
            case PIXIE:
                this.texture = world.fightingSceneScreen.parentGame.getAssetManager().get("gameplay/pixie_pixelated.png");
                break;
        }
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);
        if(selectedByTwo){
            spriteBatch.draw(selector1Textur, position.x + 18, position.y);

        }
        if (selectedByOne){
            spriteBatch.draw(selector2Textur, position.x, position.y);
        }

    }

    public void SelectPlayerOne(boolean b) {
        selectedByOne = b;
    }

    public void SelectPlayerTwo(boolean b) {
        selectedByTwo = b;
    }
}

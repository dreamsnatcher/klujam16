package at.klujam.game.Mechanics.Entities;

import at.klujam.game.Mechanics.FightWorld;
import at.klujam.game.Mechanics.Fighting.F_Ability;
import at.klujam.game.Mechanics.States.F_Dead;
import at.klujam.game.Mechanics.States.F_State;
import at.klujam.game.screens.GameplayScreen;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veit on 15.04.2016.
 */
public abstract class F_Entity {

    public static final Color NOT_GREEN = new Color(11, 110, 29, 1);
    private final GlyphLayout glyphLayout;
    public int hitpoints = 100;
    public float resistence = 0;
    public float baseDamage = 1;
    public List<F_Ability> abilities = new ArrayList<F_Ability>();
    public Vector2 position;
    public int armor;
    protected Animation animation;
    protected F_Entity forcedEntity;
    boolean selectedByOne = false;
    boolean selectedByTwo = false;
    Rectangle bounds;
    FightWorld world;
    Texture texture;
    Array<F_State> states;
    Animation dyingAnimation;
    float dyingAnimationdelta = 0;
    float scale = 1;
    private Texture selector1Textur;
    private Texture selector2Textur;
    private BitmapFont numberFont;
    private float showStateDuration = 0;
    private Color stateColor;
    private String stateString;
    private float animate = 0;
    private float nextAnimation = MathUtils.random(0, 15);


    public F_Entity(Vector2 position, FightWorld world) {
        this.position = position;
        this.world = world;
        states = new Array<F_State>();

        selector1Textur = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/selected1.png");
        selector2Textur = world.fightingSceneScreen.parentGame.getAssMan().get("gameplay/selected2.png");
        numberFont = world.fightingSceneScreen.parentGame.getAssMan().get("fonts/celtic_small.fnt");
        if (this instanceof F_Enemy) {
            numberFont = world.fightingSceneScreen.parentGame.getAssMan().get("fonts/celtic_even_smaller.fnt");
        }

        glyphLayout = new GlyphLayout();
    }

    public void removeState(F_State state) {
        this.states.removeValue(state, false);
    }

    public void addState(F_State state) {
        if (state.stackable) {
            states.add(state);
        } else if (!states.contains(state, false)) {
            states.add(state);
        }
    }

    public void update(float delta) {
        for (F_State state : states) {
            state.update(delta);
        }
    }

    ;

    public void doAnimation() {
        animate = 0;
    }

    public void render(float delta, SpriteBatch spriteBatch) {


        if (nextAnimation <= 0) {
            doAnimation();
            nextAnimation = MathUtils.random(3f, 20f);
        }
        nextAnimation -= delta;

        if (isDead() && dyingAnimation != null) {
            TextureRegion texture = dyingAnimation.getKeyFrame(dyingAnimationdelta, false);
            spriteBatch.draw(texture, position.x, position.y);
            dyingAnimationdelta++;
        } else if (animation != null && animate <= animation.getAnimationDuration()) {
            TextureRegion texture = animation.getKeyFrame(animate, true);
            spriteBatch.draw(texture, position.x, position.y);
            animate += delta;
        } else {
            spriteBatch.draw(texture, position.x, position.y);
        }
        if (selectedByTwo) {
            spriteBatch.draw(selector1Textur, position.x + texture.getWidth() / 2 + 9, position.y + texture.getHeight());

        }
        if (selectedByOne) {
            spriteBatch.draw(selector2Textur, position.x + texture.getWidth() / 2 - 9, position.y + texture.getHeight());
        }
        if (hitpoints < 10) {
            numberFont.setColor(Color.GOLD);
        } else {
            numberFont.setColor(Color.RED);
        }

        float sumWidth = 0;
        String hitPoints = "";
        if (hitpoints <= 0) {
            hitPoints = "Dead";
        } else {
            hitPoints = "HP: " + hitpoints;
        }
        glyphLayout.setText(numberFont, hitPoints);
        sumWidth += 10;
        sumWidth += glyphLayout.width;

        float apOffset = sumWidth;

        String armorString = "AP: " + armor;
        glyphLayout.setText(numberFont, armorString);
        sumWidth += glyphLayout.width;
        sumWidth += 10;
        float atOffset = sumWidth;

        String attack = "At: " + baseDamage;
        glyphLayout.setText(numberFont, attack);
        sumWidth += glyphLayout.width;

        numberFont.draw(spriteBatch, hitPoints, position.x + (texture.getWidth() / 2f) - sumWidth / 2f, position.y - numberFont.getXHeight() + 10);

        numberFont.setColor(Color.GOLDENROD);
        numberFont.draw(spriteBatch, armorString, position.x + (texture.getWidth() / 2f) - sumWidth / 2f + apOffset, position.y - numberFont.getXHeight() + 10);

        numberFont.setColor(NOT_GREEN);
        numberFont.draw(spriteBatch, attack, position.x + (texture.getWidth() / 2f) - sumWidth / 2f + atOffset, position.y - numberFont.getXHeight() + 10);


        if (showStateDuration > 0) {
            if (showStateDuration < 1) {
                stateColor.a = showStateDuration;
            }
            numberFont.setColor(stateColor);

            numberFont.draw(spriteBatch, stateString, position.x, position.y + texture.getHeight() + 20);
            showStateDuration -= delta;
        }

    }

    public void SetStateText(Color color, String text, float duration) {
        this.stateColor = new Color(color);
        this.stateString = text;
        this.showStateDuration = duration;
    }

    public void inflict_damage(float damage) {
        if (damage - armor > 0) {
            this.hitpoints -= (damage - armor);
        }
        if (this.hitpoints <= 0) {
            this.addState(new F_Dead(0, this, world));
            if (this instanceof F_Enemy) {
                if (((F_Enemy) this).type == F_Enemy.BOSS) {
                    Screen screen = world.fightingSceneScreen.parentGame.getScreenManager().getLastScreen();
                    if (screen instanceof GameplayScreen) {
                        ((GameplayScreen) screen).world.boss.setDead(true);
                    }
                }
            }
        }
    }

    public void nextRound() {
        //TODO
    }

    public boolean isDead() {
        boolean dead = false;
        for (F_State state : states) {
            if (state instanceof F_Dead) {
                return true;
            }
        }
        return false;
    }


    public void SelectPlayerOne(boolean b) {
        selectedByOne = b;
    }

    public void SelectPlayerTwo(boolean b) {
        selectedByTwo = b;
    }

    //TODO @Veit: I did it!
    public void forceTarget(F_Entity origin) {
        forcedEntity = origin;
    }


    public void heal(float v) {
        this.hitpoints += v;
    }
}

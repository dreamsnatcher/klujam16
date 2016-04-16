package at.klujam.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private AssetManager assMan;
	private ScreenManager screenManager;
	private SoundManager soundManager;
	private Animator animator;

	// gives the original size for all screen working with the scaling orthographic camera
	// set in DesktopLauncher to any resolution and it will be scaled automatically.
	public static final int GAME_WIDTH = 1920;
	public static final int GAME_HEIGHT = 1080;

	@Override
	public void create() {
		screenManager = new ScreenManager(this);
		soundManager = new SoundManager(this);
		animator = new Animator(this);

		// LOAD ASSETS HERE ...
		// Loading screen will last until the last one is loaded.
		assMan = new AssetManager();
		// for the menu
		assMan.load("menu/Ravie_42.fnt", BitmapFont.class);
		assMan.load("menu/Ravie_72.fnt", BitmapFont.class);
		assMan.load("fonts/celtic.fnt", BitmapFont.class);
        assMan.load("fonts/celtic_small.fnt", BitmapFont.class);
        assMan.load("fonts/celtic_even_smaller.fnt", BitmapFont.class);
		assMan.load("menu/menu_background.jpg", Texture.class);
		// for the credits
		assMan.load("credits/gradient_top.png", Texture.class);
		assMan.load("credits/gradient_bottom.png", Texture.class);
		// for the sounds
		assMan.load("sfx/blip.wav", Sound.class);
		assMan.load("sfx/explosion.wav", Sound.class);
		assMan.load("sfx/hit.wav", Sound.class);
		assMan.load("sfx/jump.wav", Sound.class);
		assMan.load("sfx/laser.wav", Sound.class);
		assMan.load("sfx/pickup.wav", Sound.class);
		assMan.load("sfx/powerup.wav", Sound.class);

        assMan.load("speech/Hokus-fight-start.mp3", Sound.class);
        assMan.load("speech/Pokus-attack01.mp3", Sound.class);
        assMan.load("speech/Pokus-fight-start.mp3", Sound.class);


        assMan.load("music/song1.mp3", Music.class);
        assMan.load("music/song2.mp3", Music.class);

		//Entities
		assMan.load("gameplay/spritesheet.png", Texture.class);
		assMan.load("gameplay/movingAnimation_Down.png", Texture.class);

		assMan.load("gameplay/gobo_1__000.png", Texture.class);
		assMan.load("gameplay/gobo_1__001.png", Texture.class);
		assMan.load("gameplay/gobo_1__002.png", Texture.class);
		assMan.load("gameplay/gobo_1__003.png", Texture.class);
		assMan.load("gameplay/gobo_1__004.png", Texture.class);
		assMan.load("gameplay/gobo_1__005.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_000.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_001.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_002.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_003.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_004.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_005.png", Texture.class);
		assMan.load("gameplay/gobo_1_back_006.png", Texture.class);
		assMan.load("gameplay/gobo_1_right_000.png", Texture.class);
		assMan.load("gameplay/gobo_1_right_001.png", Texture.class);
		assMan.load("gameplay/gobo_1_right_002.png", Texture.class);
		assMan.load("gameplay/gobo_1_right_003.png", Texture.class);
		assMan.load("gameplay/gobo_1_right_004.png", Texture.class);
		assMan.load("gameplay/gobo_1_right_005.png", Texture.class);
		assMan.load("gameplay/gobo_1_big_000.png", Texture.class);
		assMan.load("gameplay/gobo_1_big_001.png", Texture.class);
		assMan.load("gameplay/gobo_1_big_002.png", Texture.class);

		assMan.load("gameplay/gobo_2_000.png", Texture.class);
		assMan.load("gameplay/gobo_2_001.png", Texture.class);
		assMan.load("gameplay/gobo_2_002.png", Texture.class);
		assMan.load("gameplay/gobo_2_003.png", Texture.class);
		assMan.load("gameplay/gobo_2_004.png", Texture.class);
		assMan.load("gameplay/gobo_2_005.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_000.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_001.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_002.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_003.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_004.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_005.png", Texture.class);
		assMan.load("gameplay/gobo_2_back_006.png", Texture.class);
		assMan.load("gameplay/gobo_2_right_000.png", Texture.class);
		assMan.load("gameplay/gobo_2_right_001.png", Texture.class);
		assMan.load("gameplay/gobo_2_right_002.png", Texture.class);
		assMan.load("gameplay/gobo_2_right_003.png", Texture.class);
		assMan.load("gameplay/gobo_2_right_004.png", Texture.class);
		assMan.load("gameplay/gobo_2_left_000.png", Texture.class);
		assMan.load("gameplay/gobo_2_left_001.png", Texture.class);
		assMan.load("gameplay/gobo_2_left_002.png", Texture.class);
		assMan.load("gameplay/gobo_2_left_003.png", Texture.class);
		assMan.load("gameplay/gobo_2_left_004.png", Texture.class);
		assMan.load("gameplay/gobo_2_left_005.png", Texture.class);
		assMan.load("gameplay/gobo_2_big_000.png", Texture.class);
		assMan.load("gameplay/gobo_2_big_001.png", Texture.class);
		assMan.load("gameplay/gobo_2_big_002.png", Texture.class);

		assMan.load("buttons/button_wood_active.png", Texture.class);
		assMan.load("buttons/button_wood_idle.png", Texture.class);
		assMan.load("gameplay/goblin_1.png", Texture.class);
		assMan.load("gameplay/goblin_2.png", Texture.class);
		assMan.load("gameplay/bitch_butterfly.png", Texture.class);
		assMan.load("gameplay/unicorn.png", Texture.class);
		assMan.load("gameplay/pixie_pixelated.png", Texture.class);
		assMan.load("walls/wall_line_1.png", Texture.class);
		assMan.load("gameplay/floor.png", Texture.class);
		assMan.load("gameplay/gobo_1_big_000.png", Texture.class);


		//Stuff
		assMan.load("gameplay/selected1.png", Texture.class);
		assMan.load("gameplay/selected2.png", Texture.class);

		//assMan.load("fonts/font.fnt", BitmapFont.class);
	}

	@Override
	public void render() {
		screenManager.getCurrentScreen().render(Gdx.graphics.getDeltaTime());
	}

	public AssetManager getAssMan() {
		return assMan;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public Animator getAnimator() {
		return animator;
	}
}

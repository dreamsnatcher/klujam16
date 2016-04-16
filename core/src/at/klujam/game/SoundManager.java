package at.klujam.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

/**
 * Created by Mathias Lux, mathias@juggle.at, 05.02.2016.
 */
public class SoundManager {
    private Game parentGame;
    private HashMap<String, String> event2sound;
    private HashMap<String, String> event2music;
    private HashMap<String, String> event2speech;
    private Music currentMusic;


    public SoundManager(Game parentGame) {
        this.parentGame = parentGame;

        // register the available events.
        event2sound = new HashMap<String, String>(20);
        event2sound.put("blip", "sfx/blip.wav");
        event2sound.put("explode", "sfx/explosion.wav");
        event2sound.put("hit", "sfx/hit.wav");
        event2sound.put("jump", "sfx/jump.wav");
        event2sound.put("laser", "sfx/laser.wav");
        event2sound.put("pickup", "sfx/pickup.wav");
        event2sound.put("powerup", "sfx/powerup.wav");
        event2sound.put("rage", "sfx/rage.wav");

        event2speech = new HashMap<String, String>(20);
        event2speech.put("hokus_attack", "speech/Pokus-attack01.mp3");
        event2speech.put("pokus_attack", "speech/Pokus-attack01.mp3");
        event2speech.put("hokus_fight_start", "speech/Hokus-fight-start.mp3");
        event2speech.put("pokus_fight_start", "speech/Pokus-fight-start.mp3");

        event2music = new HashMap<String, String>(20);
        event2music.put("song1", "music/song1.mp3");
        event2music.put("song2", "music/song2.mp3");

        //event2speech.put("song1", "music/blip.wav");
        //event2speech.put("song2", "music/explosion.wav");
    }

    /**
     * Plays an event registered in the constructor. Make sure that (i) the event is known and (ii) the
     * asset is loaded in the constructor of Game.
     *
     * @param event
     */
    public void playEvent(String event) {
        if (event2sound.get(event) != null) {
            parentGame.getAssMan().get(event2sound.get(event), Sound.class).play();
        } else {
            System.err.println("Event unknown.");
        }
    }

    public void playSpeech(String speech) {
        if (event2speech.get(speech) != null) {
            parentGame.getAssMan().get(event2speech.get(speech), Sound.class).play();
        } else {
            System.out.println(speech);
            System.err.println("Speech unknown.");
        }
    }

    public void playMusic(String music) {
        if (event2music.get(music) != null) {
            if(currentMusic!=null)
                currentMusic.stop();
            currentMusic = parentGame.getAssMan().get(event2music.get(music), Music.class);
            currentMusic.setLooping(true);
            currentMusic.play();
        } else {
            System.err.println("Music unknown.");
        }
    }

    public void stopMusic(){
        if(currentMusic!=null)
            currentMusic.stop();
    }
}

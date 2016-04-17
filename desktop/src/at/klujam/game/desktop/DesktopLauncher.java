package at.klujam.game.desktop;

import at.klujam.game.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 1920;
        config.width = 1080;
        config.fullscreen = false;
        new LwjglApplication(new Game(), config);
    }
}

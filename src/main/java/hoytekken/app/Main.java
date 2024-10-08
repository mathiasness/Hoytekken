package hoytekken.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Main class for the game
 * runs the Hoytekken Game application
 */
public class Main {
    private static final int WINDOW_WIDTH = 1920;
    private static final int WINDOW_HEIGHT = 1280;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("hoytekken");
        cfg.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);

        new Lwjgl3Application(new Hoytekken(), cfg);
    }
}

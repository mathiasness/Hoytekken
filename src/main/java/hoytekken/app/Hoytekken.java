package hoytekken.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hoytekken.app.controller.HtekkenController;
import hoytekken.app.model.HTekkenModel;
import hoytekken.app.model.components.eventBus.EventBus;
import hoytekken.app.view.screens.MenuScreen;
import hoytekken.app.view.screens.SelectionScreen;

/**
 * ApplicationListener/Game class for the game
 * connects the model, controller and the view
 */
public class Hoytekken extends Game {
    public static final int TILE_SIZE = 32;
    public static final int V_WIDTH = TILE_SIZE * 30;
    public static final int V_HEIGHT = TILE_SIZE * 15;
    public static final float PPM = 100;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        HTekkenModel model = new HTekkenModel(new EventBus());
        new HtekkenController(model);

        setScreen(new MenuScreen(this, model));
        //setScreen(new SelectionScreen(this, model));
    }
}

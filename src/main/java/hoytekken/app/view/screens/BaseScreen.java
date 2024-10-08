package hoytekken.app.view.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hoytekken.app.Hoytekken;
import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.GameStateEvent;
import hoytekken.app.model.components.eventBus.IEvent;
import hoytekken.app.model.components.eventBus.IEventListener;
import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.view.ViewableModel;

/**
 * Base class for all screens.
 */
public abstract class BaseScreen implements Screen, IEventListener {
    protected Hoytekken game;
    protected ViewableModel model;

    protected OrthographicCamera gameCam;
    protected FitViewport gamePort;

    /**
     * Constructor for the base screen. Initializes the following:
     * <ul>
     *   <li>{@code this.game} Hoytekken</li>
     *   <li>{@code this.model} ViewableModel</li>
     *   <li>{@code this.gameCam} OrthographicCamera</li>
     *   <li>{@code this.gamePort} FitViewport</li>
     * </ul>
     * @param game the game object
     * @param model the viewable model
     * @param scaling whether the viewport should scale
     */
    public BaseScreen(Hoytekken game, ViewableModel model, boolean scaling) {
        this.game = game;
        this.model = model;

        initializeCameraAndViewport(scaling);

        this.model.getEventBus().addListener(this);
    }

    /**
     * Constructor for the base screen. Initializes the following:
     * <ul>
     *   <li>{@code this.game} Hoytekken</li>
     *   <li>{@code this.model} ViewableModel</li>
     *   <li>{@code this.gameCam} OrthographicCamera</li>
     *   <li>{@code this.gamePort} FitViewport, (ViewPort not scaled to {@code Hoytekken.PPM})</li>
     * </ul>
     * @param game the game object
     * @param model the viewable model
     */
    public BaseScreen(Hoytekken game, ViewableModel model) {
        this(game, model, false);
    }

    @Override
    public void handleEvent(IEvent event) {
        if (event instanceof GameStateEvent) {
            handleStateSwitch((GameStateEvent) event);
        }
    }

    private int getWinningPlayer() throws IllegalStateException{
        boolean playerOneWon = model.getPlayer(PlayerType.PLAYER_ONE).isAlive();
        boolean playerTwoWon = model.getPlayer(PlayerType.PLAYER_TWO).isAlive();

        if (playerOneWon && playerTwoWon) throw new IllegalStateException("Both players cannot win at the same time");
        if (!playerOneWon && !playerTwoWon) throw new IllegalStateException("No player has won");

        return playerOneWon && !playerTwoWon ? 1 : 2;
    }

    /**
     * Initializes the camera and viewport.
     * @param scaling whether the viewport should scale
     */
    protected void initializeCameraAndViewport(boolean scaling) {
        gameCam = new OrthographicCamera();
        if (scaling) gamePort = new FitViewport(Hoytekken.V_WIDTH/Hoytekken.PPM, Hoytekken.V_HEIGHT/Hoytekken.PPM, gameCam);
        else gamePort = new FitViewport(Hoytekken.V_WIDTH, Hoytekken.V_HEIGHT, gameCam);
    }

    /**
     * Handles the state switch.
     * If the game state has changed, switches to the appropriate screen.
     */
    protected void handleStateSwitch(GameStateEvent event) {
        GameState newState = event.newState();

        switch (newState) {
            case MAIN_MENU:
                if (!(this instanceof MenuScreen)) {
                    model.getEventBus().removeListener(this);
                    game.setScreen(new MenuScreen(game, model));
                }
                break;
            case SELECTION:
                if (!(this instanceof SelectionScreen)) {
                    model.getEventBus().removeListener(this);
                    game.setScreen(new SelectionScreen(game, model));
                }
                break;
            case INSTRUCTIONS:
                if (!(this instanceof InstructionsScreen)) {
                    model.getEventBus().removeListener(this);
                    game.setScreen(new InstructionsScreen(game, model));
                }
                break;
            case ACTIVE_GAME:
                if (!(this instanceof GameScreen)) {
                    model.getEventBus().removeListener(this);
                    game.setScreen(new GameScreen(game, model));
                }
                break;
            case GAME_OVER:
                if (!(this instanceof GameOverScreen)) {
                    int winningPlayer = getWinningPlayer();
                    model.getEventBus().removeListener(this);
                    game.setScreen(new GameOverScreen(game, model, winningPlayer));
                }
                break;
            default:
                break;
        }
    }

    /**
     * Updates the screen's state and handles transitions based on the game's current state.
     * This includes:
     * <ul>
     *   <li>Updating the camera's position and properties.</li>
     *   <li>Handling state transitions, such as switching screens when the game state changes.</li>
     * </ul>
     * Subclass {@code GameScreen}, should override this method to provide specific update logic.
     *
     * @param delta The time in seconds since the last frame was rendered.
     */
    protected void update(float delta) {
        this.gameCam.update();
        //handleStateSwitch();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {
        this.gamePort.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
    
}

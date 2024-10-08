package hoytekken.app.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import hoytekken.app.model.components.ForceDirection;
import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.ClickedScreenEvent;
import hoytekken.app.model.components.player.AIPlayer;
import hoytekken.app.model.components.player.PlayerType;

/**
 * Controller for the game
 * handles input from the user
 */
public class HtekkenController extends InputAdapter {
    ControllableModel model;

    // Player types
    private final PlayerType playerOne = PlayerType.PLAYER_ONE;
    private final PlayerType playerTwo = PlayerType.PLAYER_TWO;

    /**
     * Constructor for the controller
     * keeps model and sets input processor for the game
     * 
     * @param model the model to control
     */
    public HtekkenController(ControllableModel model) {
        this.model = model;
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Handles key down events
     * 
     * @param keycode the key code of the key that was pressed
     * @return true if the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return switch (model.getGameState()) {
            case ACTIVE_GAME -> handleActiveGameInput(keycode);
            case MAIN_MENU -> handleMainMenuInput(keycode);
            default -> false;
        };

    }

    /**
     * Handles key code events for the main menu state
     * 
     * @param keycode id of key
     */
    private boolean handleMainMenuInput(int keycode) {
        if (keycode == Input.Keys.I) {model.setGameState(GameState.INSTRUCTIONS);}
        else if (keycode == Input.Keys.ESCAPE) {Gdx.app.exit();}
        else {return false;}
        return true;
    }

    /**
     * Handles key code events for the active game state
     * 
     * @param keycode id of key
     */
    private boolean handleActiveGameInput(int keycode) {
        boolean p1handled = handleP1Input(keycode);
        boolean p2handled = false;
        
        if (!(model.getPlayer(playerTwo) instanceof AIPlayer)) 
            p2handled = handleP2Input(keycode);

        return p1handled || p2handled;
    }

    // Input keys for player 1
    private boolean handleP1Input(int keycode) {
        return switch (keycode) {
            case Input.Keys.LEFT -> model.setDirection(playerOne, ForceDirection.LEFT);
            case Input.Keys.RIGHT -> model.setDirection(playerOne, ForceDirection.RIGHT);
            case Input.Keys.UP -> model.jump(playerOne);
            case Input.Keys.P -> model.performAttackAction(playerOne, ActionType.PUNCH);
            case Input.Keys.K -> model.performAttackAction(playerOne, ActionType.KICK);
            case Input.Keys.DOWN -> model.getPlayer(playerOne).changeBlockingState();
            default -> false;
        };
    }

    // Input keys for player 2
    private boolean handleP2Input(int keycode) {
        return switch (keycode) {
            case Input.Keys.A -> model.setDirection(playerTwo, ForceDirection.LEFT);
            case Input.Keys.D -> model.setDirection(playerTwo, ForceDirection.RIGHT);
            case Input.Keys.W -> model.jump(playerTwo);
            case Input.Keys.Q -> model.performAttackAction(playerTwo, ActionType.PUNCH);
            case Input.Keys.E -> model.performAttackAction(playerTwo, ActionType.KICK);
            case Input.Keys.S -> model.getPlayer(playerTwo).changeBlockingState();
            default -> false;
        };
    }

    @Override
    public boolean keyUp(int keycode) {
        if (model.getGameState() == GameState.ACTIVE_GAME) {
            // Player 1
            // Stop applying force to the player when the key is released
            if (keycode == Input.Keys.LEFT && model.getDirection(playerOne) == ForceDirection.LEFT
                    || keycode == Input.Keys.RIGHT && model.getDirection(playerOne) == ForceDirection.RIGHT) {
                return model.setDirection(playerOne, ForceDirection.STATIC);
            }

            // Deactivate block when DOWN-key is released
            if (keycode == Input.Keys.DOWN) {
                return model.getPlayer(playerOne).changeBlockingState();
            }

            // Player2
            // Stop applying force to the player when the key is released
            if (keycode == Input.Keys.A && model.getDirection(playerTwo) == ForceDirection.LEFT
                    || keycode == Input.Keys.D && model.getDirection(playerTwo) == ForceDirection.RIGHT) {
                return model.setDirection(playerTwo, ForceDirection.STATIC);
            }

            // Deactivate block when S-key is released
            if (keycode == Input.Keys.S) {
                return model.getPlayer(playerTwo).changeBlockingState();
            }
        }
        return false;
    }

    /**
     * Handles touch down events based on the current game state
     * 
     * @param screenX the x-coordinate of the touch
     * @param screenY the y-coordinate of the touch
     * @param pointer the pointer of the touch
     * @param button  the button of the touch
     * 
     * @return true if the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (model.getGameState()) {
            case INSTRUCTIONS -> model.setGameState(GameState.MAIN_MENU);
            case MAIN_MENU -> model.setGameState(GameState.SELECTION);
            case GAME_OVER -> Gdx.app.getApplicationListener().create();
            case SELECTION -> this.model.getEventBus().emitEvent(new ClickedScreenEvent(screenX, screenY));
            default -> {return false;}
        }
        return true;
    }

}

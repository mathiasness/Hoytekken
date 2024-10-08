package hoytekken.app.model;

import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.EventBus;

import java.util.HashMap;

/**
 * Interface for methods that are used by both the view and the control
 */
public interface IViewAndControl {

    /**
     * Gets the event bus, stored in model
     * @return the event bus
     */
    EventBus getEventBus();

    /**
     * Method to get the maps for the game
     * 
     * @return the maps for the game
     */
    HashMap<String, String> getGameMaps();

    /**
     * Gets the gamestate that the game is currently in
     * 
     * @return a GameState-object that represents the current gamestate
     */
    GameState getGameState();

    /**
     * Updates the current gamestate
     * 
     * @param gameState is the gamestate that the game gets set to
     */
    void setGameState(GameState gameState);

    /**
     * Method to set the map for the game
     * 
     * @param mapName the name of the map
     */
    void setGameMap(String mapName);

    /**
     * Method sets number of players
     * @param onePlayer true if one player, false if two players
     * @return true if the number of players was set, false otherwise
     */
    boolean setNumberOfPlayers(Boolean onePlayer);
}

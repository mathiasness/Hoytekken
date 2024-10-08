package hoytekken.app.view;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import hoytekken.app.model.IViewAndControl;
import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.player.IPlayer;
import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.model.components.player.ViewablePlayer;
import hoytekken.app.model.components.powerup.ActivePowerUp;
import hoytekken.app.model.components.powerup.PowerUp;

/**
 * Interface for the viewable model
 */
public interface ViewableModel extends IViewAndControl{

    /**
     * Updates the model
     * 
     * @param dt time slice float
     */
    void updateModel(float dt);

    /**
     * Getter for the game world
     * 
     * @return the game world
     */
    World getGameWorld();

    /**
     * Getter for the player
     * 
     * @param player the player number
     * @return the player
     */
    ViewablePlayer getPlayer(PlayerType player);

    /**
     * Getter for the map
     * 
     * @return the map path string
     */
    String getMap();

    /**
     * Getter for the tiled map
     * 
     * @return the tiled map
     */
    TiledMap getTiledMap();

    /**
     * Gets the gamestate that the game is currently in
     * 
     * @return a GameState-object that represents the current gamestate
     */
    GameState getGameState();

    /**
     * Gets the powerup that is currently active
     * 
     * @return the active powerup
     */
    ActivePowerUp getActivePowerUp();

}

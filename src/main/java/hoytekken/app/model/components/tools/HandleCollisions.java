package hoytekken.app.model.components.tools;

import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.model.components.powerup.ActivePowerUp;

/**
 * Interface for collision handling methods in the model
 */
public interface HandleCollisions {

    /**
     * Method to handle the collision between the player and the ground
     * 
     * @return true if the collision was handled, false otherwise
     */
    boolean resetDoubleJump(PlayerType player);


    /**
     * adds a body to the list of bodies that are queued for removal
     */
    void destroyPowerUpList();

    /**
     * Method to apply a power up to the player
     * 
     * @param player the player to apply the power up to
     * @param powerUp the power up to apply
     */
    void applyPowerUp(PlayerType player, ActivePowerUp powerUp);

    /**
     * Method to get the active power up
     * @return the active power up
     */
    ActivePowerUp getActivePowerUp();
}

package hoytekken.app.model.components.player;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface defining common behavior for a player in the game.
 */
public interface IPlayer extends ICombat, IPowerUp, ViewablePlayer {

    /**
     * Updates the player entity's state and position over time.
     * 
     * @param dt The time step since the last update, in seconds.
     */
    void update(float dt);

    /**
     * Retrieves the physical body representing the player.
     * 
     * @return the Box2D body of the player
     */
    Body getBody();

    /**
     * Moves the player by applying a force.
     * 
     * @param deltaX the change in the x direction
     * @param deltaY the change in the y direction
     */
    void move(float deltaX, float deltaY);

    /**
     * Inflicts damage on the player.
     * 
     * @param damage the amount of damage to inflict
     */
    void takeDamage(int damage);

    /**
     * Check if players position is off the map.
     * 
     * @return boolean true, if player is off the map
     */
    boolean fallenOffTheMap();

    /**
     * Retrieves the max health of the player.
     * 
     * @return max health of player
     */
    int getMaxHealth();

    /**
     * Retrieves the kick damage of the player.
     * 
     * @return an int representing the kick damage
     */
    int getKickDamage();

    /**
     * Retrieves the punch damage of the player.
     * 
     * @return an int representing the punch damage
     */
    int getPunchDamage();

    /**
     * Retrieves the jumping height of the player.
     * 
     * @return an int representing the deltaY of a jump
     */
    int getJumpingHeight();

    /**
     * Retrieves the max velocity of the player.
     * 
     * @return a float representing the max velocity of the player
     */
    float getMaxVelocity();

    /**
     * Flips the player image to face left
     */
    void flipLeft();

    /**
     * Flips the player image to face right
     */
    void flipRight();

}

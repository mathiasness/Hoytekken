package hoytekken.app.model.components.player;

/**
 * Interface for the combat actions that a player can perform
 */
public interface ICombat {
    /**
     * A player will punch another player and
     * inflict damage on that player.
     * Will only hit if other player is in range.
     * 
     * @param that the player to punch
     * @return bool on if player was hit
     */
    boolean punch(IPlayer that);

    /**
     * A player will kick another player and
     * inflict damage on that player.
     * Will only hit if other player is in range.
     * 
     * @param that the player to kick
     * @return bool on if player was hit
     */
    boolean kick(IPlayer that);

    /**
     * Toggles the players isBlocking field variable.
     * @return  bool on if it was successful
     */
    boolean changeBlockingState();

    /**
     * Retrieves a boolean representing if player is blocking or not.
     * 
     * @return value of the players isBlocking field variable.
     */
    boolean getIsBlocking();
}

package hoytekken.app.model.components.powerup;

/**
 * Interface for power up factory
 */
public interface PowerUpFactory {
    /**
     * Gets the next power up
     * 
     * @return the power up
     */
    PowerUp getNext();
}

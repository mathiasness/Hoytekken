package hoytekken.app.model.components.player;

public interface IPowerUp {
    /**
     * A player gains an extra life.
     * 
     */
    void gainExtraLife();

    /**
     * A player gets increased damage.
     * 
     * @param increaseAmount the amount of damage that is being increased
     */
    void increaseDamage(int increaseAmount);

    /**
     * A player gets increased speed.
     * 
     * @param increaseAmount the amount of speed that is being increased
     */
    void increaseSpeed(int increaseAmount);

    /**
     * A player gets increased health.
     * 
     * @param increaseAmount the amount of health that is being increased
     */
    void increaseHealth(int increaseAmount);

}

package hoytekken.app.model.components.powerup;

import com.badlogic.gdx.graphics.Texture;

import hoytekken.app.model.components.player.IPlayer;

/**
 * Abstract class for power-ups.
 */
public abstract class PowerUp {
    private final Texture pUpTexture;

    /**
     * Constructor for PowerUp
     * 
     * @param pUpTexture Texture of the power-up.
     */
    PowerUp(Texture pUpTexture) {
        this.pUpTexture = pUpTexture;
    }

    /**
     * Creates a new power-up, without having to create an object.
     * 
     * @param type char to represent the type of power-up.
     * @throws IllegalArgumentException for undefined types.
     * @return A power-up.
     */
    static PowerUp newPowerUp(PowerUpType type) {
        return switch (type) {
            case EXTRA_DAMAGE -> new ExtraDamage();
            case EXTRA_LIFE -> new ExtraLife();
            case DOUBLE_SPEED -> new DoubleSpeed();
            case EXTRA_HEALTH -> new ExtraHealth();
            default -> throw new IllegalArgumentException("Undefined type for PowerUp");
        };
    }

    /**
     * applies the power-up, affecting the player.
     * 
     * @param player Player to affect.
     */
    public abstract void applyPowerUp(IPlayer player);

    /**
     * Gets the texture of the power-up.
     * 
     * @return Texture of the power-up.
     */
    Texture getTexture() {
        return pUpTexture;
    }
}

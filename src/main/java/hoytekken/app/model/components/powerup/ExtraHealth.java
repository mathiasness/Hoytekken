package hoytekken.app.model.components.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import hoytekken.app.model.components.player.IPlayer;

/**
 * Power up that increases the player's health
 */
public class ExtraHealth extends PowerUp {

    private static final Integer HEALTH = 50;

    /**
     * Constructor for ExtraHealth
     */
    ExtraHealth() {
        super(new Texture(Gdx.files.internal("extra_health.png")));
    }

    @Override
    public void applyPowerUp(IPlayer player) {
        player.increaseHealth(HEALTH);
    }

}

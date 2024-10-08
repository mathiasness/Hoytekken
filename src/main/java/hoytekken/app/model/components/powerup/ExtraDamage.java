package hoytekken.app.model.components.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import hoytekken.app.model.components.player.IPlayer;

/**
 * Power up that increases the player's damage
 */
public class ExtraDamage extends PowerUp {
    private static final Integer DAMAGE = 20;

    /**
     * Constructor for ExtraDamage
     */
    ExtraDamage() {
        super(new Texture(Gdx.files.internal("extra_damage.png")));
    }

    @Override
    public void applyPowerUp(IPlayer player) {
        player.increaseDamage(DAMAGE);
    }

}

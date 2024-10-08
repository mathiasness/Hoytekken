package hoytekken.app.model.components.powerup;

import java.util.Random;

/**
 * Random power up factory
 */
public class RandomPowerUpFactory implements PowerUpFactory {

    @Override
    public PowerUp getNext() {
        Random rand = new Random();
        int index = rand.nextInt(PowerUpType.values().length);
        PowerUpType type = PowerUpType.values()[index];
        return PowerUp.newPowerUp(type);
    }
}

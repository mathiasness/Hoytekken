package hoytekken.app.model.components.tools;

import com.badlogic.gdx.physics.box2d.Contact;

import hoytekken.app.model.components.player.PlayerFixtures;
import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.model.components.powerup.ActivePowerUp;

/**
 * Class to detect collisions between objects
 */
public class CollisionDetector extends AbstractCollision {
    /**
     * Constructor for the collision detector
     * 
     * @param model the model to handle the collisions
     */
    public CollisionDetector(HandleCollisions model) {
        super(model);
    }

    /**
     * Handles the beginning of contact between two fixtures.
     * 
     * @param contact the contact information
     */
    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if (userDataA != null && userDataB != null) {
            handlePlayerCollisions(userDataA, userDataB);
            handlePowerUpCollision(userDataA, userDataB);
        }
    }

    /**
     * Determines the type of player based on the user data.
     * 
     * @param userData the user data of the fixture
     * @return the type of player
     */
    private PlayerType getPlayerType(Object userData) {
        return userData.equals(PlayerFixtures.PLAYER_ONE_FEET) ? PlayerType.PLAYER_ONE : PlayerType.PLAYER_TWO;
    }

    /**
     * Determines the player fixture based on the user data.
     * 
     * @param userData data of user
     */
    private PlayerFixtures getPlayerFixture(Object userData) {
        return userData.equals(PlayerFixtures.PLAYER_ONE_FEET) ? PlayerFixtures.PLAYER_ONE_FEET
                : PlayerFixtures.PLAYER_TWO_FEET;
    }

    /**
     * Handles collisions involving player bodies.
     * 
     * @param userDataA the user data of fixture A
     * @param userDataB the user data of fixture B
     */
    private void handlePlayerCollisions(Object userDataA, Object userDataB) {
        PlayerType playerTypeA = this.getPlayerType(userDataA);
        PlayerFixtures playerFixtureA = this.getPlayerFixture(userDataA);

        PlayerType playerTypeB = this.getPlayerType(userDataB);
        PlayerFixtures playerFixtureB = this.getPlayerFixture(userDataB);

        if (userDataA.equals(playerFixtureA) || userDataB.equals(playerFixtureA)) {
            handlePlayerCollision(playerTypeA, playerFixtureA);
        }

        if (userDataA.equals(playerFixtureB) || userDataB.equals(playerFixtureB)) {
            handlePlayerCollision(playerTypeB, playerFixtureB);
        }
    }

    /**
     * Handles collision events involving player bodies.
     * 
     * @param playerType the type of player
     * @param playerBody the body part of the player involved in collision
     */
    private void handlePlayerCollision(PlayerType playerType, PlayerFixtures playerBody) {
        switch (playerBody) {
            case PLAYER_ONE_FEET:
            case PLAYER_TWO_FEET:
                feetTouched(playerType);
                break;
            case PLAYER_ONE_BODY:
            case PLAYER_TWO_BODY:
                break;
            default:
                break;
        }
    }

    /**
     * Resets double jump if player's feet touch something.
     * 
     * @param playerType the type of player
     */
    private void feetTouched(PlayerType playerType) {
        model.resetDoubleJump(playerType);
    }

    /**
     * Handles collisions involving power-ups.
     * 
     * @param userDataA the user data of fixture A
     * @param userDataB the user data of fixture B
     */
    private void handlePowerUpCollision(Object userDataA, Object userDataB) {
        if(userDataA.toString().contains("PLAYER_ONE_BODY") && userDataB.toString().contains("powerUp")
            || userDataB.toString().contains("PLAYER_ONE_BODY") && userDataA.toString().contains("powerUp")) {
            ActivePowerUp powerUp = model.getActivePowerUp();
            model.applyPowerUp(PlayerType.PLAYER_ONE, powerUp);
            model.destroyPowerUpList();

        } else if (userDataA.toString().contains("PLAYER_TWO_BODY") && userDataB.toString().contains("powerUp")
            || userDataB.toString().contains("PLAYER_TWO_BODY") && userDataA.toString().contains("powerUp")) {
            ActivePowerUp powerUp = model.getActivePowerUp();
            model.applyPowerUp(PlayerType.PLAYER_TWO, powerUp);
            model.destroyPowerUpList();
        }
    }

    

}

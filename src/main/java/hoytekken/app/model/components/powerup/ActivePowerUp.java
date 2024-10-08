package hoytekken.app.model.components.powerup;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import hoytekken.app.Hoytekken;
import hoytekken.app.model.components.player.IPlayer;

/**
 * Class represents the currently active power up in the game
 */
public class ActivePowerUp extends Sprite {
    private static final float POWERUP_SIZE = 30 / Hoytekken.PPM;

    private final String type;
    private final World world;
    private Body body;
    private final Texture texture;
    private final PowerUp powerUp;

    private boolean isVisible = true;
    private boolean shouldBeDestroyed = false;

    private float powerUpInterval = 0;
    
    /**
     * Constructor for the active power up
     * 
     * @param factory the power up factory
     * @param world   the world object
     */
    public ActivePowerUp(PowerUpFactory factory, World world) {

        this.world = world;
        this.powerUp = factory.getNext();
        this.type = powerUp.getClass().getSimpleName();
        this.texture = powerUp.getTexture();
        
        setRegion(texture);
        defineBody();
        setBounds(0, 0, POWERUP_SIZE, POWERUP_SIZE);
        positionBody();
        positionTexture();
    }

    private void positionBody() {
        body.setTransform(((float) Math.random() * Hoytekken.V_WIDTH / Hoytekken.PPM),
        ((float) Math.random() * Hoytekken.V_HEIGHT / Hoytekken.PPM), 0);
    }

    private void positionTexture() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    private void defineBody() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        // Set the user data to this object
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(POWERUP_SIZE / 2, POWERUP_SIZE / 2);
        fdef.shape = shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this.type + "powerUp");
    }

    /*
     * Updates the powerup
     * includes updating the timer for the powerup and removing it
     */
    public void update(float dt) {
        if (isVisible) {
            powerUpInterval += dt;
            if (powerUpInterval >= 3) {
                makeInvisible();
            }
        }
    }

    /*
     * Makes the powerup visible
     */
    public void makeVisible() {
        isVisible = true;
        powerUpInterval = 0;
    }

    /*
     * Applies the powerup to the player
     */
    public void apply(IPlayer player) {
        powerUp.applyPowerUp(player);
    }

    /*
     * Getter for the body
     */
    public Body getBody() {
        return body;
    }

    /*
     * Boolean to determine whether the powerup is visible
     */
    public boolean isVisible() {
        return this.isVisible;
    }

    /*
     * Makes the powerup invisible
     */
    public void makeInvisible() {
        this.isVisible = false;
    }

    /*
     * Marks the powerup for destruction
     */
    public void markForDestruction() {
        this.shouldBeDestroyed = true;
    }

    /*
     * Boolean to determine whether the powerup should be destroyed
     */
    public boolean shouldBeDestroyed() {
        return this.shouldBeDestroyed;
    }
}

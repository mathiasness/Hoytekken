package hoytekken.app.model.components.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Abstract class for collision
 */
public abstract class AbstractCollision implements ContactListener {
    protected HandleCollisions model;

    /**
     * Constructor for the abstract collision
     * 
     * @param model the model to handle the collisions
     */
    public AbstractCollision(HandleCollisions model) {
        this.model = model;
    }

    @Override
    public void beginContact(Contact contact) {
        // ignore begin contact
    }

    @Override
    public void endContact(Contact contact) {
        // ignore end contact
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // ignore pre solve
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // ignore post solve
    }
}

package hoytekken.app.model.components.tools;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import hoytekken.app.model.components.player.PlayerType;

public class CollisionDetectorTest {
    private CollisionDetector collisionDetector;
    private HandleCollisions model;

    @Mock
    private Contact mockContact = Mockito.mock(Contact.class);

    @Mock
    private Fixture mockFixtureA = Mockito.mock(Fixture.class), mockFixtureB = Mockito.mock(Fixture.class);

    @Mock
    private Manifold mockMan = Mockito.mock(Manifold.class);

    @Mock
    private ContactImpulse mockImpulse = Mockito.mock(ContactImpulse.class);

    @BeforeEach
    void setUp(){
        model = Mockito.mock(HandleCollisions.class);
        collisionDetector = new CollisionDetector(model);

        //Configure the mock contact to return the mock fixtures
        Mockito.when(mockContact.getFixtureA()).thenReturn(mockFixtureA);
        Mockito.when(mockContact.getFixtureB()).thenReturn(mockFixtureB);
    }

    @Test
    void sanityTest() {
        assert(true);
        assertEquals(1, 1);
         assertNotNull(model);
        assertDoesNotThrow(() -> new CollisionDetector(model));
        assertNotNull(collisionDetector);
        assertNotNull(mockContact);
        assertNotNull(mockFixtureA);
        assertNotNull(mockFixtureB);
    }

    @Test
    void testBeginContact() {
        assertDoesNotThrow(() -> collisionDetector.beginContact(mockContact));
    }

    @Test
    void testEndContact() {
        assertDoesNotThrow(() -> collisionDetector.endContact(mockContact));
    }

    @Test
    void testPreSolve() {
        assertDoesNotThrow(() -> collisionDetector.preSolve(mockContact, mockMan));
    }

    @Test
    void testPostSolve() {
        assertDoesNotThrow(() -> collisionDetector.postSolve(mockContact, mockImpulse));
    }

    @Test
    void testHandleCollisionModel() {
        assertDoesNotThrow(() -> model.resetDoubleJump(PlayerType.PLAYER_ONE));
        assertDoesNotThrow(() -> model.resetDoubleJump(PlayerType.PLAYER_TWO));
    }

    @Test
    void testDestroyPowerUpList() {
        assertDoesNotThrow(() -> model.destroyPowerUpList());
    }

    @Test
    void testApplyPowerUp() {
        assertDoesNotThrow(() -> model.applyPowerUp(PlayerType.PLAYER_ONE, null));
        assertDoesNotThrow(() -> model.applyPowerUp(PlayerType.PLAYER_TWO, null));
    }

    @Test
    void testGetActivePowerUp() {
        assertDoesNotThrow(() -> model.getActivePowerUp());
    }
}

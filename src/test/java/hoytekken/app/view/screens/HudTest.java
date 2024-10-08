package hoytekken.app.view.screens;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import hoytekken.app.Hoytekken;

public class HudTest {
    private static HeadlessApplication application;
    private static Hoytekken gameMock;
    private Hud hud;

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new Hoytekken(), config);
        Gdx.gl = mock(GL20.class);
        gameMock = mock(Hoytekken.class);
        gameMock.batch = mock(SpriteBatch.class);
    }

    @BeforeEach
    void setUpBeforeEach() {
        hud = new Hud(gameMock.batch);
    }

    @AfterEach
    void disposeHud() {
        hud.getStage().dispose();
    }

    @AfterAll
    static void cleanUp() {
        application.exit();
        application = null;
    }

    @Test
    void sanityTest() {
        assertEquals(1, 1, "Sanity check to verify that tests are working.");
        assertNotNull(application, "Headless application should be initialized.");
        assertNotNull(Gdx.gl, "Mock GL20 should be initialized.");
        assertNotNull(gameMock, "Mock game should be initialized.");
        assertNotNull(gameMock.batch, "Mock SpriteBatch should be initialized.");
        assertNotNull(hud, "Hud should be initialized.");
    }

    @Test
    void testStage() {
        assertNotNull(hud.getStage());
        assertDoesNotThrow(() -> hud.getStage());
    }

    @Test
    void testHealth() {
        assertDoesNotThrow(() -> hud.setPlayerHealth("99"));
        assertDoesNotThrow(() -> hud.setEnemyHealth("99"));
        assertDoesNotThrow(() -> hud.setPlayerHealth("0"));
        assertDoesNotThrow(() -> hud.setEnemyHealth("0"));
        assertDoesNotThrow(() -> hud.setPlayerHealth("99999"));
        assertDoesNotThrow(() -> hud.setEnemyHealth("99999"));

    }
}

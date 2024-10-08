package hoytekken.app.view.screens;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import hoytekken.app.Hoytekken;
import hoytekken.app.model.components.eventBus.EventBus;
import hoytekken.app.view.ViewableModel;

public class BaseScreenTest {
    private static HeadlessApplication application;
    private static Hoytekken gameMock;
    private static ViewableModel modelMock;

    private EventBus eventbus = new EventBus();
    private MenuScreen menuScreen;

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new Hoytekken(), config);

        // Mock dependencies
        Gdx.gl = mock(GL20.class);
        gameMock = mock(Hoytekken.class);
        modelMock = mock(ViewableModel.class);
        gameMock.batch = mock(SpriteBatch.class);
    }

    @BeforeEach
    void setUpBeforeEach() {
        when(modelMock.getEventBus()).thenReturn(eventbus);
        menuScreen = new MenuScreen(gameMock, modelMock);
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
        assertNotNull(Gdx.gl, "Mock GL20 object should be initialized.");
        assertNotNull(gameMock, "Mock game object should be initialized.");
        assertNotNull(modelMock, "Mock model object should be initialized.");
        assertNotNull(gameMock.batch, "Mock SpriteBatch object should be initialized.");
    }

    @Test
    void testInitializeScreens() {

        // Assert objects are initialized
        assertNotNull(menuScreen.game, "Game object should be initialized.");
        assertNotNull(menuScreen.model, "Model object should be initialized.");
        assertNotNull(menuScreen.gameCam, "Camera object should be initialized.");
        assertNotNull(menuScreen.gamePort, "Viewport object should be initialized.");

        // Assert correct types are used
        assertTrue(menuScreen.gamePort instanceof FitViewport, "gamePort should be of type FitViewport.");
        assertTrue(menuScreen.gameCam instanceof OrthographicCamera, "gameCam should be of type OrthographicCamera.");

        // Assert that the camera is centered
        assertEquals(Hoytekken.V_WIDTH / 2f, menuScreen.gameCam.position.x, "Camera X position should be centered.");
        assertEquals(Hoytekken.V_HEIGHT / 2f, menuScreen.gameCam.position.y, "Camera Y position should be centered.");

    }

    @Test
    void testResizeScreen() {
        // MenuScreen menuScreen = new MenuScreen(gameMock, modelMock);

        // assert aspect ratio is maintained
        float originalAspectRatio = menuScreen.gamePort.getWorldWidth() / menuScreen.gamePort.getWorldHeight();
        menuScreen.resize(800, 600);

        float newWidth = menuScreen.gamePort.getScreenWidth();
        float newHeight = menuScreen.gamePort.getScreenHeight();
        float newAspectRatio = newWidth / newHeight;

        assertEquals(originalAspectRatio, newAspectRatio, 0.01, "Aspect ratio should be maintained after resize.");
        assertEquals(800, newWidth, "Screen width should be 800.");
        assertNotEquals(600, newHeight, "Height should not be 600.");
        assertEquals(newWidth / newAspectRatio, newHeight, "Height should be adjusted to maintain aspect ratio.");

        // assert screens are resized for different parameters
        menuScreen.resize(1000, 800);
        assertEquals(1000, menuScreen.gamePort.getScreenWidth(), "Screen width should be 1000.");
        assertEquals(1000 / newAspectRatio, menuScreen.gamePort.getScreenHeight(), "Screen height should be 800.");

        menuScreen.resize(1200, 900);
        assertEquals(1200, menuScreen.gamePort.getScreenWidth(), "Screen width should be 1200.");
        assertEquals(1200 / newAspectRatio, menuScreen.gamePort.getScreenHeight(), "Screen height should be 900.");

        // assert does not throw exception
        assertDoesNotThrow(() -> menuScreen.resize(0, 0), "Resize should not throw an exception.");
        assertDoesNotThrow(() -> menuScreen.resize(100000, 100000), "Resize should not throw an exception.");

    }

    @Test
    void testSuperImplementations() {
        // MenuScreen menuScreen = new MenuScreen(gameMock, modelMock);

        // assert super implementations does not throw exceptions
        assertDoesNotThrow(() -> menuScreen.show(), "Show should not throw an exception.");
        assertDoesNotThrow(() -> menuScreen.hide(), "Hide should not throw an exception.");
        assertDoesNotThrow(() -> menuScreen.pause(), "Pause should not throw an exception.");
        assertDoesNotThrow(() -> menuScreen.resume(), "Resume should not throw an exception.");
        assertDoesNotThrow(() -> menuScreen.dispose(), "Dispose should not throw an exception.");

    }

}

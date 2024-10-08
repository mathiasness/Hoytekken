package hoytekken.app.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.eventBus.EventBus;
import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.view.ViewableModel;


public class ModelViewTests {
    private static final Vector2 GRAVITY_VECTOR = new Vector2(0, -14);
    private static final int INITIAL_BODY_COUNT = 3;
    private static final int MAX_HP = 99;
    private static final int MAX_LIVES = 3;

    private ViewableModel model;

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };
        new HeadlessApplication(listener, config);
        Gdx.gl = mock(GL20.class);
    }

    @BeforeEach
    void setUpBeforeEach() {
        model = new HTekkenModel(Mockito.mock(EventBus.class));
        model.setNumberOfPlayers(false);

    }

    @AfterEach
    void cleanUp() {
        model.getGameWorld().dispose();
    }

    @Test
    void sanityTest() {
        assertEquals(1, 1, "Sanity check to verify that tests are working.");
        assertNotNull(Gdx.gl, "Mock GL20 object should be initialized.");
        assertNotNull(model, "Model object should be initialized.");
    }

    @Test
    void testUpdateModel() {
        //assert the update method does not throw for different time deltas
        assertDoesNotThrow(() -> model.updateModel(0.1f), "Update should not throw.");
        assertDoesNotThrow(() -> model.updateModel(10.0f), "Update should not throw.");
        assertDoesNotThrow(() -> model.updateModel(0.0f), "Update should not throw.");
    }

    @Test
    void testGetPlayer() {
        //assert view has access to ViewablePlayer objects
        assertNotNull(model.getPlayer(PlayerType.PLAYER_ONE), "Player object should be initialized.");
        assertNotNull(model.getPlayer(PlayerType.PLAYER_TWO), "Player object should be initialized.");

        //assert View has access to correct player attributes
        assertDoesNotThrow(() -> model.getPlayer(PlayerType.PLAYER_ONE).getHealth());
        assertDoesNotThrow(() -> model.getPlayer(PlayerType.PLAYER_TWO).getHealth());
        assertDoesNotThrow(() -> model.getPlayer(PlayerType.PLAYER_ONE).getLives());
        assertDoesNotThrow(() -> model.getPlayer(PlayerType.PLAYER_TWO).getLives());
        assertDoesNotThrow(() -> model.getPlayer(PlayerType.PLAYER_ONE).isAlive());
        assertDoesNotThrow(() -> model.getPlayer(PlayerType.PLAYER_TWO).isAlive());

        //assert players are not the same
        assertNotEquals(model.getPlayer(PlayerType.PLAYER_ONE), model.getPlayer(PlayerType.PLAYER_TWO));

        //assert players are alive
        assertTrue(model.getPlayer(PlayerType.PLAYER_ONE).isAlive(), "Player should be alive.");
        assertTrue(model.getPlayer(PlayerType.PLAYER_TWO).isAlive(), "Player should be alive.");

        //assert players have health
        assertTrue(model.getPlayer(PlayerType.PLAYER_ONE).getHealth() == MAX_HP, "Player should start with 99 health.");
        assertTrue(model.getPlayer(PlayerType.PLAYER_TWO).getHealth() == MAX_HP, "Player should start with 99 health.");

        //assert players have lives
        assertTrue(model.getPlayer(PlayerType.PLAYER_ONE).getLives() == MAX_LIVES, "Player should start with 3 lives.");
        assertTrue(model.getPlayer(PlayerType.PLAYER_TWO).getLives() == MAX_LIVES, "Player should start with 3 lives.");
    }

    @Test
    void testGetMap() {
        //assert view has access to map and it is initialized as deafault map
        String map = model.getMap();
        assertNotNull(map, "Map object should be initialized.");
        assertEquals("defaultMap.tmx", map, "Map should be defaultMap.tmx.");

    }

    @Test
    void testGetTiledMap() {
        //assert view has access to tiledmap and it is not loaded before game starts
        assertNull(model.getTiledMap(), "Tiledmap should not be loaded before game starts.");
    }

    @Test
    void testGetGameState() {
        //assert view has access to gamestate and it is initialized as main menu
        assertNotNull(model.getGameState(), "Gamestate should be initialized.");
        assertEquals(GameState.MAIN_MENU ,model.getGameState(), "Intial game stata should me main menu.");
    }

    @Test
    void testGetGameWorld() {
        //assert view has access to game world and it is initialized with gravity and bodies
        assertNotNull(model.getGameWorld(), "Game world should be initialized.");

        World gameWorld = model.getGameWorld();
        Vector2 gravity = gameWorld.getGravity();
        int bodyCount = gameWorld.getBodyCount();

        assertEquals(GRAVITY_VECTOR, gravity, "Game world should have gravity vector set.");
        assertEquals(INITIAL_BODY_COUNT, bodyCount, "Game world should have to player bodies");
        assertDoesNotThrow(() -> gameWorld.step(1f, 1, 1), "Game world should be able to step.");
    }
}
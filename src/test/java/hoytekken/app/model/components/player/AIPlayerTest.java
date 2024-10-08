package hoytekken.app.model.components.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import hoytekken.app.model.components.player.Player;
import hoytekken.app.model.components.player.PlayerType;

public class AIPlayerTest {
    private World world;
    private Player opposition;
    private AIPlayer AIPlayer;

    private static final int MAX_HEALTH = 99;
    private static final float PUNCH_RANGE = 1.8f;
    private static final float KICK_RANGE = 2.2f;
    private static final float DELTA_TIME = 0.1f;
    private static final float TIME_STEPS = 1 / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };
        new HeadlessApplication(listener, config);
    }

    @BeforeEach
    void setUpBeforeEach() {
        Gdx.gl = mock(GL20.class);

        // create interface for world which gives better abstraction?
        world = new World(new Vector2(0, -14), true); // no gravity right now
        opposition = new Player(world, PlayerType.PLAYER_ONE, 99);
        AIPlayer = new AIPlayer(world, PlayerType.PLAYER_TWO, 99, opposition);
    }

    private void movePlayersBeside(float rangeFactor) {
        float dirX = Float.compare(opposition.getBody().getPosition().x, AIPlayer.getBody().getPosition().x);

        while (!AIPlayer.isWithinRange(opposition, rangeFactor)) {
            AIPlayer.move(dirX * 0.5f, 0);
            world.step(1 / 60f, 6, 2);
        }
    }

    @Test
    void sanityTest() {
        assertNotNull(world);
        assertNotNull(opposition);
        assertNotNull(AIPlayer);
        assertNotNull(AIPlayer.getBody());
        assertTrue(AIPlayer.isAlive());
        assertEquals(opposition.getHealth(), MAX_HEALTH);
    }

    @Test
    void moveAIPlayerTest() {
        float initX = AIPlayer.getBody().getPosition().x;
        float dirX = Float.compare(opposition.getBody().getPosition().x, AIPlayer.getBody().getPosition().x);

        AIPlayer.move(dirX * 0.5f, 0);
        world.step(TIME_STEPS, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        float newX = AIPlayer.getBody().getPosition().x;

        float deltaX = initX - newX;

        assertTrue(deltaX != 0);
    }

    @Test
    void AIPlayerDoDamageTest() {
        float initX = AIPlayer.getBody().getPosition().x;
        movePlayersBeside(KICK_RANGE);
        float newX = AIPlayer.getBody().getPosition().x;

        assertTrue(initX != newX);

        assertTrue(AIPlayer.isWithinRange(opposition, KICK_RANGE));
        assertFalse(AIPlayer.isWithinRange(opposition, PUNCH_RANGE));

        AIPlayer.kick(opposition);
        world.step(TIME_STEPS, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        assertEquals(92, opposition.getHealth());
    }

    @Test
    void AIPlayerTakeDamageTest() {
        movePlayersBeside(PUNCH_RANGE);

        opposition.punch(AIPlayer);
        world.step(TIME_STEPS, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        assertEquals(89, AIPlayer.getHealth());
    }

}

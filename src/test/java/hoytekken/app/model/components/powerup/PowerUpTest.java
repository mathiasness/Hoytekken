package hoytekken.app.model.components.powerup;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import hoytekken.app.Hoytekken;
import hoytekken.app.model.components.player.IPlayer;
import hoytekken.app.model.components.player.Player;
import hoytekken.app.model.components.player.PlayerType;

public class PowerUpTest {

    private World world;
    private Player player1;
    private Player player2;

    private static final int MAX_HEALTH = 99;
    private static final float PUNCH_RANGE = 1.8f;
    private static final float KICK_RANGE = 2.2f;
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

        world = new World(new Vector2(0, -14), true);
        player1 = new Player(world, PlayerType.PLAYER_ONE, MAX_HEALTH);
        player2 = new Player(world, PlayerType.PLAYER_TWO, MAX_HEALTH);
    }

    private boolean isWithinRange(IPlayer p1, IPlayer p2, float rangeFactor) {
        float playerWidth = 45 / Hoytekken.PPM;

        Vector2 p1Pos = new Vector2(p1.getBody().getPosition().x, p1.getBody().getPosition().y);
        Vector2 p2Pos = new Vector2(p2.getBody().getPosition().x, p2.getBody().getPosition().y);

        float distance = p1Pos.dst(p2Pos);
        float range = playerWidth * rangeFactor;
        return distance <= range;
    }

    private void movePlayersBeside(float rangeFactor) {
        while (!isWithinRange(player2, player1, rangeFactor)) {
            player1.move(1, 0);
            world.step(1 / 60f, 6, 2);
        }
    }

    @Test
    void newPowerUpTest() {
        PowerUp dmg = PowerUp.newPowerUp(PowerUpType.EXTRA_DAMAGE);
        PowerUp hp = PowerUp.newPowerUp(PowerUpType.EXTRA_HEALTH);
        PowerUp life = PowerUp.newPowerUp(PowerUpType.EXTRA_LIFE);
        PowerUp speed = PowerUp.newPowerUp(PowerUpType.DOUBLE_SPEED);

        assertTrue(dmg instanceof ExtraDamage);
        assertTrue(hp instanceof ExtraHealth);
        assertTrue(life instanceof ExtraLife);
        assertTrue(speed instanceof DoubleSpeed);
        assertNotNull(dmg.getTexture());
        assertNotNull(hp.getTexture());
        assertNotNull(life.getTexture());
        assertNotNull(speed.getTexture());

    }

    @Test
    void extraDamageTest() {
        ExtraDamage extraDamagPowerUp = new ExtraDamage();
        extraDamagPowerUp.applyPowerUp(player1);

        movePlayersBeside(PUNCH_RANGE);
        player1.punch(player2);
        world.step(TIME_STEPS, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        assertEquals(69, player2.getHealth());
    }

    @Test
    void extraHealthTest() {
        ExtraHealth extraHealthPowerUp = new ExtraHealth();
        extraHealthPowerUp.applyPowerUp(player1);

        assertEquals(149, player1.getHealth());
    }

    @Test
    void extraLifeTest() {
        ExtraLife extraLifPowerUp = new ExtraLife();
        extraLifPowerUp.applyPowerUp(player1);

        assertEquals(4, player1.getLives());
    }

    @Test
    void doubleSpeedTest() {
        DoubleSpeed doubleSpeedPowerUp = new DoubleSpeed();
        doubleSpeedPowerUp.applyPowerUp(player1);

        assertEquals(3, player1.getMaxVelocity());
    }
}

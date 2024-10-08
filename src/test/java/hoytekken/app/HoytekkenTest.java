package hoytekken.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HoytekkenTest {
    private Hoytekken game;

    @BeforeEach
    void setUp() {
        game = new Hoytekken();
        game.batch = Mockito.mock(SpriteBatch.class);
    }

    @Test
    void sanityTest() {
        assertTrue(true);
        assertEquals(1, 1);
        assertNotNull(game);
        assertNotNull(game.batch);
    }
}

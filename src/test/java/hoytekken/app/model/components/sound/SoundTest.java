package hoytekken.app.model.components.sound;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

/*
 * Unit tests for the Sound class
 */
public class SoundTest {
    private ISound sound;
    private String path = "sounds/punch-3-166696.mp3";

    @BeforeAll
    static void setUpBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        ApplicationListener listener = new ApplicationAdapter() {
        };

        new HeadlessApplication(listener, config);
    }

    @BeforeEach
    void setUpBeforeEach() {
        sound = new Sound(path);
    }

    @Test
    void sanityTest() {
        assertNotNull(sound);
    }

    @Test
    void checkIdNotNull() {
        assertNotNull(sound.getId());
    }

    @Test
    void testPlay() {
        assertDoesNotThrow(() -> sound.play());
    }

    @Test
    void testStop() {
        assertDoesNotThrow(() -> sound.stop());
    }

    @Test
    void testLoop() {
        assertDoesNotThrow(() -> sound.loop());
    }

    @Test
    void testStopLoop() {
        assertDoesNotThrow(() -> sound.stopLoop());
    }

    @Test
    void testPlayStop() {
        assertDoesNotThrow(() -> {
            sound.play();
            sound.stop();
        });
    }

    @Test
    void testLoopStopLoop() {
        assertDoesNotThrow(() -> {
            sound.loop();
            sound.stopLoop();
        });
    }

    @Test
    void testPlayLoopStopLoop() {
        assertDoesNotThrow(() -> {
            sound.play();
            sound.loop();
            sound.stopLoop();
        });
    }
}

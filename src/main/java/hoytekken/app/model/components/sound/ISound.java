package hoytekken.app.model.components.sound;

/**
 * Interface for sound effects.
 */
public interface ISound {

    /**
     * Play the sound.
     */
    void play();

    /**
     * Stop playing the sound.
     */
    void stop();

    /**
     * Loop the sound.
     */
    void loop();

    /**
     * Stop the sound loop.
     */
    void stopLoop();

    /**
     * Get the id of the sound.
     */
    long getId();
}

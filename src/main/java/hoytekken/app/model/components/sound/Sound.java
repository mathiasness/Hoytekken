package hoytekken.app.model.components.sound;

import com.badlogic.gdx.Gdx;

/**
 * Sound class for playing sound effects
 */
public class Sound implements ISound {
    private final com.badlogic.gdx.audio.Sound sound;
    private long id;

    /**
     * Constructor for Sound
     * @param path path to the sound file
     */
    public Sound(String path) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    @Override
    public void play() {
        this.id = this.sound.play();
    }

    @Override
    public void stop() {
        sound.stop();
    }

    @Override
    public void loop() {
        play();
        sound.setLooping(id, true);
    }

    @Override
    public void stopLoop() {
        sound.stop(id);
    }

    @Override
    public long getId() {
        return id;
    }
}
package hoytekken.app.view.screens;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hoytekken.app.Hoytekken;
import hoytekken.app.view.ViewableModel;

/**
 * class represents a menu screen
 */
public class MenuScreen extends BaseScreen {
    private static final String BG_PATH = "background.png";
    private static final String PLAY = "CLICK TO PLAY";
    private static final String INSTRUCTIONS = "PRESS \'I\' FOR INSTRUCTIONS";
    private static final String EXIT = "PRESS \'ESC\' TO EXIT";

    private final Texture background;
    private final Stage stage;

    /**
     * Constructor for the menu screen
     * 
     * @param game  the game object
     * @param model the viewable model
     */
    public MenuScreen(Hoytekken game, ViewableModel model) {
        super(game, model);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        background = new Texture(Gdx.files.internal(BG_PATH));
        stage = new Stage(gamePort, game.batch);
        createMenuTable();
    }

    private void createMenuTable() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        Label instructionsLabel = new Label(INSTRUCTIONS, font);
        Label exitLabel = new Label(EXIT, font);
        Label playLabel = new Label(PLAY, font);

        table.add(exitLabel).expandX();
        table.add(playLabel).expandX();
        table.add(instructionsLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.draw(background, 0, 0, gamePort.getWorldWidth(), gamePort.getWorldHeight());
        game.batch.end();

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }

}

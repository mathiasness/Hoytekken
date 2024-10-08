package hoytekken.app.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hoytekken.app.Hoytekken;
import hoytekken.app.view.ViewableModel;

/**
 * class represents a game over screen
 */
public class GameOverScreen extends BaseScreen {
    private final Stage stage;

    /**
     * Constructor for the game over screen
     * 
     * @param game  the game object
     * @param model the viewable model
     */
    public GameOverScreen(Hoytekken game, ViewableModel model, int winningPlayer) {
        super(game, model);

        stage = new Stage(gamePort, game.batch);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label gameOverLabel = new Label("Game Over, Player " + winningPlayer + " Won!", font);
        Label restart = new Label("Click to restart", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(restart).expandX().padTop(10f);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}

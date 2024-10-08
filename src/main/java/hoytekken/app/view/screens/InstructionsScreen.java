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
 * Class representing the instructions screen.
 */
public class InstructionsScreen extends BaseScreen {
    private final Stage stage;

    /**
     * Constructor for the instructions screen.
     * 
     * @param game the game object
     * @param mode the viewable model
     */
    public InstructionsScreen(Hoytekken game, ViewableModel mode) {
        super(game, mode);

        stage = new Stage(gamePort, game.batch);
        createInstructionsTable();
    }

    private void createInstructionsTable() {
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center().setFillParent(true);

        String[][] instructions = {
                {"Actions", "Player1", "Player2"},
                {"Movement", "Keys: A, W, D", "Keys: Left, Up, Right"},
                {"Punch", "Key: Q", "Key: P"},
                {"Kick", "Key: E", "Key: K"},
                {"Block", "Key: S", "Key: B"}
        };

        for (String[] instructionSet : instructions) {
            for (String instruction : instructionSet) {
                table.add(new Label(instruction, font)).expandX();
            }
            table.row();
        }

        table.add(new Label("Click to continue", font)).expandX().padTop(100);
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

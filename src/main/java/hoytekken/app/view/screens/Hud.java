package hoytekken.app.view.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hoytekken.app.Hoytekken;

/**
 * class represents the heads-up display for the game
 */
public class Hud {
    private final Stage stage;
    private final Viewport port;

    private static final Integer HUD_PADDING_TOP = 5;
    private static final Float INIT_TIME = 0.0f;

    private Integer playerHealth;
    private Float battleTimer;
    private Integer enemyHealth;

    private List<Label> labelList;
    private final LabelStyle defaultLabelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    private Table upperTable;

    /**
     * Constructor for the heads-up display
     * 
     * @param sb sprite-batch
     */
    public Hud(SpriteBatch sb) {
        battleTimer = INIT_TIME;

        port = new FitViewport(Hoytekken.V_WIDTH, Hoytekken.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(port, sb);
        upperTable = new Table();
        upperTable.top();
        upperTable.setFillParent(true);

        labelList = initLabels();
        addLabelsToTable(labelList, upperTable);

        stage.addActor(upperTable);
    }

    private Label createLabel(String text) {
        return new Label(text, defaultLabelStyle);
    }

    // better to save labels as field variables?
    private List<Label> initLabels() {
        List<Label> list = new ArrayList<>();
        list.add(createLabel("PLAYER"));
        list.add(createLabel("TIME"));
        list.add(createLabel("ENEMY"));
        // next lines might have to change due to updating the variable
        list.add(createLabel(String.format("%02d", playerHealth)));
        list.add(createLabel(String.format("%03d", battleTimer.intValue())));
        list.add(createLabel(String.format("%02d", enemyHealth)));
        return list;
    }

    private void addLabelsToTable(List<Label> labels, Table table) {
        int counter = 0;
        for (Label label : labels) {
            counter++;
            table.add(label).expandX().padTop(HUD_PADDING_TOP);
            if (counter == 3) {
                table.row();
            }
        }
    }

    /**
     * Getter for the stage
     * 
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    // setters be removed for better alternative later

    /**
     * Setter for the player's health
     * 
     * @param health of player
     */
    public void setPlayerHealth(String health) {
        labelList.get(3).setText(health);
    }

    /**
     * Setter for the enemy's health
     * 
     * @param health of enemy
     */
    public void setEnemyHealth(String health) {
        labelList.get(5).setText(health);
    }

    public void update(float dt) {
        battleTimer += dt;
        labelList.get(4).setText(String.format("%03d", battleTimer.intValue()));
    }
}

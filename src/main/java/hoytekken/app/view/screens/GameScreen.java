package hoytekken.app.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import hoytekken.app.Hoytekken;
import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.model.components.powerup.ActivePowerUp;
import hoytekken.app.view.ViewableModel;

/**
 * class represents an active game screen
 */
public class GameScreen extends BaseScreen {
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;
    private Box2DDebugRenderer b2dr;

    private final Hud hud;


    /**
     * Constructor for the game screen
     * 
     * @param game  the game object
     * @param model the viewable model
     */
    public GameScreen(Hoytekken game, ViewableModel model) {
        super(game, model, true);

        hud = new Hud(game.batch);
        initializeMapAndRenderers();
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    private void initializeMapAndRenderers() {
        map = model.getTiledMap();
        renderer = new OrthoCachedTiledMapRenderer(map, 1 / Hoytekken.PPM);
        b2dr = new Box2DDebugRenderer();
    }

    @Override
    protected void update(float delta) {
        model.updateModel(delta);
        renderer.setView(gameCam);
        super.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);
        hud.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(this.model.getGameWorld(), gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);

        // Update health
        hud.setPlayerHealth(this.model.getPlayer(PlayerType.PLAYER_ONE).getHealth() + " - "
                + this.model.getPlayer(PlayerType.PLAYER_ONE).getLives());
        hud.setEnemyHealth(this.model.getPlayer(PlayerType.PLAYER_TWO).getHealth() + " - "
                + this.model.getPlayer(PlayerType.PLAYER_TWO).getLives());

        game.batch.begin();
        // game.batch.draw(img, 0, 0);
        this.model.getPlayer(PlayerType.PLAYER_ONE).draw(game.batch);
        this.model.getPlayer(PlayerType.PLAYER_TWO).draw(game.batch);
        //this.model.getActivePowerUp().draw(game.batch);

        ActivePowerUp activePowerUp = this.model.getActivePowerUp();
        if (activePowerUp != null && activePowerUp.isVisible()) {
            activePowerUp.draw(game.batch);
        }

        game.batch.end();
        hud.getStage().draw();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        hud.getStage().dispose();
    }

}

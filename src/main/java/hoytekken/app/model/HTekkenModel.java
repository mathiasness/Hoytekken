package hoytekken.app.model;

import java.util.HashMap;
import java.util.LinkedList;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import hoytekken.app.controller.ActionType;
import hoytekken.app.controller.ControllableModel;
import hoytekken.app.model.components.ForceDirection;
import hoytekken.app.model.components.GameState;
import hoytekken.app.model.components.player.AIPlayer;
import hoytekken.app.model.components.eventBus.EventBus;
import hoytekken.app.model.components.eventBus.GameStateEvent;
import hoytekken.app.model.components.player.IPlayer;
import hoytekken.app.model.components.player.Player;
import hoytekken.app.model.components.player.PlayerType;
import hoytekken.app.model.components.powerup.ActivePowerUp;
import hoytekken.app.model.components.powerup.RandomPowerUpFactory;
import hoytekken.app.model.components.tools.Box2DWorldGenerator;
import hoytekken.app.model.components.tools.CollisionDetector;
import hoytekken.app.model.components.tools.HandleCollisions;
import hoytekken.app.view.ViewableModel;

/**
 * The model for the game
 */
public class HTekkenModel implements ViewableModel, ControllableModel, HandleCollisions {
    // Gravity vector
    private static final Vector2 GRAVITY_VECTOR = new Vector2(0, -14);

    // Event bus
    private final EventBus eventBus;

    // Game world and state
    private final World gameWorld;
    private GameState gameState;

    // Players
    private final IPlayer playerOne;
    private IPlayer playerTwo;
    private ForceDirection p1Direction = ForceDirection.STATIC;
    private ForceDirection p2Direction = ForceDirection.STATIC;
    private static final int MAX_JUMPS = 2;
    private int playerOneJumpCounter = 0;
    private int playerTwoJumpCounter = 0;
    private final float directionSpeed = 0.5f;

    // Map
    private String map;
    private final TmxMapLoader mapLoader;
    private TiledMap tiledmap;
    private final static String DEFAULT_MAP = "defaultMap.tmx";
    private final static HashMap<String, String> gameMaps = new HashMap<>() {
        {
            put("map1", "defaultMap.tmx");
            put("map2", "secondKMVmap.tmx");
            put("map3", "thirdKMVmap.tmx");
            put("map4", "fourthKMVmap1.tmx");
        }
    };

    // Powerups
    private ActivePowerUp activePowerUp;
    private float timeSinceLastPowerUp = 0;
    private final float powerUpSpawnInterval = 10;
    private final LinkedList<Body> bodiesToDestroy = new LinkedList<>();

    /**
     * Constructor for the model
     * 
     * @param map string for chosen map
     */
    public HTekkenModel(String map, EventBus eventBus) {
        this.map = map;
        this.gameWorld = new World(GRAVITY_VECTOR, true);
        this.gameState = GameState.MAIN_MENU;

        this.playerOne = new Player(gameWorld, PlayerType.PLAYER_ONE, 99);
        //this.playerTwo = new Player(gameWorld, PlayerType.PLAYER_TWO, 99);
        this.playerTwo = null;
        //playerTwo.flipLeft();

        mapLoader = new TmxMapLoader();

        this.gameWorld.setContactListener(new CollisionDetector(this));

        this.activePowerUp = new ActivePowerUp(new RandomPowerUpFactory(), gameWorld);
        this.eventBus = eventBus;
    }

    /**
     * Constructor for the model, uses default map
     */
    public HTekkenModel(EventBus eventBus) {
        this(HTekkenModel.DEFAULT_MAP, eventBus);
    }

    @Override
    public void updateModel(float dt) {
        gameWorld.step(1 / 60f, 6, 2);
        movePlayers();

        if (activePowerUp != null) {
            activePowerUp.update(dt);
            if (!activePowerUp.isVisible() || activePowerUp.shouldBeDestroyed()) {
                bodiesToDestroy.add(activePowerUp.getBody());
                activePowerUp = null;
            }
        }
        if (activePowerUp == null) {
            timeSinceLastPowerUp += dt;
            if (timeSinceLastPowerUp >= powerUpSpawnInterval) {
                activePowerUp = new ActivePowerUp(new RandomPowerUpFactory(), gameWorld);
                activePowerUp.makeVisible();
                timeSinceLastPowerUp = 0;
            }
        }

        while (!bodiesToDestroy.isEmpty()) {
            Body b = bodiesToDestroy.poll();
            if (b != null && b.getUserData() instanceof ActivePowerUp) {
                gameWorld.destroyBody(b);
            }
        }

        playerOne.update(dt);
        playerTwo.update(dt);

        if (isGameOver()) {
            setGameState(GameState.GAME_OVER);
        }

    }

    @Override
    public World getGameWorld() {
        return this.gameWorld;
    }

    @Override
    public IPlayer getPlayer(PlayerType player) {
        return player == PlayerType.PLAYER_ONE ? playerOne : playerTwo;
    }

    @Override
    public String getMap() {
        return this.map;
    }

    @Override
    public TiledMap getTiledMap() {
        return tiledmap;
    }

    @Override
    public boolean setDirection(PlayerType player, ForceDirection direction) {
        if (player == PlayerType.PLAYER_ONE) {
            p1Direction = direction;
            return true;
        } else if (player == PlayerType.PLAYER_TWO) {
            p2Direction = direction;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ForceDirection getDirection(PlayerType player) {
        if (player == PlayerType.PLAYER_ONE) {
            return p1Direction;
        } else if (player == PlayerType.PLAYER_TWO) {
            return p2Direction;
        } else {
            throw new IllegalArgumentException("Player: " + player + " not found");
        }
    }

    @Override
    public boolean jump(PlayerType player) {
        if (getPlayer(player).getIsBlocking()) {
            return false;
        }
        if (playerOneJumpCounter < MAX_JUMPS && player == PlayerType.PLAYER_ONE) {
            playerOneJumpCounter++;
            IPlayer p1 = getPlayer(player);
            p1.move(0, p1.getJumpingHeight());
            return true;
        } else if (playerTwoJumpCounter < MAX_JUMPS && player == PlayerType.PLAYER_TWO) {
            playerTwoJumpCounter++;
            IPlayer p2 = getPlayer(player);
            p2.move(0, p2.getJumpingHeight());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean resetDoubleJump(PlayerType player) {
        if (player == PlayerType.PLAYER_ONE) {
            playerOneJumpCounter = 0;
        } else {
            playerTwoJumpCounter = 0;
        }
        return true;
    }

    private boolean movePlayers() {
        directionToSpeed(PlayerType.PLAYER_ONE, p1Direction);
        directionToSpeed(PlayerType.PLAYER_TWO, p2Direction);
        return true;
    }

    /**
     * Sets the speed of the player based on the direction
     * 
     * @param player to set the speed for
     * @param direction the speed goes
     * 
     */
    private void directionToSpeed(PlayerType player, ForceDirection direction) {
        IPlayer p = getPlayer(player);
        switch (direction) {
            case LEFT:
                p.move(-this.directionSpeed, 0);
                break;
            case RIGHT:
                p.move(this.directionSpeed, 0);
                break;
            case STATIC:
                p.move(0, 0);
                break;
        }
    }

    @Override
    public boolean performAttackAction(PlayerType attacker, ActionType actionType) {
        IPlayer attackingPlayer = getPlayer(attacker);
        IPlayer victimPlayer = attacker == PlayerType.PLAYER_ONE ? playerTwo : playerOne;

        switch (actionType) {
            case KICK:
                return attackingPlayer.kick(victimPlayer);
            case PUNCH:
                return attackingPlayer.punch(victimPlayer);
        }
        return false;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.eventBus.emitEvent(new GameStateEvent(this.gameState, gameState));
        this.gameState = gameState;
    }

    /**
     * Checks if the game is over
     * 
     * @return true if the game is over, false otherwise
     */
    private boolean isGameOver() {
        return !playerOne.isAlive() || !playerTwo.isAlive();
    }

    /**
     * Retrieves the jump count for player one. Only used for test purposes.
     * 
     * @return the current jump count for player one
     * @throws IllegalStateException if the player is not found
     */
    int getJumpCounter(PlayerType player) {
        if (player == PlayerType.PLAYER_ONE) {
            return playerOneJumpCounter;
        } else if (player == PlayerType.PLAYER_TWO) {
            return playerTwoJumpCounter;
        } else {
            throw new IllegalArgumentException("Player: " + player + " not found");
        }
    }

    @Override
    public HashMap<String, String> getGameMaps() {
        return HTekkenModel.gameMaps;
    }

    @Override
    public void setGameMap(String mapName) {
        String gameMap = gameMaps.get(mapName);
        if (gameMap != null) {
            this.map = gameMap;
            this.tiledmap = mapLoader.load(gameMap);
            new Box2DWorldGenerator(gameWorld, tiledmap);
        } else {
            throw new IllegalArgumentException("Map: " + mapName + " not found");
        }
    }

    @Override
    public ActivePowerUp getActivePowerUp() {
        return activePowerUp;
    }

    @Override
    public void applyPowerUp(PlayerType player, ActivePowerUp powerUp) {
        IPlayer p = getPlayer(player);
        powerUp.apply(p);

    }

    @Override
    public void destroyPowerUpList() {
        if (activePowerUp != null && activePowerUp.getBody() != null) {
            bodiesToDestroy.add(activePowerUp.getBody());
            activePowerUp.markForDestruction();
        }
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public boolean setNumberOfPlayers(Boolean onePlayer) {
        if (onePlayer && !(this.playerTwo instanceof AIPlayer)) {
            this.playerTwo = new AIPlayer(gameWorld, PlayerType.PLAYER_TWO, 99, this.playerOne);
            this.playerTwo.flipLeft();
            return true;
        }
        else if (!onePlayer && !(this.playerTwo instanceof Player)){
            this.playerTwo = new Player(gameWorld, PlayerType.PLAYER_TWO, 99);
            this.playerTwo.flipLeft();
            return true;
        }
        else return false;
    }

    
}

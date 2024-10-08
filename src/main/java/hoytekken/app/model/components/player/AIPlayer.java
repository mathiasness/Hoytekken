package hoytekken.app.model.components.player;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.World;

/**
 * AIPlayer class that represents an AI player
 * Extends {@code Player.Class} and introduces automated decision making
 */
public class AIPlayer extends Player {
    private static final float PUNCH_RANGE = 1.8f;
    private static final float KICK_RANGE = 2.2f;
    private static final float IDLE_ACCELERATION = 0.15f;
    private static final float CHASE_ACCELERATION = 0.5f;
    
    private final IPlayer target;

    //AI Actions
    private boolean chase = false;
    private boolean idleMovement = false;
    private boolean block = false;

    // Action timers
    private float movementTimer = 0;
    private float blockTimer = 0;
    private float lastBlockTimer = 0;

    // Timer limits
    private static final float BLOCK_TIME_LIMIT = 2;
    private static final float CHASE_TIME_LIMIT = 2;
    private static final float IDLE_TIME_LIMIT = 2;
    private static final float LAST_BLOCK_TIME_LIMIT = 2;

    // improve fluidity of idle movement
    private int moveTicks = 0;
    private int lastDir = 0;

    /**
     * Constructor for AIPlayer
     * @param world the game world
     * @param type the player type
     * @param health the player health
     * @param target the target player
     */
    public AIPlayer(World world, PlayerType type, int health, IPlayer target) {
        super(world, type, health);
        this.target = target;

        // reduce AI speed to make it less aggressive
        increaseSpeed(-1);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        makeDecision();
        checkTimers();

        // update active timers
        if (block) blockTimer += dt;
        else lastBlockTimer += dt;

        if (idleMovement) {
            idleMovement();
            movementTimer += dt;
        } else if (chase) {
            moveTowardsTarget();
            movementTimer += dt;
        }
    }

    //Randomly choose between 0 and 1
    private int randomChoice() {
        return new Random().nextInt(2);
    }

    //Make decision based on target position
    private void makeDecision() {
        int decide = randomChoice();

        if (isWithinRange(target, PUNCH_RANGE) && !block) attackOrDefend(true, decide);
        else if (isWithinRange(target, KICK_RANGE) && !block) attackOrDefend(false, decide);
        else if (!(idleMovement || chase)) chooseMovement();
    }

    //Attack or defend based if target is within range
    private void attackOrDefend(boolean punchRange, int decide) {
        if (decide == 0) {
            if (punchRange) punch(target);
            else kick(target);
        } else if (lastBlockTimer > LAST_BLOCK_TIME_LIMIT) startBlock();
    }

    // check timers and stop actions if necessary
    private void checkTimers() {
        if (block && blockTimer > BLOCK_TIME_LIMIT) stopBlock();
        if (idleMovement && movementTimer > IDLE_TIME_LIMIT) idleMovement = false;
        if (chase && movementTimer > CHASE_TIME_LIMIT) chase = false;
    }

    //stop block action
    private void stopBlock() {
        block = false;
        lastBlockTimer = 0;
        changeBlockingState();
    }

    //start block action
    private void startBlock() {
        block = true;
        blockTimer = 0;
        changeBlockingState();
    }

    //Choose between idle movement and chase
    private void chooseMovement() {
        movementTimer = 0;
        if (randomChoice() == 0) idleMovement = true;
        else chase = true;
    }

    //Move left and right
    private void idleMovement() {
        int choice = randomChoice();
        if (!(moveTicks % 30 == 0)) choice = lastDir;

        if (choice == 0) {
            lastDir = 0;
            move(IDLE_ACCELERATION, 0);
        } else {
            lastDir = 1;
            move(IDLE_ACCELERATION, 0);
        }

        moveTicks++;
    }

    //Move towards target
    private void moveTowardsTarget() {
        float dirX = Float.compare(target.getBody().getPosition().x, getBody().getPosition().x);
        move(dirX * CHASE_ACCELERATION, 0);
    }
}

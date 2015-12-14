package AntiTD.troops;

import AntiTD.*;
import AntiTD.tiles.GoalTile;
import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.Stack;

/**
 * Created by id12men on 2015-11-27.
 */
public abstract class Troop implements MovableGameObject {
    protected int health;
    protected int score;
    protected double speed;
    private Image img;
    private Stack<Tile> history;
    private Tile nextTile;
    private double moveProgres;
    private boolean hasReacedGoal;
    private boolean isMoving;
    private boolean slowed;

    /**
     * Constructor for troop object.
     * @param pos starting position tile
     */
    protected Troop(Tile pos) {
        this(null, pos);
    }

    /**
     * Constructor for troop object.
     * @param img image to store for rendering
     * @param pos starting position tile
     */
    protected Troop(Image img, Tile pos) {
        this(img, pos, 1, 1, 1);
    }

    /**
     * Constructor for troop object.
     * @param pos starting position tile
     * @param health health of troop
     * @param score score to gain when troop finishes
     * @param speed move speed
     */
    protected Troop(Tile pos, int health, int score, double speed) {
        this(null, pos, health, score, speed);
    }

    /**
     * Constructor for troop object.
     * @param img image to store for rendering
     * @param pos starting position tile
     * @param health health of troop
     * @param score score to gain when troop finishes
     * @param speed move speed
     */
    protected Troop(Image img, Tile pos, int health, int score, double speed) {
        this.img = img;
        this.health = health;
        this.score = score;
        this.speed = speed;
        this.history = new Stack<Tile>();
        this.history.push(pos);
        slowed = false;
    }

    @Override
    public abstract void tick();

    /**
     * ** CAUTION **
     * Should be used in implemented tick method and no where else.
     * <br /><br />
     * Moves the troop to next tile according to speed, when accumulated
     * speed reaches the value of 100 the position will be updated.
     */
    protected void move() {
        if (!hasReacedGoal && isAlive()) {
            if (!this.isMoving) {
                this.isMoving = true;
                this.moveProgres = speed;
                this.nextTile = getNextTile();
            }

            if (this.moveProgres < 100) {
                this.moveProgres += speed;
            } else {
                this.isMoving = false;
                this.moveProgres = 0;

                history.push(nextTile);
                if (nextTile instanceof GoalTile) {
                    hasReacedGoal = true;
                } else if (nextTile.isTeleporter()) {
                    Tile endTPTile = nextTile.getTeleportTo();

                    // Add all tiles between end and start teleport in history
                    Tile tempTile = getNextTile();
                    while (endTPTile != getTilePosition() ) {
                        history.push(tempTile);
                        tempTile = getNextTile();
                    }

                }
            }
        }
    }

    @Override
    public Tile getMoveToPosition() {
        return nextTile;
    }

    @Override
    public int getMoveProgress() {
        Long p = Math.round(this.moveProgres);
        return p.intValue();
    }

    /**
     * Get next in path that is movable and not in move history.
     * @return next tile
     */
    private Tile getNextTile() {
        Tile[] neigbors;
        neigbors = history.peek().getNeighbors2();

        Tile nextTile = null;

        for (Tile tile : neigbors) {
            if (tile.isMoveable()) {
                if (history.search(tile) == -1) {
                    nextTile = tile;
                    break;
                }
            }
        }
        return nextTile;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        if (hasReacedGoal) {
            return score;
        } else {
            return 0;
        }
    }

    @Override
    public boolean hasReachedGoal() {
        return hasReacedGoal;
    }

    /**
     * Get current health of object.
     * @return health value
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Attacks this troop
     * @param damage amount of damage to take
     * @return true if this troop died else false
     */
    public boolean attackThis(int damage) {
        if ( !hasReachedGoal() ) {
            health = health - damage;
            return !this.isAlive();
        } else {
            return false;
        }
    }

    /**
     * Checks troops life status
     * @return true if alive else false
     */
    @Override
    public boolean isAlive() {
        boolean isAlive = true;
        if (health < 1) {
            isAlive = false;
        }
        if (hasReacedGoal) {
            isAlive = false;
        }
        return isAlive;
    }

    @Override
    public Position getPosition() {
        return history.peek().getPosition();
    }

    @Override
    public Tile getTilePosition() {
        return history.peek();
    }
    public String type(){
        return "Troop";
    }

    /**
     * Slows the movement speed to this troop whe called.
     */
    public void slowSpeed(){
        if (! slowed) {
            this.speed = (speed * 0.5);
            slowed = true;
        }
    }
}

package AntiTD.troops;

import AntiTD.*;
import AntiTD.tiles.GoalTile;
import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by dv13trm on 2015-11-27.
 */
public abstract class Troop implements GameObject {

    protected int health;
    protected int score;
    protected int speed;
    private Image img;
    private Stack<Tile> history;
    private Tile nextTile;
    private int moveProgres;
    private boolean hasReacedGoal;
    private boolean isMoving;


    private float velX;
    private float velY;

    protected Troop(Tile pos) {
        this(null, pos);
    }

    protected Troop(Image img, Tile pos) {
        this.img = img;
        this.history = new Stack<Tile>();
        this.history.push(pos);
    }

    @Override
    public abstract void tick();

    protected void move() {
        if (hasReacedGoal == false && this.isAlive()) {
            if (!this.isMoving) {
                this.isMoving = true;
                this.moveProgres = speed;
                this.nextTile = getNextTile();
            } else if (this.moveProgres < 100) {
                this.moveProgres += speed;
                if (this.moveProgres > 100) {
                    this.moveProgres = 100;
                }
            } else {
                this.isMoving = false;
                this.moveProgres = 0;
                history.push(nextTile);
                if (nextTile instanceof GoalTile) {
                    hasReacedGoal = true;
                }
            }
        }
    }

    @Override
    public Tile getMoveToPosition() {
        return nextTile;
    }

    @Override
    public int getMoveProgres() {
        return this.moveProgres;
    }

    private Tile getNextTile() {
        Tile[] neigbors = history.peek().getNeighbors2();
        Tile nextTile = null;

        for (Tile tile : neigbors) {
            if (tile.isMoveable()) {
                if (history.search(tile) == -1) {
                    nextTile = tile;
                    break;
                }
            }
        }
        if (nextTile.isTeleporter()) {
            nextTile = nextTile.getTeleportTo();
        }
        return nextTile;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        if (hasReacedGoal && this.isAlive()) {
            return score;
        } else {
            return 0;
        }
    }

    public int getHealth() {
        return this.health;
    }

    /**
     * Attacks this troop
     *
     * @param damage amount of damage to take
     * @return true if this troop died else false
     */
    public boolean attackThis(int damage) {
        health = health - damage;
        return !this.isAlive();
    }

    /**
     * Checks troops life status
     *
     * @return true if alive else false
     */
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public Position getPosition() {
        return history.peek().getPosition();
    }

    @Override
    public Tile getTilePosition() {
        return history.peek();
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public String type(){
        return "Troop";
    }
}

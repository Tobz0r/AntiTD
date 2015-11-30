package AntiTD.troops;

import AntiTD.*;
import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.Stack;

/**
 * Created by dv13trm on 2015-11-27.
 */
public abstract class Troop implements GameObject {

    private int health;
    private int score;
    private Image img;
    private Stack<Tile> history;
    private boolean hasReacedGoal;

    public Troop(Image img, Tile pos) {
        this.img = img;
        this.score = 0;
        this.history = new Stack<Tile>();
        this.history.push(pos);
    }

    @Override
    public abstract void tick();

    public void move() {
        if (hasReacedGoal == false && health > 0) {
            Tile[] neigbors = history.peek().getNeighbors();
            Tile nextTile = null;

            for (Tile tile : neigbors) {
                if (tile.isMoveable()) {
                    if (history.search(tile) != -1) {
                        nextTile = tile;
                        break;
                    }
                }
            }

            if (nextTile.isGoal()) {
                hasReacedGoal = true;
            }

            history.push(nextTile);
        }
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        if (hasReacedGoal && health > 0) {
            return score;
        } else {
            return 0;
        }
    }

    public boolean attackThis(int damage) {
        health = health - damage;
        return health < 0 ?;
    }

    @Override
    public Tile getPosition() {
        return history.peek();
    }
}

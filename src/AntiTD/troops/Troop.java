package AntiTD.troops;

import AntiTD.*;
import AntiTD.tiles.Tile;

import java.awt.*;
import java.util.Stack;

/**
 * Created by dv13trm on 2015-11-27.
 */
public abstract class Troop implements GameObject {

    private int score;
    private Image img;
    private Stack<Tile> history;

    public Troop(Image img, Tile pos) {
        this.img = img;
        this.score = 0;
        this.history = new Stack<Tile>();
        this.history.push(pos);
    }

    @Override
    public abstract void tick();

    public void move() {
        Tile[] neigbors = history.peek().getNeighbors();
        Tile nextTile = null;

        for (Tile tile : neigbors) {
            if (history.search(tile) != -1) {
                nextTile = tile;
            }
        }
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        return score;
    }

    @Override
    public Tile getPosition() {
        return history.peek();
    }
}

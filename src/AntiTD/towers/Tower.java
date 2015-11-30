package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    private int score;
    private Image img;

    public Tower(Image img, Tile pos) {
        this.img = img;
        this.score = 0;
        this.pos = pos;
    }


    @Override
    public abstract void tick();

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
        return pos;
    }
}

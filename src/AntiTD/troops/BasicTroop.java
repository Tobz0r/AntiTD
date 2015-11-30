package AntiTD.troops;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by mattias on 2015-11-30.
 */
public class BasicTroop extends Troop {

    public BasicTroop(Tile pos) {
        super(null, pos);
    }

    @Override
    public void tick() {
        this.move();
    }

    @Override
    public void render(Graphics g) {

    }
}

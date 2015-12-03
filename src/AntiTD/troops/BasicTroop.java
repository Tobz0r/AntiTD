package AntiTD.troops;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by mattias on 2015-11-30.
 */
public class BasicTroop extends Troop {

    private final int MAX_HEALTH = 1;

    public BasicTroop(Tile pos) {
        super(null, pos);
        super.health = MAX_HEALTH;
    }

    @Override
    public void tick() {
        this.move();
    }

    @Override
    public void render(Graphics g) {

    }
}

package AntiTD.GameBackEnd.troops;

import AntiTD.GameBackEnd.tiles.Tile;

import java.awt.*;

/**
 * Created by mattias on 2015-11-30.
 */
public class BasicTroop extends Troop {

    private final int MAX_HEALTH = 1;
    private final int KILL_DEATH_SCORE = 10;

    public BasicTroop(Tile pos) {
        super(null, pos);
        super.health = MAX_HEALTH;
        super.score = KILL_DEATH_SCORE;
    }

    @Override
    public void tick() {
        this.move();
    }

    @Override
    public void render(Graphics g) {

    }
}
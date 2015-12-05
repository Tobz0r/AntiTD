package AntiTD.troops;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by mattias on 2015-11-30.
 */
public class BasicTroop extends Troop {

    private final double SPEED = 1;
    private final int MAX_HEALTH = 2000;
    private final int KILL_DEATH_SCORE = 10;

    public BasicTroop(Tile pos) {
        super(null, pos);
        super.health = MAX_HEALTH;
        super.score = KILL_DEATH_SCORE;
        super.speed = SPEED;
    }

    @Override
    public void tick() {
        this.move();
    }

    @Override
    public void render(Graphics g) {

    }
}

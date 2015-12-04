package AntiTD.troops;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by dv13tes on 2015-12-04.
 */
public class SpeedTroop extends Troop {

    private final double SPEED = 3;
    private final int MAX_HEALTH = 1;
    private final int KILL_DEATH_SCORE = 10;

    public SpeedTroop(Tile pos) {
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

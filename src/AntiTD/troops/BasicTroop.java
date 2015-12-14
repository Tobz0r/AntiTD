package AntiTD.troops;

import AntiTD.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by mattias on 2015-11-30.
 */
public class BasicTroop extends Troop {

    static private final double SPEED = 1;
    static private final int MAX_HEALTH = 5;
    static private final int KILL_DEATH_SCORE = 10;

    /**
     * Constructor for basic troop
     * @param pos Starting tile position.
     */
    public BasicTroop(Tile pos) {
        this(null, pos);
    }

    /**
     * Constructor for basic troop
     * @param img Image used for rendering this object.
     * @param pos Starting tile position.
     */
    public BasicTroop(Image img, Tile pos) {
        this(img, pos, MAX_HEALTH, KILL_DEATH_SCORE, SPEED);
    }

    /**
     * Constructor for basic troop, used for overriding default health, score and speed values
     *
     * ** CAUTION **
     * Use this constructor for test purposes only.
     * @param img Image used for rendering this object.
     * @param pos Starting tile position.
     * @param health Damage the troop can sustain.
     * @param score Score generated if this troop reaches goal.
     * @param speed Move progress value for every tick. Should be a value between
     *              0 and 100. The tick will increase the move progress with this
     *              value and when progress reaches 100 the move is finished.
     */
    public BasicTroop(Image img, Tile pos, int health, int score, double speed) {
        super(img, pos, health, score, speed);
    }
    /*
    public BasicTroop(Tile pos) {
        super(null, pos);
        super.health = MAX_HEALTH;
        super.score = KILL_DEATH_SCORE;
        super.speed = SPEED;
    }
    */
    @Override
    public void tick() {
        this.move();
    }
}

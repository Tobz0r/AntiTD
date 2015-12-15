package AntiTD.troops;

import AntiTD.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by dv13tes on 2015-12-04.
 */
public class TankTroop extends Troop {
    static private final double SPEED = 1;
    static private final int MAX_HEALTH = 100;
    static private final int KILL_DEATH_SCORE = 10;
    private Random r;


    /**
     * Constructor for basic troop
     * @param pos Starting tile position.
     */
    public TankTroop(Tile pos) {
        this(null, pos);
    }

    /**
     * Constructor for basic troop
     * @param img Image used for rendering this object.
     * @param pos Starting tile position.
     */
    public TankTroop(Image img, Tile pos) {
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
    public TankTroop(Image img, Tile pos, int health, int score, double speed) {
        super(img, pos, health, score, speed);
        r=new Random();
    }
    @Override
    public void tick() {
        this.move();
    }

    @Override
    public void render(Graphics g) {

    }

    /**
     * Attacks the troop with the specified damage.
     * TankTroop has a 20% chanse to avoid the damage.
     * @param damage amount of damage to take
     * @return true if unit died, else false
     */
    @Override
    public boolean attackThis(int damage) {
        if ( ! hasReachedGoal() ) {
            if((r.nextInt(5) + 1)==1){
                damage=0;
                System.out.println("Blocked attack");
            }
            health = health - damage;
            return !this.isAlive();
        } else {
            return false;
        }
    }


}
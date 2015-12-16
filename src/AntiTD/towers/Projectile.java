package AntiTD.towers;

import AntiTD.MovableGameObject;
import AntiTD.Position;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Tobias Estefors
 * A projectile used to be sent from a tower to a troop
 */
public class Projectile implements MovableGameObject {

    private Troop target;
    private Tower tower;
    private final double speed = 1;
    private double moveProgres;
    private BufferedImage img;

    /**
     * Constructor for <b>Projectile</b> object
     * @param target destination object
     * @param tower originator object
     * @param img image for rendering
     */
    public Projectile(Troop target, Tower tower, BufferedImage img){
        super();
        this.target=target;
        this.tower=tower;
        this.img=img;
    }

    /**
     * returns this projectiles current target
     * @return A troop that is this target
     */
    public Troop getTarget(){
        return target;
    }

    /**
     * Gets called every timetick and updates this projectiles position
     */
    @Override
    public void tick() {
        this.moveProgres += speed;
        if (this.moveProgres > 100.0) {
            target.attackThis(tower.getDamage());
            if (tower instanceof FrostTower) {
                target.slowSpeed();
            }

        }

    }

    /**
     * Checks if the projectile is still in the game
     * @return true if it hasnt hit his target else false
     */
    public boolean isAlive() {
        return moveProgres > 100 ? false : true;
    }

    /**
     * Returns this projectiles visuals
     * @return a buffered image
     */
    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public int getCurrentScore() {
        return 0;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public Tile getTilePosition() {
        return tower.getTilePosition();
    }

    @Override
    public Tile getMoveToPosition() {
        return target.getTilePosition();
    }

    @Override
    public int getMoveProgress() {
        Long v = Math.round(this.moveProgres);
        return v.intValue();
    }

    @Override
    public boolean hasReachedGoal() {
        return false;
    }
}

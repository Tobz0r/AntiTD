package AntiTD.towers;

import AntiTD.MovableGameObject;
import AntiTD.Position;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dv13tes on 2015-12-10.
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
     * Get the destination target.
     * @return the target
     */
    public Troop getTarget(){
        return target;
    }

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

    @Override
    public boolean isAlive() {
        return moveProgres < 100;
    }

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

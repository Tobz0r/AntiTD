package AntiTD.towers;

import AntiTD.GameObject;
import AntiTD.Position;
import AntiTD.tiles.GoalTile;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * Created by dv13tes on 2015-12-10.
 */

/*
Velocity
(-1 / dist ) * deltaX
 */
public class Projectile implements GameObject {

    private Troop target;

    private Tower tower;

    private final double speed = 1;
    private double moveProgres;

    private BufferedImage img;

    public Projectile(Troop target, Tower tower, BufferedImage img){
        super();
        this.target=target;
        this.tower=tower;
        this.img=img;

    }

    public void damage(){
        tower.attack(target,tower.getDamage());
    }
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
    public boolean aliveTarget(){
        return target.isAlive();
    }

    public boolean isAlive() {
        return moveProgres > 100 ? false : true;
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
    public String type() {
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

    public GameObject getMoveTo() {
        return target;
    }


    public int getMoveProgress() {
        Long v = Math.round(this.moveProgres);
        return v.intValue();
    }
}

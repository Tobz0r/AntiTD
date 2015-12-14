package AntiTD.towers;

import AntiTD.GameObject;
import AntiTD.MovableGameObject;
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
public class Projectile implements MovableGameObject {

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
    public Tile getTilePosition() {
        return tower.getTilePosition();
    }

    public Troop getTarget(){
        return target;
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

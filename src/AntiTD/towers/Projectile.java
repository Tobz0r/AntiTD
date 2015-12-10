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

    private final int speed=10;
    private int moveProgres;

    private BufferedImage img;

    private boolean isMoving;

    public Projectile(Troop target, Tower tower){
        super();
        this.target=target;
        this.tower=tower;
        try {
            img= ImageIO.read(new File("sprites/fireball.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void damage(){
        tower.attack(target,tower.getDamage());
    }
    public Troop getTarget(){
        return target;
    }

    @Override
    public void tick() {
        if (target.isAlive()) {
            if (!this.isMoving) {
                this.isMoving = true;
                this.moveProgres = speed;
            }

            if (this.moveProgres < 100) {
                this.moveProgres += speed;
                if (this.moveProgres > 100) {
                    this.moveProgres = 100;
                }
            } else {
                this.isMoving = false;
                this.moveProgres = 0;
            }
        }

    }
    public boolean aliveTarget(){
        return target.isAlive();
    }

    @Override
    public void render(Graphics g) {

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

    @Override
    public int getMoveProgres() {
        return moveProgres;
    }
}

package AntiTD.towers;

import AntiTD.Handler;
import AntiTD.Position;
import AntiTD.Sounds;
import AntiTD.tiles.Tile;
import AntiTD.towers.*;
import AntiTD.troops.Troop;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class BasicTower extends Tower {
    private int damage;
    private int range;
    private int price;
    private Position pos;
    private Tile posTile;
    private Troop tr;
    private Troop target;
    private String type = "BasicTower";
    private int result;
    private BufferedImage projectileImg;
    private Handler handler;
    int bullets;
    int count;
    int cooldown;
    private Sounds sounds = new Sounds();


    public BasicTower(Image img, Tile pos, ArrayList<Troop> troops, Handler handler) {
        super(img, pos, troops);
        Random r = new Random();
        this.handler=handler;
        int low = 0;
        int High = 5;
        result = r.nextInt(High - low) + low;
        setDamage(1);
        setRange(5);
        setPrice(1);
        setPosition(pos.getPosition());
        this.posTile = pos;
        count = 0;
        cooldown=0;
        try {
            projectileImg=ImageIO.read(new File("sprites/fireball.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void initScan() {
        int distance = Integer.MAX_VALUE;
        ArrayList<Troop>troops=getTroopsList();
        for (Troop troop : troops) {
            Troop nearUnit = null;
            if (troop.isAlive()) {
                int dist = distance(troop);
                if (dist <= getRange()) {
                    pushInRange(troop);

                    if (dist < distance) {
                        nearUnit = troop;
                        setNearUnit(troop);
                        distance = dist;
                        //removeTroopFromList(troop);
                    }
                }
            }
            if (nearUnit != null) {
                target = nearUnit;
                // System.out.println("target gets value");
            }
        }
    }

    public void aggroTarget() {
        if (target != null) {
            //Projectile bullet=new Projectile(target,this);
            if (checkIfUnitIsClose(target) && target.isAlive() && cooldown> 200) {
                sounds.music("music/lazer.wav",false);
                //attack(target, getDamage());
                handler.addObject(new Projectile(target,this,projectileImg));
                cooldown=0;
            } else {
                target = null;
                //System.out.println("else");
                /*
                if (!target.isAlive()) {
                    removeTroopFromList(target);
                }
                target = null;
                getInRange().clear();
                */
            }
        }
    }
    public void pauseTowerSound(){
        sounds.pauseMusic();
    }
    public void resumeTowerSound(){
        sounds.resumeMusic(true);
    }
    public void createTower(Tower tower, Tile pos) {
        //Tower temp = new BasicTower(img,pos);
        tower.init(getTroopsList(), getTowerList(), pos);
        getTowerList().add(tower);

    }


    public void attack(Troop troop, int damage) {
        if (troop.isAlive()) {
            troop.attackThis(damage);
            if (!troop.isAlive()) {
                incrementMoney();
            }
        }
    }

    public int distance(Troop troop) {
        return (new Double(Math.hypot(troop.getPosition().getX(), troop.getPosition().getY()))).intValue();
    }

    public boolean checkIfUnitIsClose(Troop troop) {
        if (Math.hypot(troop.getPosition().getX() - getPosition().getX(), troop.getPosition().getY() - getPosition().getY()) <= getRange()) {
            return true;
        }
        return false;
    }

    public void startShooting() {
        int bullets = 5;

        if (target != null) {
            //System.out.println(target.isAlive());
            //System.out.println(target.type());
            aggroTarget();
        } else if (target == null) {
            //System.out.println("target null");
            initScan();
        }


    }

    public String getTowerType() {
        return type;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public Position getPosition() {
        return pos;
    }

    /*Test methods*/
    public Troop getTarget() {
        return target;
    }

    public void setNearUnit(Troop tr) {
        this.tr = tr;
    }

    public Troop getNearUnit() {
        return tr;
    }

    @Override
    public void tick() {
        cooldown++;
        count++;
        if (count >= 60) {
            if (this.getTroopFromList()) {
                startShooting();
            }
            //System.out.println(result);
            count = 0;
        }

    }

    @Override
    public void render(Graphics g) {

    }


    @Override
    public Tile getMoveToPosition() {
        return this.posTile;
    }

    @Override
    public int getMoveProgres() {
        return 0;
    }

}
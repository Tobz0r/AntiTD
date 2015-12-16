package AntiTD.towers;

import AntiTD.Handler;
import AntiTD.Position;
import AntiTD.Sounds;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Rasmus Dahlkvist
 */
public class FrostTower extends Tower{
    private int damage;
    private int range;
    private int price;
    private Handler handler;
    private Troop tr;
    private Troop target;
    private Position pos;
    private int count = 0;
    private Sounds sounds = new Sounds();
    private boolean playMusic = true;
    private BufferedImage projectileImg;
    private Tile posTile;
    private String type = "FrostTower";
    /**
            * Constructor for frostTower, tower which slow units that it hits.
    *
            * ** CAUTION **
            * Use this constructor for test purposes only.
    * @param img Image used for rendering this object.
    * @param pos Starting tile position.
     * @param troops Gets the troops currently alive on the map.
     * @param handler Used to implement shoot-sounds when attacking target.
    *
     */
    public FrostTower(Image img, Tile pos,ArrayList<Troop> troops,Handler handler) {

        super(img, pos, troops);
        setDamage(10);
        setRange(999);
        this.handler=handler;
        setPrice(5);
        setPosition(pos.getPosition());
        this.posTile = pos;
        try {
            projectileImg=ImageIO.read(new File("sprites/frostProjectile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void initScan() {
        int distance = Integer.MAX_VALUE;
        ArrayList<Troop> troops=getTroopsList();
        for(Troop troop : troops){
            Troop nearUnit = null;
            int dist = distance(troop);
            if(dist <= getRange()) {
                pushInRange(troop);
                if (dist < distance) {
                    nearUnit = troop;
                    setNearUnit(troop);
                    distance = dist;
                }
            }
            if(nearUnit !=null){
                target = nearUnit;
            }
        }
    }
    public void aggroTarget() {
        if (target != null) {
            Projectile bullet=new Projectile(target,this,projectileImg);
            if (checkIfUnitIsClose(target) && target.isAlive() ){
                if(playMusic){
                    sounds.music("music/lazer.wav",false);
                }
                handler.addObject(bullet);
            } else {
                if (!target.isAlive()) {
                    removeTroopFromList(target);
                }
                target = null;
                getInRange().clear();
            }
        }
    }
    public void createTower(Tower tower, Tile pos){
        tower.init(getTroopsList(), getTowerList(), pos);
        getTowerList().add(tower);

    }
    public void startShooting(){
        checkIfTroopReachedGoal();
        if (target != null) {
            if(!target.isSlowed()) {
            }
            this.aggroTarget();
        } else {
            this.initScan();
        }
    }
    public void pauseTowerSound(){
        playMusic = false;
    }
    public void resumeTowerSound(){
        playMusic = true;
    }
    public void attack(Troop troop, int damage){
        if(troop.isAlive()) {
            troop.attackThis(damage);
            if(!troop.isAlive()){
                incrementMoney();
            }
        }
    }
    public int distance(Troop troop) {
        return (new Double(Math.hypot(troop.getPosition().getX(), troop.getPosition().getY()))).intValue();
    }
    public boolean checkIfUnitIsClose(Troop troop){
        if(Math.hypot(troop.getPosition().getX() -getPosition().getX(), troop.getPosition().getY() - getPosition().getY()) <= getRange()){
            return true;
        }
        return false;
    }

  /**
   *Getters and setter for tower
   * */
    public String getTowerType(){
        return type;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public int getDamage(){
        return damage;
    }
    public void setRange(int range){
        this.range = range;
    }
    public int getRange(){
        return range;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public int getPrice(){
        return price;
    }
    public void setPosition(Position pos){
        this.pos = pos;
    }
    public Position getPosition(){
        return pos;
    }
    @Override
    public void tick(){
        count ++;
        if(count >= 60) {
            if (this.getTroopFromList()) {
                startShooting();
            }
            count = 0;
        }
    }
    /*test method*/
    public Troop getTarget(){
        return target;
    }
    public void setNearUnit(Troop tr){
        this.tr = tr;
    }
    public Troop getNearUnit(){
        return tr;
    }

    @Override
    public Tile getTilePosition() {
        return posTile;
    }
}

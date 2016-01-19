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
   * @param img Image used for rendering this object.
   * @param pos Starting tile position.
   * @param troops Gets the troops currently alive on the map.
   * @param handler Used to implement shoot-sounds when attacking target.
   *
   */
    public FrostTower(Image img, Tile pos,ArrayList<Troop> troops,Handler handler) {

        super(img, pos, troops);
        setDamage(10);
        setRange(4);
        this.handler=handler;
        setPrice(5);
        setPosition(pos.getPosition());
        this.posTile = pos;
        try {
            projectileImg=ImageIO.read( this.getClass().getResourceAsStream("/sprites/frostProjectile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

  /**
   * Tower scan for nearby Troop
   * Using distance to calculate if
   * troop is in range for the tower.
   * Then it gets at target.
   */
    public void initScan() {
        int distance = 4;
        ArrayList<Troop> troops=getTroopsList();
        for(Troop troop : troops){
            Troop nearUnit = null;
            int dist = distance(troop);
            if(dist <= getRange()) {
                pushInRange(troop);
                //if (dist < distance) {
                    nearUnit = troop;
                    setNearUnit(troop);
                    distance = dist;
                //}
            }
            if(nearUnit !=null){
                target = nearUnit;
            }
        }
    }
  /**
   * Tower starts shooting at
   * The target. Initialize a bullet and
   * shoots until target is out of range.
   */
    public void aggroTarget() {
        if (target != null) {
            Projectile bullet=new Projectile(target,this,projectileImg);
            if (checkIfUnitIsClose(target) && target.isAlive() ){
                if(playMusic){
                    sounds.music("/music/lazer.wav",false);
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
  /**
   *This is for test purpose. Merthod which create a
   * Tower that can be tested in a TestClass.
   */
    public void createTower(Tower tower, Tile pos){
        tower.init(getTroopsList(), getTowerList(), pos);
        getTowerList().add(tower);

    }
  /**
   * Check if target tower has a target.
   * Then check if it's slowed if not it start
   * shooting.
   */
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
  /**
   *Pause tower sound.
   */
    public void pauseTowerSound(){
        playMusic = false;
    }
  /**
   *Resume tower sound.
   */
    public void resumeTowerSound(){
        playMusic = true;
    }

  /**
   * Method for dealing damage.
   * on a Troop.
   * Used before we implemented projectile
   */
    public void attack(Troop troop, int damage){
        if(troop.isAlive()) {
            troop.attackThis(damage);
            if(!troop.isAlive()){
                incrementMoney();
            }
        }
    }
    /*
    public int distance(Troop troop) {
        return (new Double(Math.hypot(troop.getPosition().getX(), troop.getPosition().getY()))).intValue();
    }
    */
  /**
   * Check if Troop is in range.
   * @return true if it is, else false
   */
    public boolean checkIfUnitIsClose(Troop troop){
        if(Math.hypot(troop.getPosition().getX() -getPosition().getX(), troop.getPosition().getY() - getPosition().getY()) <= getRange()){
            return true;
        }
        return false;
    }

  /**
   * Gets this tower type.
   * @return type of tower in string
   */
    public String getTowerType(){
        return type;
    }
  /**
   * Set damage of tower.
   * @param damage
   */
    public void setDamage(int damage){
        this.damage = damage;
    }
  /**
   * Return tower damage.
   * @return damage
   */
    public int getDamage(){
        return damage;
    }
  /**
   * Set tower range.
   * @param range
   */
    public void setRange(int range){
        this.range = range;
    }
  /**
   * Return tower range.
   * @return range
   */
    public int getRange(){
        return range;
    }
    /**
   * Set tower price.
   * @param price
   */
    public void setPrice(int price){
        this.price = price;
    }
  /**
   * Return tower price.
   * @return price
   */
    public int getPrice(){
        return price;
    }
  /**
   * Set tower position.
   * @param pos
   */
    public void setPosition(Position pos){
        this.pos = pos;
    }
  /**
   * Return tower position.
   * @return position
   */
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

  /**
   * Method for testing
   * @return target.
   */
    public Troop getTarget(){
        return target;
    }
  /**
   * Method for testing
   * Set near unit to tr. Which is a troop.
   */
    public void setNearUnit(Troop tr){
        this.tr = tr;
    }
  /**
   * Method for testing
   * @return gets tr, near unit
   */
    public Troop getNearUnit(){
        return tr;
    }

  /**
   * @return towers tile.
   */
    @Override
    public Tile getTilePosition() {
        return posTile;
    }
}

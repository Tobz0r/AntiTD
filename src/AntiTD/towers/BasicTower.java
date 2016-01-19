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
 * @author Rasmus Dahlkvist
 */
public class BasicTower extends Tower {
    private int damage;
    private int range;
    private int price;
    private Position pos;
    private Troop tr;
    private Troop target;
    private String type = "BasicTower";
    private int result;
    private BufferedImage projectileImg;
    private Handler handler;
    int count;
    private Sounds sounds = new Sounds();
    private boolean playMusic=true;

  /**
   * Constructor for BasicTower.
   * @param img Image used for rendering this object.
   * @param pos Starting tile position.
   * @param troops Gets the troops currently alive on the map.
   * @param handler Used to implement shoot-sounds when attacking target.
   *
   */
    public BasicTower(Image img, Tile pos, ArrayList<Troop> troops, Handler handler) {
        super(img, pos, troops);
        Random r = new Random();
        this.handler=handler;
        int low = 0;
        int High = 5;
        result = r.nextInt(High - low) + low;
        setDamage(1);
        setRange(4);
        setPrice(1);
        setPosition(pos.getPosition());
        count = 0;

        try {
            projectileImg=ImageIO.read( this.getClass().getResourceAsStream("/sprites/fireball.gif"));
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
        int distance = Integer.MAX_VALUE;
        ArrayList<Troop>troops=getTroopsList();
        for (Troop troop : troops) {
            Troop nearUnit = null;
            if (troop.isAlive()) {
                int dist = distance(troop);
                if (dist <= getRange()) {
                    pushInRange(troop);

                    //if (dist < distance) {
                        nearUnit = troop;
                        setNearUnit(troop);
                        distance = dist;
                    //}
                }
            }
            if (nearUnit != null) {
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

    public void pauseTowerSound(){
        playMusic=false;
    }
    public void resumeTowerSound(){
        playMusic=true;
    }

  /**
   *This is for test purpose. Merthod which create a
   * Tower that can be tested in a TestClass.
   */
    public void createTower(Tower tower, Tile pos) {
        tower.init(getTroopsList(), getTowerList(), pos);
        getTowerList().add(tower);

    }
  /**
   * Method for dealing damage
   * on a Troop.
   * Used before we implemented projectile
   */
    public void attack(Troop troop, int damage) {
        if (troop.isAlive()) {
            troop.attackThis(damage);
            if (!troop.isAlive()) {
                incrementMoney();
            }
        }
    }

    /*public int distance(Troop troop) {
        int x1 = this.getTilePosition().getPosition().getX();
        int y1 = this.getTilePosition().getPosition().getY();

        int x2 = troop.getTilePosition().getPosition().getX();
        int y2 = troop.getTilePosition().getPosition().getY();

        float dist = (float) Math.sqrt(
                Math.pow(x1 - x2, 2) +
                Math.pow(y1 - y2, 2) );
        return Math.round(dist);
        //return (new Double(Math.hypot(troop.getPosition().getX(), troop.getPosition().getY()))).intValue();
    }*/
  /**
   * Check if Troop is in range.
   * @return true if it is, else false.
   */
    public boolean checkIfUnitIsClose(Troop troop) {
        if (Math.hypot(troop.getPosition().getX() - getPosition().getX(), troop.getPosition().getY() - getPosition().getY()) <= getRange()) {
            return true;
        }
        return false;
    }
  /**
   * Check if target tower has a target.
   * If it does it starts shooting.
   */
    public void startShooting() {

        if (target != null) {
            aggroTarget();
        } else if (target == null) {
            initScan();
        }


    }
  /**
   * Gets this tower type
   * @return type of tower in string.
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
   * Return tower damage
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
   * Set tower position
   * @param pos
   */
  public void setPosition(Position pos){
    this.pos = pos;
  }
  /**
   * Return tower position
   * @return position
   */
    public Position getPosition() {
        return pos;
    }

    /**Test methods
     * Get target
     * */
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

    @Override
    public void tick() {
        count++;
        if (count >= 60) {
            if (this.getTroopFromList()) {
                startShooting();
            }
            count = 0;
        }
    }

}

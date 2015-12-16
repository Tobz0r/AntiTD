package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Rasmus Dahlkvist
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    private int money;
    private Image img;
    private Image imge;

    private ArrayList<Troop> troops = new ArrayList();
    private Troop target;
    private LinkedList<Troop> inRange = new LinkedList();
    private ArrayList<Tower> towers = new ArrayList();
    private int range;
    private int damage;
    private int price;
    private Tower iniTower;
    private Tile posTile;
    private Sounds sounds;

    public Tower(Image img, Tile pos, ArrayList<Troop> troops) {
        this.img = img;
        this.money = 0;
        this.pos = pos;
        this.posTile = pos;
        this.troops = troops;

    }

  /**
   * increment money
   */
    public void incrementMoney(){
        money++;
    }

  /**
   * get units that is in range
   */
    public LinkedList getInRange(){
        return inRange;
    }

  /**
   *Insert units in inrange list
   */
    public void pushInRange(Troop troop){
        inRange.push(troop);
    }
  /**
   * Get troops list
   */
    public ArrayList getTroopsList(){
        return troops;
    }
  /**
   * Get tower list
   */
    public ArrayList getTowerList(){
        return towers;
    }
  /**
   * Test method
   */
    public void init(ArrayList<Troop> troops, ArrayList<Tower> towers, Tile pos) {
        this.pos = pos;
        this.troops = troops;
        this.towers = towers;
    }

    @Override
    /**
     * Tick method for the thread
     * tic 60 times before tower shoots
     */
    public abstract void tick();

    @Override
    public Image getImage() {

        return img;
    }
  /**
   * sets currentScore by checking if troop is alive
   * @param troop
   */
    public void setCurrentScore(Troop troop) {
        if (!troop.isAlive()) {
            money++;
        }
    }

    public abstract void startShooting();

    /**
     * If target is not null the tower will
     * attack the current target
     */
    public abstract void aggroTarget();

    /**
     * Search for a unit near the tower, using
     * the given arraylist from the environment.
     * Check if target is in the given range of the tower
     * set target parameter to the unit which is close
     */
    public abstract void initScan();

    /**
     * A method which check the distance to a given unit
     * @param troop the troop which the tower is checking distance to
     * @return Distance in int
     */
    public int distance(Troop troop) {
        int x1 = this.getTilePosition().getPosition().getX();
        int y1 = this.getTilePosition().getPosition().getY();

        int x2 = troop.getTilePosition().getPosition().getX();
        int y2 = troop.getTilePosition().getPosition().getY();

        float dist = (float) Math.sqrt(
                Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)
        );
        return Math.round(dist);
        //return (new Double(Math.hypot(troop.getPosition().getX(), troop.getPosition().getY()))).intValue();
    }
    //public abstract int distance(Troop troop);

    /**
     * Attack unit
     * @param damage
     * @param troop
     * @return true if unit died, else false
     */
    public abstract void attack(Troop troop, int damage);

  /**
   * Check if a unit is close, if it is in range
   * for the tower it will return true
   * @param troop
   * @return true if unit is in range
   */
    public abstract boolean checkIfUnitIsClose(Troop troop);

  /**
   * Get the tower type
   * @return true if unit died, else false
   */
    public abstract String getTowerType();
  /**
   * SetDamge
   */
    public abstract void createTower(Tower tower, Tile pos);

  /**
   * Getter and setter for tower information, damage, price, position and range
   */
    public abstract void setDamage(int damage);
    public abstract int getDamage();

    public abstract void setPrice(int price);

    public abstract void setRange(int range);

    public abstract int getRange();

  @Override
  public abstract Position getPosition();

  public abstract void setPosition(Position pos);
  @Override
  public Tile getTilePosition() {
    return this.posTile;
  }

    public void addTowerToList(Tower towers) {
        this.towers.add(towers);
    }

  /**
   * Removes a troop from list
   * @param troop
   */
    public void removeTroopFromList(Troop troop) {
        troops.remove(troop);
    }

    public void setMoney(int money) {
        this.money = money;
    }

  /**
   *Pause and resume tower sound
   */
    public abstract void pauseTowerSound();
    public abstract void resumeTowerSound();
    @Override

    /**
     * Return money that the tower earned
     * @return current score in int
     */
    public int getCurrentScore() {
        return money;
    }






    /*
    * Method for test
    * */
    public abstract Troop getTarget();

    public Tower getFrostTower() {
        for (Tower tower : towers) {
            if (tower.getTowerType().equals("FrostTower")) {
                return tower;
            }
        }
        return null;
    }

    public abstract Troop getNearUnit();

  /*
   * Method for test
   * */
    public int countUnitsInList() {
        return inRange.size();
    }
  /*
   * Method for test
   * */
    public int countFrostTowerTypes() {
        int frostTower = 0;
        for (Tower tower : towers) {

            if (tower.getTowerType().equals("FrostTower")) {
                frostTower++;
            }
        }
        return frostTower;
    }
  /*
   * Method for test
   * */
    public int countBasicTowerTypes() {
        int BasicTower = 0;
        for (Tower tower : towers) {

            if (tower.getTowerType().equals("BasicTower")) {
                BasicTower++;
            }
        }
        return BasicTower;
    }
  /*
   * Method for test
   * */
    public boolean getHpFromtroop() {
        for (Troop t : troops) {
            if (checkIfUnitIsClose(t)) {
                return t.isAlive();
            }
        }
        return false;

    }
  /*
   * Method for test
   * */
  public boolean getTowers() {
    return towers.isEmpty();
  }

  /*
   * Method for test
   * */
  public int getTowersLength() {
    return towers.size();
  }



  public void addTroopToList(Troop troop) {
        troops.add(troop);
    }

    public int getTroopListSize() {
        return troops.size();
    }

    public Troop getTroopFromList(int i) {
        return troops.get(i);
    }

  /**
   * Iterator through list and check if
   * unit is in range
   * @return true if unit is in range
   */
    public boolean getTroopFromList() {
        if (troops != null) {
            Collections.shuffle(troops);
            try {
                if (!troops.isEmpty()) {
                    for (Troop troop : troops) {

                        return checkIfUnitIsClose(troop);
                    }
                }

            } catch (java.util.NoSuchElementException e) {
                System.out.println(e.getMessage());

            }
        }
        return false;
    }
  /**
   * Check if unit has reached the goal iterator
   * arraylist and check the troop method HasReachedGoal
   * Then remove it if it reached
   */
    public void checkIfTroopReachedGoal(){
        if(!troops.isEmpty()) {
            Iterator<Troop> itTroop =troops.iterator();
            while(itTroop.hasNext()){
                Troop t = itTroop.next();
                if(t.hasReachedGoal()){
                    itTroop.remove();
                }
            }
        }
    }


    public String type() {
        return "Tower";
    }


}

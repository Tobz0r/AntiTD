package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import javax.swing.*;
import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.LinkedList;

/**
 * Created by dv13tes on 2015-11-27.
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    protected int money;
    private ImageIcon img;
    private Image imge;

    protected ArrayList<Troop> troops = new ArrayList();
    private Troop target;
    protected LinkedList<Troop> inRange = new LinkedList();
    protected ArrayList<Tower> towers = new ArrayList();
    private int range;
    private int damage;
    private int price;
    private Tower iniTower;
    private Tile posTile;

    public Tower(ImageIcon img, Tile pos, ArrayList<Troop> troops) {
        this.img = img;
        this.money = 0;
        this.pos = pos;
        this.posTile = pos;
        this.troops = troops;

    }
    /*public Tower(ArrayList<Troop> troops){
        this.troops = troops;
        initScan();
    }*/
    public void init(ArrayList<Troop> troops, ArrayList<Tower> towers, Tile pos){
      this.pos = pos;
      this.troops = troops;
      this.towers = towers;

    }
    @Override
    public abstract void tick();

    /*Ska vara i environment???*
    *
     */
    public void buildTower(){
        int tempMoney = getCurrentScore();
        if(pos.isBuildable()) {
            if (tempMoney >= 5) {
                Tower temp = new FrostTower(img,pos,troops);
                temp.createTower(temp,pos);
                addTowerToList(temp);
                tempMoney = tempMoney-temp.getPrice();
                setMoney(tempMoney);

            }else if(tempMoney >=1){
                Tower temp = new BasicTower(img,pos,troops);
                temp.createTower(temp,pos);
                tempMoney = tempMoney-temp.getPrice();
                addTowerToList(temp);

                setMoney(tempMoney);
            }
        }
    }
    @Override
    public Image getImage() {

        return imge;
    }
    public void setCurrentScore(Troop troop){
        if(!troop.isAlive()){
            money++;
        }
    }
    public abstract void startShooting();
    public abstract void aggroTarget();
    public abstract void initScan();
    public abstract int distance(Troop troop);
    public abstract void attack(Troop troop, int damage);
    public abstract boolean checkIfUnitIsClose(Troop troop);
    public abstract String getTowerType();
    public abstract void createTower(Tower tower, Tile pos);
    public abstract void setDamage(int damage);
    public abstract int getDamage();
    public abstract void setPrice(int price);
    public abstract int getPrice();
    public abstract void setRange(int range);
    public abstract int getRange();
    public void addTowerToList(Tower towers){
      this.towers.add(towers);
    }

    public void setMoney(int money){
        this.money = money;
    }
    @Override
    public int getCurrentScore() {
        return money;
    }

    @Override
    public abstract Position getPosition();
    public abstract void setPosition(Position pos);


    /*
    * Method for test
    * */
    public abstract Troop getTarget();
    public Tower getFrostTower(){
        for(Tower tower: towers){
            if(tower.getTowerType().equals("FrostTower")){
                return tower;
            }
        }
        return null;
    }
    public abstract Troop getNearUnit();
    public int countUnitsInList(){
        return inRange.size();
    }

     public int countFrostTowerTypes(){
      int frostTower = 0;
      for(Tower tower : towers){

        if(tower.getTowerType().equals("FrostTower")){
          frostTower++;
        }
      }
      return frostTower;
    }
   public int countBasicTowerTypes(){
      int BasicTower = 0;
      for(Tower tower : towers){

        if(tower.getTowerType().equals("BasicTower")){
          BasicTower++;
      }
      }
      return BasicTower;
   }
    public void addTroopsToList(Troop troop){
        troops.add(troop);
    }
    public boolean getHpFromtroop(){
        for(Troop t : troops){
            if(checkIfUnitIsClose(t)){
                return t.isAlive();
            }
        }
        return false;

    }
    public void setTroopsToList(ArrayList<Troop> troops){
        this.troops = troops;

    }
    public boolean getTowers(){
     return towers.isEmpty();
    }
    public int getTowersLength(){
    return towers.size();
  }
    public String type(){
        return "Tower";
    }
    @Override
    public Tile getTilePosition() {
        return this.posTile;
    }
}

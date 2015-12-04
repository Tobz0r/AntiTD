package AntiTD.towers;

import AntiTD.Position;
import AntiTD.tiles.Tile;
import AntiTD.towers.*;
import AntiTD.troops.Troop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
    public BasicTower(ImageIcon img, Tile pos, ArrayList<Troop> troops) {

        super(img, pos, troops);
        setDamage(5);
        setRange(5);
        setPrice(1);
        setPosition(pos.getPosition());
        this.posTile = pos;
    }
    public void initScan() {
      int distance = Integer.MAX_VALUE;
      for(Troop troop : troops){
      Troop nearUnit = null;
      int dist = distance(troop);
      if(dist <= getRange()) {
        inRange.push(troop);
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
    public void aggroTarget(){
      if(target != null){
          //System.out.println("jao");
        if(checkIfUnitIsClose(target) && target.isAlive() == true){

          attack(target,getDamage());
        }else{
            //System.out.println("else");
          target = null;
          inRange.clear();
        }
      }
    }
    public void createTower(Tower tower,Tile pos){
      //Tower temp = new BasicTower(img,pos);
      tower.init(troops, towers, pos);
      towers.add(tower);

    }
    public void attack(Troop troop, int damage){
        if(troop.isAlive()) {
            troop.attackThis(damage);
            if(!troop.isAlive()){
                money++;
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
    public void startShooting(){
      if(target != null){
          //System.out.println(target.isAlive());
          //System.out.println(target.type());
        aggroTarget();
      }else{
          //System.out.println("target null");
        initScan();
      }
    }
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

    /*Test methods*/
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
    public void tick(){
        this.startShooting();
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

package AntiTD.GameBackEnd.towers;

import AntiTD.GameBackEnd.Position;
import AntiTD.GameBackEnd.tiles.Tile;
import AntiTD.towers.*;
import AntiTD.GameBackEnd.troops.Troop;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class BasicTower extends Tower {
    private int damage;
    private int range;
    private int price;
    private Position pos;
    private Troop target;
    private String type = "BasicTower";
    public BasicTower(ImageIcon img, Tile pos) {

        super(img, pos);
        setDamage(5);
        setRange(5);
        setPrice(1);
        setPosition(pos.getPosition());
    }
    public void initScan() {
      double distance = Integer.MAX_VALUE;
      for(Troop troop : troops){
      Troop nearUnit = null;
      double dist = distance(troop);
      if(dist <= getRange()) {
        inRange.push(troop);
          if (dist < distance) {
           nearUnit = troop;
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
        if(checkIfUnitIsClose(target) && target.isAlive()){
          attack(target,getDamage());
        }else{
          target = null;
          inRange.clear();
        }
      }
    }
    public void createTower(Tower temp,Tile pos){
      //Tower temp = new BasicTower(img,pos);
      temp.init(troops, towers, pos);
      towers.add(temp);

    }
    public void attack(Troop troop, int damage){
      troop.attackThis(damage);
    }
    public double distance(Troop troop) {
      return Math.hypot(troop.getPosition().getX(), troop.getPosition().getY());
    }
    public boolean checkIfUnitIsClose(Troop troop){
    if(Math.hypot(troop.getPosition().getX() -getPosition().getX(), troop.getPosition().getY() - getPosition().getY()) <= getRange()){
      return true;
      }
      return false;
    }
    public void startShooting(){
      if(target != null){
        aggroTarget();
      }else{
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
    public void tick(){


    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Tile getTilePosition() {
        return null;
    }
}
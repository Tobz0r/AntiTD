package AntiTD.towers;

import AntiTD.Handler;
import AntiTD.Position;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import javax.swing.*;
import java.awt.*;

/**
 * Created by id12rdt on 2015-11-30.
 */
public class FrostTower extends Tower{
    private int damage;
    private int range;
    private int price;
    private Troop target;
    private Position pos;
    private String type = "FrostTower";
    ImageIcon img;
    public FrostTower(ImageIcon img, Tile pos) {

      super(img, pos);
      setDamage(10);
      setRange(10);
      setPrice(5);
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
        } else{
          target = null;
          inRange.clear();
        }
     }
    }
    public void createTower(Tower tower, Tile pos){
      //Tower temp = new FrostTower(img,pos);
      tower.init(troops, towers, pos);
      towers.add(tower);
    }
    public void startShooting(){
      if(target != null){
        aggroTarget();
      }else{
        initScan();
      }
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
        return this.getTilePosition();
    }

    @Override
    public Tile getMoveToPosition() {
        return this.getTilePosition();
    }

    @Override
    public int getMoveProgres() {
        return 0;
    }
}

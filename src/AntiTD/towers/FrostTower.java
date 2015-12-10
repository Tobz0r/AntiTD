package AntiTD.towers;

import AntiTD.Handler;
import AntiTD.Position;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import javax.swing.*;
import java.awt.*;
import java.lang.annotation.Target;
import java.util.*;

/**
 * Created by id12rdt on 2015-11-30.
 */
public class FrostTower extends Tower{
    private int damage;
    private int range;
    private int price;
    private Troop tr;
    private Troop target;
    private Position pos;
    private int count = 0;
    private Tile posTile;
    private String type = "FrostTower";
    private boolean slowedTarget;
    private LinkedList<Troop> slowedTroop = new LinkedList<Troop>();
    private HashMap<Troop,Boolean> targetSlowed = new HashMap<>();
    private int targetNumb = 0;
    private int towerSpeed;
    private double shootDistance;
    private boolean attacking;
    private Bullets bullets;
    private Stack<Tile> history;
    private Image bulletImg;
    ImageIcon img;
    public FrostTower(Image tower, Tile pos,ArrayList<Troop> troops, Bullets bullets) {

      super(tower, pos, troops);
      setDamage(1);
      setRange(5);
      setPrice(5);
        setTowerSpeed(20);

      setPosition(pos.getPosition());
        slowedTarget = false;
        this.posTile = pos;
        this.bullets = bullets;
        history = new Stack<Tile>();
        this.history.push(pos);



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
    public void aggroTarget(){
        if(target != null) {
            bullets = new Bullets(bulletImg,getDamage(),5,this.getTilePosition());

            if (checkIfUnitIsClose(target) && target.isAlive() == true) {
                //System.out.println("jao");
                attack(target, getDamage());
                bullets.setTarget(target);
                bullets.tick();
            } else {
                //System.out.println("else");
                if (!target.isAlive()) {
                    removeTroopFromList(target);
                }
                target = null;
                getInRange().clear();
            }
        }
    }
    public void pointAtTroop() {
        double troopAng = Math.atan2(target.getPosition().getX() - this.pos.getX(), target.getPosition().getY() - this.pos.getY());
        //attack = true;
    }
    public void createTower(Tower tower, Tile pos){
      //Tower temp = new FrostTower(img,pos);
      tower.init(getTroopsList(), getTowerList(), pos);
      getTowerList().add(tower);

    }
    public void startShooting(){
        checkIfTroopReachedGoal();
        if (target != null) {

            if(!target.isSlowed()) {
                System.out.println("hola");
                target.slowSpeed();
            }
            this.aggroTarget();
        } else {
            //   System.out.println("Target null");
            this.initScan();
        }
    }
    public void attack(Troop troop, int damage){
        if(troop.isAlive()) {
           /* bullets = new Bullets(getDamage(),5,this.getTilePosition());
            this.shoot(bullets,target);*/
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
        if(count >= getTowerSpeed()) {
            if (this.getTroopFromList()) {
                startShooting();

            }
            //System.out.println(result);
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
    public void setValueInHashMap(String target) {

    }
    public void setTowerSpeed(int towerSpeed){
        this.towerSpeed = towerSpeed;
    }
    public int getTowerSpeed(){
        return towerSpeed;
    }

    /*protected void shoot(Bullets bullets, Troop target){
        this.target = target;
        this.bullets = bullets;

        if(!attacking){
            attacking = true;
            this.shootDistance = bullets.getSpeed();
            bullets.setPosition(getNextTile());
            System.out.println(shootDistance);
        }
        if(this.shootDistance <100){
            this.shootDistance += bullets.getSpeed();
            //System.out.println(shootDistance);
            System.out.println("y: " + bullets.getPosition().getY() + "x: " +bullets.getPosition().getX());
            if(this.shootDistance > 100){
                this.shootDistance = 100;
                System.out.println("ellu");

            }
        }else{
            this.attacking = false;
            this.shootDistance = 0;
            attack(target,bullets.getDamage());
            history.push(bullets.getTilePosition());
        }

    }
    public Tile getNextTile(){
        Tile[] neighbors;
        neighbors = history.peek().getNeighbors2();

        Tile nextTile = null;
        for(Tile tile : neighbors){
            if(history.search(tile) == -1){
                nextTile = tile;
                break;
            }
        }

        return nextTile;
    }*/


    @Override
    public void render(Graphics g) {

    }

    @Override
    public Tile getTilePosition() {
        return posTile;
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

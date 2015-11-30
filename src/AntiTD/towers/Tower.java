package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by dv13tes on 2015-11-27.
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    private int money;
    private Image img;

    ArrayList<Troop> troops;
    private Troop target;
    Stack<Troop> inRange;
    private int range;
    private int damage;
    private int price;

    public Tower(Image img, Tile pos) {
        this.img = img;
        this.money = 0;
        this.pos = pos;
    }
    public Tower(ArrayList<Troop> troops){
        this.troops = troops;
        initScan();
    }
    /*Border vara i underklassen*/
    public void initFrostTower(){

        damage = 10;
        range = 10;
        price = 5;
    }
    /*Border vara i underklassen*/
    public void initBasicTower(){
        damage = 5;
        range = 5;
        price = 1;
    }
    public void initScan() {
        double distance = Integer.MAX_VALUE;
        for(Troop troop : troops){

            Troop nearUnit = null;
            double dist = distance(troop);
            if(dist <= range) {
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
    @Override
    public abstract void tick();

    public void aggroTarget(){
        if(target != null){
            if(checkIfUnitIsClose(target)){
                attack(target,damage);
            }else{
                target = null;
                initScan();
            }
        }
    }
    /*Ska vara i environment*/
    public void buildTower(){
        int tempMoney = getCurrentScore();
        if(pos.isBuildable()) {
            if (tempMoney >= 5) {
                initFrostTower();
                tempMoney = tempMoney-price;
                setMoney(tempMoney);

            }else{
                tempMoney = tempMoney-price;
                initBasicTower();
                setMoney(tempMoney);
            }
        }
    }
    public void attack(Troop troop, int damage){

        troop.attackThis(damage);

    }
    public boolean checkIfUnitIsClose(Troop troop){
        if(Math.hypot(troop.getPosition().getX() -pos.getPosition().getX(), troop.getPosition().getY() - pos.getPosition().getY()) <= range){
            return true;
        }
        return false;
    }
    public double distance(Troop troop){
        return Math.hypot(troop.getPosition().getX(), troop.getPosition().getY());
    }

    @Override
    public Image getImage() {
        return img;
    }


    public void setCurrentScore(Troop troop){
        if(!troop.isAlive()){
            money++;
        }
    }
    public void setMoney(int money){
        this.money = money;
    }
    @Override
    public int getCurrentScore() {
        return money;
    }

    @Override
    public Position getPosition() {
        return pos.getPosition();
    }

}

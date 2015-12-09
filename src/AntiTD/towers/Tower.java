package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by dv13tes on 2015-11-27.
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    private int money;
    private Image img;
    private Image imgBullets;

    private ArrayList<Troop> troops = new ArrayList();
    private Troop target;
    private LinkedList<Troop> inRange = new LinkedList();
    private ArrayList<Tower> towers = new ArrayList();
    private int range;
    private int damage;
    private int price;
    private Tower iniTower;
    private Tile posTile;
    private double shootDistance;
    private boolean attacking;
    private Bullets bullets;
    private Stack<Tile> history;

    public Tower(Image img, Tile pos, ArrayList<Troop> troops) {
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
    public void incrementMoney(){
        money++;
    }
    public LinkedList getInRange(){
        return inRange;
    }
    public void pushInRange(Troop troop){
        inRange.push(troop);
    }
    public ArrayList getTroopsList(){
        return troops;
    }
    public ArrayList getTowerList(){
        return towers;
    }
    public void init(ArrayList<Troop> troops, ArrayList<Tower> towers, Tile pos) {
        this.pos = pos;
        this.troops = troops;
        this.towers = towers;

    }

    @Override
    public abstract void tick();

    /*protected void shoot(Bullets bullets, Troop target){
        this.target = target;
        this.bullets = bullets;

        if(!attacking){
            attacking = true;
            this.shootDistance = bullets.getSpeed();
            bullets.setPosition(getNextTile());
        }
        if(this.shootDistance <100){
            this.shootDistance += bullets.getSpeed();
            if(this.shootDistance > 100){
                this.shootDistance = 100;
            }
        }else{
            this.attacking = false;
            this.shootDistance = 0;
            history.push(bullets.getPosition());
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


    /*Ska vara i environment???*
    *
     */
    /*public void buildTower() {
        int tempMoney = getCurrentScore();
        if (pos.isBuildable()) {
            if (tempMoney >= 5) {
                Tower temp = new FrostTower(img, pos, troops);
                temp.createTower(temp, pos);
                addTowerToList(temp);
                tempMoney = tempMoney - temp.getPrice();
                setMoney(tempMoney);

            } else if (tempMoney >= 1) {
                Tower temp = new BasicTower(img, pos, troops);
                temp.createTower(temp, pos);
                tempMoney = tempMoney - temp.getPrice();
                addTowerToList(temp);

                setMoney(tempMoney);
            }
        }
    }*/


    @Override
    public Image getImage() {

        return img;
    }

    public void setCurrentScore(Troop troop) {
        if (!troop.isAlive()) {
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

    public void addTowerToList(Tower towers) {
        this.towers.add(towers);
    }

    public void removeTroopFromList(Troop troop) {
        troops.remove(troop);
    }

    public void setMoney(int money) {
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

    public Tower getFrostTower() {
        for (Tower tower : towers) {
            if (tower.getTowerType().equals("FrostTower")) {
                return tower;
            }
        }
        return null;
    }

    public abstract Troop getNearUnit();

    public int countUnitsInList() {
        return inRange.size();
    }

    public int countFrostTowerTypes() {
        int frostTower = 0;
        for (Tower tower : towers) {

            if (tower.getTowerType().equals("FrostTower")) {
                frostTower++;
            }
        }
        return frostTower;
    }

    public int countBasicTowerTypes() {
        int BasicTower = 0;
        for (Tower tower : towers) {

            if (tower.getTowerType().equals("BasicTower")) {
                BasicTower++;
            }
        }
        return BasicTower;
    }

    /* public void addTroopsToList(Troop troop){
         troops.add(troop);
     }*/
    public boolean getHpFromtroop() {
        for (Troop t : troops) {
            if (checkIfUnitIsClose(t)) {
                return t.isAlive();
            }
        }
        return false;

    }

    public void addTroopToList(Troop troop) {
        troops.add(troop);
    }

    public void setTroopsToList(ArrayList<Troop> troops) {
        this.troops = troops;
    }

    public int getTroopListSize() {
        return troops.size();
    }

    public Troop getTroopFromList(int i) {
        return troops.get(i);
    }

    public boolean getTroopFromList() {
        if (troops != null) {
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
    public void checkIfTroopReachedGoal(){
        if(!troops.isEmpty()) {
            Iterator<Troop> itTroop =troops.iterator();
            while(itTroop.hasNext()){
                Troop t = itTroop.next();
                if(t.hasReacedGoal()){
                    itTroop.remove();
                }

            }
        }
    }

    public boolean getTowers() {
        return towers.isEmpty();
    }

    public int getTowersLength() {
        return towers.size();
    }

    public String type() {
        return "Tower";
    }

    @Override
    public Tile getTilePosition() {
        return this.posTile;
    }
}


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
 * Created by id12rdt on 2015-11-30.
 */
public class FrostTower extends Tower{
    private int damage;
    private int range;
    private int price;
    private Handler handler;
    private Troop tr;
    private Troop target;
    private Position pos;
    private int cooldown=0;
    private int count = 0;
    private Sounds sounds = new Sounds();
    private boolean playMusic = true;
    private BufferedImage projectileImg;
    private Tile posTile;
    private String type = "FrostTower";
    private boolean slowedTarget;
    private LinkedList<Troop> slowedTroop = new LinkedList<Troop>();
    private HashMap<Troop,Boolean> targetSlowed = new HashMap<>();
    private int targetNumb = 0;
    ImageIcon img;
    public FrostTower(Image img, Tile pos,ArrayList<Troop> troops,Handler handler) {

        super(img, pos, troops);
        setDamage(10);
        setRange(10);
        this.handler=handler;
        setPrice(5);
        setPosition(pos.getPosition());
        slowedTarget = false;
        this.posTile = pos;
        try {
            projectileImg=ImageIO.read(new File("sprites/frostProjectile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


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
    public void aggroTarget() {
        if (target != null) {
            Projectile bullet=new Projectile(target,this,projectileImg);
            if (checkIfUnitIsClose(target) && target.isAlive() ){
                if(playMusic){
                    sounds.music("music/lazer.wav",false);
                }
                //attack(target, getDamage());
                handler.addObject(bullet);
                cooldown=0;
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
    public void createTower(Tower tower, Tile pos){
        //Tower temp = new FrostTower(img,pos);
        tower.init(getTroopsList(), getTowerList(), pos);
        getTowerList().add(tower);

    }
    public void startShooting(){
        checkIfTroopReachedGoal();
        if (target != null) {

            if(!target.isSlowed()) {
                //target.slowSpeed();
            }
            this.aggroTarget();
        } else {
            //   System.out.println("Target null");
            this.initScan();
        }
    }
    public void pauseTowerSound(){
        playMusic = false;
    }
    public void resumeTowerSound(){
        playMusic = true;
    }
    public void attack(Troop troop, int damage){
        if(troop.isAlive()) {
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
        cooldown++;

        count ++;
        if(count >= 60) {
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
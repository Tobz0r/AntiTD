package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by dv13tes on 2015-11-27.
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    private int score;
    private Image img;
    private int damage;
    ArrayList<Troop> troops;
    private Troop target;
    Stack<Troop> inRange;
    int range;

    public Tower(Image img, Tile pos) {
        this.img = img;
        this.score = 0;
        this.pos = pos;
        range = 5;

    }

    public void init(ArrayList<Troop> troops) {
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
                inRange.clear();
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
            score++;
        }
    }
    @Override
    public int getCurrentScore() {
        return score;
    }

    @Override
    public Position getPosition() {
        return pos.getPosition();
    }

}

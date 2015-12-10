package AntiTD.towers;

import AntiTD.GameObject;
import AntiTD.Position;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;
import java.util.Stack;

/**
 * Created by id12rdt on 2015-12-09.
 */
public class Bullets implements GameObject{

    private int damage;
    private int speed;
    private Tile pos;
    private Image img;
    Troop target;
    boolean attacking;
    double shootDistance;
    Stack<Tile> history;
    double count = 0;
    public Bullets(Image img,int damage, int speed, Tile pos){
        this.damage = damage;
        this.speed = speed;
        this.pos = pos;
        this.img = img;
        attacking = false;
        shootDistance = 0;
        history = new Stack<Tile>();
        history.push(pos);

    }

    public int getDamage(){
        return  damage;
    }
    public int getSpeed(){
        return speed;
    }
    public void setPosition(Tile pos){
        this.pos = pos;
    }
    public void setTarget(Troop target){
        this.target = target;

    }
    public Troop getTarget(){
        return target;
    }
    protected void shoot(){

        if(!attacking){
            attacking = true;
            this.shootDistance = this.getSpeed();
            this.setPosition(getNextTile());
            System.out.println(shootDistance);
        }
        if(this.shootDistance <100){
            this.shootDistance += this.getSpeed();
            //System.out.println(shootDistance);
            System.out.println("y: " + this.getPosition().getY() + "x: " + this.getPosition().getX());
            if(this.shootDistance > 100){
                this.shootDistance = 100;
                System.out.println("ellu");

            }
        }else{
            this.attacking = false;
            this.shootDistance = 0;
            target.attackThis(getDamage());
            history.push(this.getTilePosition());
            if(checkIfBulletHitTarget(target)){
                target.attackThis(getDamage());
            }
        }

    }

    public Tile getNextTile(){
        Tile[] neighbors;
        neighbors = history.peek().getNeighbors2();
       Tile  nextTile = null;

        return nextTile;
    }
    public int distance(Troop troop) {
        return (new Double(Math.hypot(troop.getPosition().getX(), troop.getPosition().getY()))).intValue();
    }
    public boolean checkIfBulletHitTarget(Troop troop){
        if(target.getPosition() == pos.getPosition()){
            return true;
        }
        return false;
    }
    @Override
    public void render(Graphics g) {

    }
    @Override
    public Tile getMoveToPosition(){
        return pos;
    }
    @Override
    public void tick(){

        //shoot();

    }

    public Image getImage(){
        return img;
    }

    public int getCurrentScore(){
        return 0;
    }

    @Override
    public Position getPosition(){
        return history.peek().getPosition();
    }
    public String type(){
        return "bullets";
    }

    public Tile getTilePosition(){
        return pos;
    }
    public int getMoveProgres(){
        return 0;
    }

}

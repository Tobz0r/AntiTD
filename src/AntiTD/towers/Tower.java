package AntiTD.towers;

import AntiTD.*;
import AntiTD.tiles.Tile;
import AntiTD.troops.Troop;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public abstract class Tower implements GameObject {
    private Tile pos;
    private int score;
    private Image img;
    Troop players;

    public Tower(Image img, Tile pos) {
        this.img = img;
        this.score = 0;
        this.pos = pos;

    }

    @Override
    public abstract void tick();

    protected double fireX;
    protected double fireY;
    public void scanPlayer(){

        double dist = Integer.MAX_VALUE;
        for(Troop t : players){



        }


    }
    public boolean checkIfUnitIsClose(){
        if(Math.hypot(players.getPosition().getPosition().getX()))
    }
    public double distance(){
        return Math.hypot();
    }

    @Override
    public Image getImage() {
        return img;
    }

    public void setCurrentScore(){
        if(!players.isAlive()){
            score++;
        }
    }
    @Override
    public int getCurrentScore() {
        return score;
    }

    @Override
    public Tile getPosition() {
        return pos;
    }

}

package AntiTD.towers;

import AntiTD.tiles.Tile;
import AntiTD.towers.*;

import java.awt.*;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class BasicTower extends Tower {
    private int damage;
    private int range;




    public BasicTower(Image img, Tile pos) {
        super(img, pos);
    }
    public void init(){
        damage = 5;
        range = 5;
    }

    public int getDamage(){
        return damage;
    }
    public int getRange(){
        return range;
    }

    public void tick(){


    }
}

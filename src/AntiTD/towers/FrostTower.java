package AntiTD.towers;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by id12rdt on 2015-11-30.
 */
public class FrostTower extends Tower{
    private int damage;
    private int range;




    public FrostTower(Image img, Tile pos) {
        super(img, pos);
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

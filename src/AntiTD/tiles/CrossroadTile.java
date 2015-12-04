package AntiTD.tiles;

import AntiTD.Position;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class CrossroadTile extends Tile {

    private ArrayList<Tile> nextTiles;

    public CrossroadTile() {
        this(null);
    }
    public CrossroadTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
        nextTiles=new ArrayList<>();
    }

    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(getPosition().getX()*48,getPosition().getY()*48,getSize(),getSize());
    }
    public void findNextWay(){
        ArrayList<Tile>neighbors=getNeighbors();
        for(Tile tile: neighbors){
            if((tile.isMoveable()) && !(tile instanceof JunctionTile)){
                nextTiles.add(tile);
            }
        }
    }
}

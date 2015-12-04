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
        g.fillRect((int)(getPosition().getX()*(getSize().getWidth())),(int)(getPosition().getY()*(getSize().getHeight())),(int)getSize().getWidth(),(int)getSize().getHeight());
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

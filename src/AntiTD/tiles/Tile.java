package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;

import java.util.ArrayList;

public abstract class Tile {


    private boolean moveable;
    private boolean buildable;
    private Position position;


    public boolean setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
    public boolean setMoveable(boolean moveable){
        this.moveable=moveable;
    }
    public boolean isMoveable() {
        return moveable;
    }
    public boolean isBuildable() {
        return buildable;
    }
    public void setPosition(Position position){
        this.position=position;
    }
    public Position getPosition(){
        return position;
    }
    public ArrayList<Tile> getNeighbours(Tile node){
        ArrayList<Tile> neighbours=new ArrayList<Tile>();
        Tile[][] map=Level.getCurrentMap();
        int row=node.getPosition().getX();
        int column=node.getPosition().getY();
        for(int i = -1; i < 2; i++) {
            if (!(row+i <0 || row+i >= map.length) {
                for (int j = -1; j < 2; j++) {
                    if (!(column+j < 0 || column+j >= map[i].length) {
                        if(i != 0 || j != 0) {
                            neighbours.add(map[i][j]);
                        }
                    }
                }
            }
        }
        return neighbours;
    }
}

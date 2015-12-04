package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
import AntiTD.troops.Troop;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Tile implements TileRender {


    private boolean moveable;
    private boolean buildable;
    private boolean isTeleportStart;
    private Tile teleportEnd;
    private Dimension size=new Dimension(48,48);

    private Position position;
    private Tile[] neighbors;
    Troop player;

    public Tile(Position pos) {
        this.position = pos;
    }
    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
    public void setMoveable(boolean moveable){
        this.moveable=moveable;
    }
    public boolean isMoveable() {
        return moveable;
    }
    public boolean isBuildable() {
        return buildable;
    }
    public Dimension getSize(){
        return size;
    }
    public void setPosition(Position position){
        this.position=position;
    }
    public Position getPosition(){
        return position;
    }
    public ArrayList<Tile> getNeighbors(){
        ArrayList<Tile> neighbours=new ArrayList<Tile>();
        Tile[][] map=Level.getCurrentMap();
        int row=getPosition().getX();
        int column=getPosition().getY();
        int rows=map.length;
        int columns=map[0].length;
        for(int i = -1; i < 2; i++) {
            if (!(row+i <0 || row+i >= rows)) {
                for (int j = -1; j < 2; j++) {
                    if (!(column+j < 0 || column+j >= columns)) {
                        if(i != 0 && j != 0) {
                            neighbours.add(map[i+row][j+column]);
                        }
                    }
                }
            }
        }
        return neighbours;
        /*
        rad -1 col 0
        rad 1 col 0
         rad 0 col -1
         rad 0 col 1

         */
    }

    public void setSize(Dimension size){
        this.size=size;
    }
    public Tile[] getNeighbors2() {
        return neighbors;
    }
    public void setTeleportTo(Tile tile){
        this.teleportEnd=tile;
    }
    public Tile getTeleportTo(){
        return teleportEnd;
    }
    public boolean isTeleporter(){
        return isTeleportStart;
    }

    public void setNeighbors(Tile[] neighbors) {
        this.neighbors = neighbors;
    }

    public String toString(){
        return "Tile X: "+getPosition().getX()+" Y: "+getPosition().getY();
        //return getClass().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (position != null ? !position.equals(tile.position) : tile.position != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(neighbors, tile.neighbors);

    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (neighbors != null ? Arrays.hashCode(neighbors) : 0);
        return result;
    }


}

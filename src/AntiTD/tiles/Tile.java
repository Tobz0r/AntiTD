package AntiTD.tiles;

/**
 * Created by dv13trm on 2015-11-27.
 */
import AntiTD.Position;
import AntiTD.troops.Troop;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Tile implements TileRender {


    private boolean moveable;
    private boolean buildable;
    private boolean isTeleportStart;
    private boolean isTeleporter;
    private Tile teleportEnd;
    private Dimension size=new Dimension(48,48);
    private BufferedImage image=null;
    private Position position;
    private Tile[] neighbors;
    Troop player;

    public Tile(Position pos) {
        this.position = pos;
        isTeleportStart=false;
        isTeleporter=false;
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

    public void setImage(BufferedImage image){
        this.image=image;
    }
    public BufferedImage getImage(){
        return image;
    }

    public void setSize(Dimension size){
        this.size=size;
    }
    public Tile[] getNeighbors2() {
        return neighbors;
    }
    public void setTeleportTo(Tile tile){
        this.teleportEnd=tile;
        isTeleportStart = true;
        isTeleporter=true;
        teleportEnd.setTeleporterimage(true);

    }
    public void resetTeleport(){
        isTeleporter=false;
        isTeleportStart=false;
        setTeleporterimage(false);
        teleportEnd=null;
    }
    public boolean isTeleporterimage(){
        return isTeleporter;
    }
    public void setTeleporterimage(boolean isTeleporter){
        this.isTeleporter=isTeleporter;
    }
    public Tile getTeleportTo(){
        return teleportEnd;
    }
    public boolean isTeleporter(){
        return this.isTeleportStart;
    }

    public void setNeighbors(Tile[] neighbors) {
        this.neighbors = neighbors;
    }

    public String toString(){
        return "Tile X: "+getPosition().getX()+" Y: "+getPosition().getY();
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

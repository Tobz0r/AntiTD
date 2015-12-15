package AntiTD.tiles;


import AntiTD.Position;
import AntiTD.troops.Troop;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Tobias Estefors
 */
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

    public Tile(Position pos) {
        this.position = pos;
        isTeleportStart=false;
        isTeleporter=false;
    }

    /**
     * Specifies if this tile is buildable or not
     * @param true if tile is buildable else false
     */
    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }
    /**
     * Specifies if this tile is walkable or not
     * @param true if tile is walkable else false
     */
    public void setMoveable(boolean moveable){
        this.moveable=moveable;
    }

    /**
     * Checks if this tile is walkable or not
     * @return true if tile is walkable else false
     */
    public boolean isMoveable() {
        return moveable;
    }

    /**
     * Checks if this tile is buildable or not
     * @return true if its buildable else false
     */
    public boolean isBuildable() {
        return buildable;
    }

    /**
     * Returns the size of this tile
     * @return An dimension containing width and height for this tile
     */
    public Dimension getSize(){
        return size;
    }

    /**
     * Sets the size of this tile
     * @param size an dimension containing width and height
     */
    public void setSize(Dimension size){
        this.size=size;
    }

    /**
     * Sets the position of this tile
     * @param position the position of this tile
     */
    public void setPosition(Position position){
        this.position=position;
    }

    /**
     * Returns the position of this tile
     * @return this tiles position
     */
    public Position getPosition(){
        return position;
    }

    /**
     * Sets the image to be drawn on the gameboard for this tile
     * @param image this tiles image
     */
    public void setImage(BufferedImage image){
        this.image=image;
    }

    /**
     * Returns the image of this tile
     * @return a bufferedtimage of this tile
     */
    public BufferedImage getImage(){
        return image;
    }

    /**
     * Returns an array of this tiles neighbors
     * @return an array of tiles
     */
    public Tile[] getNeighbors() {
        return neighbors;
    }
    public void setNeighbors(Tile[] neighbors) {
        this.neighbors = neighbors;
    }


    public void setTeleportTo(Tile tile){
        this.teleportEnd=tile;
        isTeleportStart = true;
        isTeleporter=true;
        teleportEnd.setTeleporterimage(true);

    }
    public Tile getTeleportTo(){
        return teleportEnd;
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
    public boolean isTeleporter(){
        return this.isTeleportStart;
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

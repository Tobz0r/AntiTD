package AntiTD.tiles;

import AntiTD.Position;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Tobias Estefors
 * Tile wich way can be changed with a mouseclick.
 */
public class CrossroadTile extends Tile {

    private ArrayList<Tile> nextTiles;
    private BufferedImage basicTile;

    public CrossroadTile() {
        this(null);
    }
    public CrossroadTile(Position pos){
        super(pos);
        setBuildable(false);
        setMoveable(true);
        try {
            basicTile=ImageIO.read(new File("sprites/patheses.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        nextTiles=new ArrayList<>();
    }

    /**
     * Draws the tile on the board
     * @param g the board graphics
     */
    @Override
    public void landOn(Graphics g) {
        g.setColor(Color.red);
        if(getImage()!=null){
            g.drawImage(basicTile,(int)(getPosition().getX()*(getSize().getWidth())),
                    (int)(getPosition().getY()*(getSize().getHeight())),
                    (int)getSize().getWidth(),
                    (int)getSize().getHeight(),null);
            g.drawImage(getImage(),(int)(getPosition().getX()*(getSize().getWidth())),
                    (int)(getPosition().getY()*(getSize().getHeight())),
                    (int)getSize().getWidth(),
                    (int)getSize().getHeight(),null);
        }
    }

    /**
     * Creates an array of avalible pathways
     * @return a array with tiles
     */
    public Tile[] findNextWay(){
        nextTiles.clear();
        Tile[] neighbors = getNeighbors();
        Tile[] arrNeighbors=new Tile[neighbors.length];
        for(Tile tile: neighbors){
            if(!(tile instanceof JunctionTile )){
                nextTiles.add(tile);
            }
        }
        for(int i=0; i < nextTiles.size();i++){
            arrNeighbors[i]=nextTiles.get(i);
        }
        return arrNeighbors;
    }

    /**
     * Swaps an array positions, since the neighbors is checked from 0 to size, the ways will be swaped
     * @param tiles and array of tiles
     * @return a reversed array of tiles
     */
    private Tile[] reverseArray(Tile[] tiles){
        Tile[] copy = tiles.clone();
        Collections.reverse(Arrays.asList(copy));
        return copy;
    }
    /**
     * Shuffles an array positions, since the neighbors is checked from 0 to size, the ways will be swaped
     * @param tiles and array of tiles
     * @return a reversed array of tiles
     */
    private Tile[] shuffleArray(Tile[] tiles){
        Tile[] copy = tiles.clone();
        while(copy[0]!=tiles[0])
            Collections.shuffle(Arrays.asList(copy));
        return copy;
    }

    /**
     * Changes the pathway for the crossroad
     * @throws IOException
     */
    public void changeWay() throws IOException {
        Tile[] current= getNeighbors();
        Tile[] newPath=new Tile[current.length];
        for(int i=0; i < current.length;i++){
            newPath[i]=current[i];

        }
        if (newPath.length <= 3){
            newPath = reverseArray(newPath);
        }else{
            newPath=shuffleArray(newPath);
        }
        if (newPath[0] == null) {
            newPath[0] = newPath[1];
        }
        Position p=newPath[0].getPosition();
        if(getPosition().IsPosToEast(p)){
            setImage(ImageIO.read(new File("sprites/right.gif")));
        }else if(getPosition().IsPosToNorth(p)){
            setImage(ImageIO.read(new File("sprites/up.gif")));
        }
        else if(getPosition().IsPosToSouth(p)){
            setImage(ImageIO.read(new File("sprites/down.gif")));
        }
        else if(getPosition().IsPosToWest(p)){
            setImage(ImageIO.read(new File("sprites/left.gif")));
        }
        setNeighbors(newPath);
    }
    public String toString(){
        return "Tile X: "+getPosition().getX()+" Y: "+getPosition().getY();
    }

}

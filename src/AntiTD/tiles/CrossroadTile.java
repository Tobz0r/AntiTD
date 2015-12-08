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
        if(getImage()==null){
            g.fillRect((int)(getPosition().getX()*(getSize().getWidth())),
                    (int)(getPosition().getY()*(getSize().getHeight())),
                    (int)getSize().getWidth(),
                    (int)getSize().getHeight());
        }
        else{
            g.drawImage(getImage(),(int)(getPosition().getX()*(getSize().getWidth())),
                    (int)(getPosition().getY()*(getSize().getHeight())),
                    (int)getSize().getWidth(),
                    (int)getSize().getHeight(),null);
        }
    }

    public Tile[] findNextWay(){
        nextTiles.clear();
        Tile[] neighbors = getNeighbors2();
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
    private Tile[] reverseArray(Tile[] tiles){
        Tile[] copy = tiles.clone();
        Collections.reverse(Arrays.asList(copy));
        return copy;
    }
    void changeWay() throws IOException {
        Tile[] current=getNeighbors2();
        Tile[] newPath=new Tile[current.length];
        for(int i=0; i < current.length;i++){
            newPath[i]=current[i];
        }
        newPath=reverseArray(newPath);
        if(newPath[0]==null){
            newPath[0]=newPath[1];
        }
        Position p=newPath[0].getPosition();
        System.out.println(getPosition());
        System.out.println(p);
        if(getPosition().IsPosToEast(p)){
            setImage(ImageIO.read(new File("rightarrow.png")));
            System.out.println("ELIASHEJ");
        }else if(getPosition().IsPosToNorth(p)){
            setImage(ImageIO.read(new File("uparrow.png")));
        }
        else if(getPosition().IsPosToSouth(p)){
            setImage(ImageIO.read(new File("downarrow.png")));
        }
        else if(getPosition().IsPosToWest(p)){
            setImage(ImageIO.read(new File("leftarrow.png")));
            System.out.println("ELaaaaaIASHEJ");

        }
        setNeighbors(newPath);
    }
    public String toString(){
        return "Tile X: "+getPosition().getX()+" Y: "+getPosition().getY();
    }

}

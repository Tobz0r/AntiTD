package AntiTD.tiles;

import AntiTD.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Tobias Estefors
 * A class that holds all information needed to start a level.
 * Will be created for each level when the game starts.
 */
public class Level {

    private Position startPosition=null;
    private int startingCredits;
    private Tile[][] map;
    private String name;

    private int victoryPoints;

    public Level(String name){
        this.name=name;
    }

    /**
     * Adds a matrix of tiles to the level
     * @param map a matrix of tiles
     */
    public  void addMap(Tile[][] map){
        this.map=map;
    }

    /**
     * Sets the startingcredits for this level
     * @param startingCredits an integer containing starting credits
     */
    public void setStartingCredits(int startingCredits){
        this.startingCredits=startingCredits;
    }

    /**
     * Returns this levels startingcredit
     * @return an integer of startingcredits
     */
    public int getStartingCredits(){
        return startingCredits;
    }

    /**
     * Sets up the crossroad for each crossroad tile this level.
     * needs to be called each time a new level is loaded
     * @return An arraylist of mouseliteners for crossroads
     */
    public ArrayList setUpCrossroad() {
        ArrayList<CrossroadSwitch> tiles=new ArrayList<>();
        for(int i=0;i < map.length; i++) {
           for (int j = 0; j < map[i].length; j++) {
               if (map[i][j] instanceof CrossroadTile) {
                   Tile[] tileMap=((CrossroadTile) map[i][j]).findNextWay();
                   map[i][j].setNeighbors(tileMap);
                   CrossroadSwitch cSwitch=new CrossroadSwitch((CrossroadTile) map[i][j]);
                   tiles.add(cSwitch);
                   try {
                       ((CrossroadTile) map[i][j]).changeWay();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }
       }
        return tiles;
    }

    /**
     * Sets up connectiontile to only have 1 viable neighbour
     */
    public void setUpConnection(){
        for(int i=0;i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] instanceof ConnectionTile) {
                    int z=0;
                    Tile[] temp=map[i][j].getNeighbors();
                    Tile[] newNeighbours=new Tile[temp.length];
                    for(int k=0; k < temp.length; k++){
                        if(temp[k] instanceof PathTile){
                            newNeighbours[z]=temp[k];
                            z++;
                        }
                    }
                    map[i][j].setNeighbors(newNeighbours);
                }
            }
        }
    }

    /**
     * Returns how many points needed for win this map
     * @return the score needed to win this map
     */
    public int getVictoryPoints(){
        return victoryPoints;
    }

    /**
     * Returns a matrix of this levels tiles
     * @return A matrix of tiles
     */
    public  Tile[][] getMap(){
        return map;
    }

    /**
     * Sets the startposition for this level
     * @param startPosition
     */
    public void setStartPosition(Position startPosition){
        this.startPosition=startPosition;
    }

    /**
     * Returns this levels startposition
     * @return startposition of this level
     */
    public Position getStartPosition(){
        return startPosition;
    }

    /**
     * Sets score needed to win this level
     * @param victoryPoints the score needed to win this level
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

}

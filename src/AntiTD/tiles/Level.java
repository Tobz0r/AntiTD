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

    private Position startPosition;
    private int startingCredits;
    private Position goalPosition;
    private static Tile[][] currentMap;
    private Tile[][] map;
    private String name;

    private int victoryPoints;

    public Level(String name){
        this.name=name;
    }
    public  void addMap(Tile[][] map){
        //First setUp crossroads
        this.map=map;
    }
    public void setStartingCredits(int startingCredits){
        this.startingCredits=startingCredits;
    }
    public int getStartingCredits(){
        return startingCredits;
    }
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
    public int getVictoryPoints(){
        return victoryPoints;
    }
    public  Tile[][] getMap(){
        return map;
    }
    public void setStartPosition(Position startPosition){
        this.startPosition=startPosition;
    }
    public Position getStartPosition(){
        return startPosition;
    }
    public void setGoalPosition(Position goalPosition){

        this.goalPosition=goalPosition;
    }
    public void setVictoryPoints(int victoryPoints) {

        this.victoryPoints = victoryPoints;
    }

}

package AntiTD.tiles;

import AntiTD.Position;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class Level {

    private Position startPosition;
    private Position goalPosition;
    private static Tile[][] currentMap;
    private Tile[][] map;
    private String name;



    private int victoryPoints;

    public Level(String name){
        this.name=name;
    }
    public  void addMap(Tile[][] map){
        this.map=map;
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
    public static void setCurrentMap(Tile[][] map){
        Level.currentMap=map;
    }
    public static Tile[][] getCurrentMap(){
        return currentMap;
    }
}

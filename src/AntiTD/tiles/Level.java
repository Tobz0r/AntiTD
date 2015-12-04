package AntiTD.tiles;

import AntiTD.Position;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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
        //First setUp crossroads
        this.map=map;
    }
    public void setUpCrossroad(){
        for(int i=0;i < map.length; i++) {
           for (int j = 0; j < map[i].length; j++) {
               if (map[i][j] instanceof CrossroadTile) {
                   Tile[] tileMap=((CrossroadTile) map[i][j]).findNextWay();
                   map[i][j].setNeighbors(tileMap);
               }
           }
       }


       /* Tile[] map2=map[i][j].getNeighbors2();
        Tile[] map3=new Tile[map2.length];
        Random r=new Random();
        map3[0]=map2[r.nextInt(map2.length)];
        map[i][j].setNeighbors(map3);*/
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
        return Level.currentMap;
    }
}

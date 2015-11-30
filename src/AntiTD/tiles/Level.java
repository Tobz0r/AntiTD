package AntiTD.tiles;

import AntiTD.Position;

import java.util.LinkedList;

/**
 * Created by dv13tes on 2015-11-30.
 */
public class Level {

    private Position startPosition;
    private Position goalPosition;
    private Tile[][] map;
    private String name;

    public Level(String name){
        this.name=name;

    }
    public void addMap(Tile[][] map){
        this.map=map;
    }
    public void setStartPosition(Position startPosition){
        this.startPosition=startPosition;
    }
    public void setGoalPosition(Position goal){
        this.goalPosition=goalPosition;
    }
}

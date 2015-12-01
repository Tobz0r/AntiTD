package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by mattias on 2015-11-27.
 */
public class Environment extends Observable implements Runnable {

    private ArrayList<Level> levels;
    private Handler handler;
    private GameBoard grid;
    private int level=0;

    public Environment(GUI gui){
        handler=new Handler();
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();
        Level level=levels.get(this.level);
        grid=new GameBoard(level);
    }
    public void incrementLevel(){
        level++;
        if(level>levels.size()){
            level=1;
        }
    }
    public GameBoard getGrid(){
        return grid;
    }

    @Override
    public void run() {


    }



}

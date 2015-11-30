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
    private int level;

    public Environment(GUI gui){
        handler=new Handler();
        addObserver(gui);
        ReadXML xmlReader = new ReadXML(new File("levels.xml"));
        levels=xmlReader.getLevels();

    }
    public void incrementLevel(){
        level++;
        if(level>levels.size()){
            level=1;
        }
    }

    @Override
    public void run() {
        Level level=levels.get(this.level);
        GameBoard grid=new GameBoard(level);
        update(grid);

    }
    private void update(GameBoard gameBoard){
        setChanged();
        notifyObservers(gameBoard);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}

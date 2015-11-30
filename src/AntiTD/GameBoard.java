package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.*;

import javax.swing.*;
import java.awt.*;


/**
 * Created by mattias on 2015-11-27.
 */
public class GameBoard extends JComponent {

    private Level level;
    private Tile[][] map;

    public GameBoard(Level level){
        setLayout(new GridLayout(1,1));
        this.level=level;
        map=level.getMap();
    }
    public void paintComponent(Graphics g){
        int x,y;
        x=-48;

        for(int i=0; i < map.length;i++){
            x+=48;
            y=0;
            for(int j=0; j < map[i].length;j++){
                switch(map[i][j].toString()) {
                    case BasicTile:
                        g.setColor(Color.black);
                        break;
                    case CrossroadTile:
                        g.setColor(Color.red);
                        break;
                    case GoalTile:
                        g.setColor(Color.yellow);
                        break;
                    case PathTile:
                        g.setColor(Color.orange);
                        break;
                    case TowerTile:
                        g.setColor(Color.green);
                        break;
                    case StartTile:
                        g.setColor(Color.pink);
                        break;
                    default:
                        System.out.println("eliashej");
                        break;
                }
                g.fillRect(x,y,48,48);
                y+=48;

            }

        }

    }

    public void resetGame(){

    }



}
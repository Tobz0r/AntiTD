package AntiTD;

import AntiTD.tiles.Level;
import AntiTD.tiles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.SampleModel;


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
                    case "AntiTD.tiles.BasicTile":
                        g.setColor(Color.black);
                        break;
                    case "AntiTD.tiles.CrossroadTile":
                        g.setColor(Color.red);
                        break;
                    case "AntiTD.tiles.GoalTile": 
                        g.setColor(Color.yellow);
                        break;
                    case "AntiTD.tiles.PathTile":
                        g.setColor(Color.orange);
                        break;
                    case "AntiTD.tiles.TowerTile":
                        g.setColor(Color.green);
                        break;
                    case "AntiTD.tiles.StartTile":
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
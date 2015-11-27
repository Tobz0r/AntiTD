package AntiTD;

import javax.swing.*;
import java.awt.*;


/**
 * Created by mattias on 2015-11-27.
 */
public class GameBoard extends JComponent {


    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(0,0,getWidth(),getHeight());
        g.fillRect(0,0,800,200);
        g.setColor(Color.blue);
        g.fillRect(0,200,800,200);
        g.setColor(Color.red);
        g.fillRect(0,400,800,200);
    }

}
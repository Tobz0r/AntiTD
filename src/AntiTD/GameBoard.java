package AntiTD;

import javax.swing.*;
import java.awt.*;


/**
 * Created by mattias on 2015-11-27.
 */
public class GameBoard extends JComponent {


    public void paintComponent(Graphics g){
        g.setColor(Color.green);
        g.drawRect(0,0,800,600);
        g.fillRect(0,0,800,600);
    }

}
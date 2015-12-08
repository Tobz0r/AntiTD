package AntiTD.tiles;

import java.awt.event.*;
import java.io.IOException;

/**
 * Created by Tobias on 2015-12-07.
 */
public class CrossroadSwitch extends MouseAdapter {

    private CrossroadTile tile;

    public CrossroadSwitch(CrossroadTile tile){
        this.tile=tile;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx=e.getX();
        int my=e.getY();
        if(mouseOver(mx,my,(int)(tile.getPosition().getX()*(tile.getSize().getWidth())),
                (int)(tile.getPosition().getY()*(tile.getSize().getHeight())),
                (int)tile.getSize().getWidth(),
                (int)tile.getSize().getHeight())){

            try {
                tile.changeWay();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    private boolean mouseOver(int mx,int my,int x, int y, int width, int height){
        return (((mx > x) && (mx < x + width))&&((my > y) && (my < y + height)));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}

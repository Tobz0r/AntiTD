package AntiTD.tiles;

import java.awt.event.*;

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

            tile.changeWay();
        }


    }
    private boolean mouseOver(int mx,int my,int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }
            else return false;
        }else return false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}

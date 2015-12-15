package AntiTD.tiles;

import java.awt.event.*;
import java.io.IOException;

/**
 * @author Tobias Estefors
 * Class used to check if the tile containing a crossroad has been clicked.
 * If that tile has been clicked it calles the changeway method from the crossroad
 */
public class CrossroadSwitch extends MouseAdapter {

    private CrossroadTile tile;

    public CrossroadSwitch(CrossroadTile tile){
        this.tile=tile;
    }

    /**
     * Changes the tiles path if clicked within the correct area
     * @param e the mouseclick
     */
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

    /**
     * Checks if the mouseclick is within the tiles x and y-value
     * @param mx the mouseclicks x-value
     * @param my the mouseclicks y-value
     * @param x  the tiles x-value
     * @param y  the tiles y-value
     * @param width the width of the tile
     * @param height the height of the tile
     * @return true if the mouseclick is within the area, else false
     */
    private boolean mouseOver(int mx,int my,int x, int y, int width, int height){
        return (((mx > x) && (mx < x + width))&&((my > y) && (my < y + height)));
    }

}

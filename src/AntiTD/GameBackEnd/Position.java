package AntiTD.GameBackEnd;

/**
 * Created by dv13trm on 2015-11-27.
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }
    public Position getPosToSouth(){
        Position p = new Position(x+1,y);
        return p;
    }

    public Position getPosToNorth(){
        Position p = new Position (x-1,y);
        return p;
    }

    public Position getPosToWest(){
        Position p = new Position (x,y-1);
        return p;
    }

    public Position getPosToEast(){
        Position p = new Position (x,y+1);
        return p;

    }

    public int getY() {
        return y;
    }
}

package AntiTD;

/**
 * @author Thom Renström, Tobias Estefors
 * Position class that is used for all game objects and tiles
 * Mostly used to check position for every unit and tile
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     * @return x value
     */
    public int getX() {
        return x;
    }
    /**
     * Check if position given in parameter is to south
     * @param p parameter to check
     * @return true if position to south, false if not
     */
    public boolean IsPosToSouth(Position p){
        if((this.getY()+1)==p.getY()){
            return this.getX()==p.getX();
        }
        return false;
    }
    /**
     * Check if position given in parameter is to north
     * @param p parameter to check
     * @return true if position to north, false if not
     */
    public boolean IsPosToNorth(Position p){
        if(this.getY()-1==p.getY()){
            return this.getX()==p.getX();
        }
        return false;
    }
    /**
     * Check if position given in parameter is to west
     * @param p parameter to check
     * @return true if position to west, false if not
     */
    public boolean IsPosToWest(Position p){
        if((this.getX()-1) == p.getX()){
            return this.getY()==getY();
        }
        return false;
    }
    /**
     * Check if position given in parameter is to east
     * @param p parameter to check
     * @return true if position to east, false if not
     */
    public boolean IsPosToEast(Position p){
        if((this.getX()+1) == p.getX()){
            return this.getY()==p.getY();
        }
        return false;
    }

    /**
     * @return y value
     */
    public int getY() {
        return y;
    }

    /**
     * @return a string representation with x and y values
     */
    public String toString(){
        return "("+getX()+","+getY()+")";
    }
}

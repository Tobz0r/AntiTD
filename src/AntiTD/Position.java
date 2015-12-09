package AntiTD;

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
    public boolean IsPosToSouth(Position p){
        if((this.getY()+1)==p.getY()){
            return this.getX()==p.getX();
        }
        return false;
    }

    public boolean IsPosToNorth(Position p){
        if(this.getY()-1==p.getY()){
            return this.getX()==p.getX();
        }
        return false;
    }

    public boolean IsPosToWest(Position p){
        if((this.getX()-1) == p.getX()){
            return this.getY()==getY();
        }
        return false;
    }

    public boolean IsPosToEast(Position p){
        if((this.getX()+1) == p.getX()){
            return this.getY()==p.getY();
        }
        return false;
    }

    public int getY() {
        return y;
    }

    public String toString(){
        return "("+getX()+","+getY()+")";
    }
}

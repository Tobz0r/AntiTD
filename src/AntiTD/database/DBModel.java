package AntiTD.database;

/**
 * Created by id12men on 2015-12-11.
 */
public class DBModel {
    private int id;
    private String playername;
    private int score;

    public DBModel(int id, String playername, int score) {
        this.id = id;
        this.playername = playername;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getPlayername() {
        return playername;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\t\tNAME: "+playername+"\t\tSCORE:"+score;
    }
}

package AntiTD.database;

/**
 * Created by id12men on 2015-12-11.
 */
public class DBModel {
    private int id;
    private String playername;
    private int score;

    /**
     * Constructor for object.
     * @param id id of object
     * @param playername name of the player
     * @param score highscore value
     */
    public DBModel(int id, String playername, int score) {
        this.id = id;
        this.playername = playername;
        this.score = score;
    }

    /**
     * Getter for id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for name
     * @return the name
     */
    public String getPlayername() {
        return playername;
    }

    /**
     * Getter for score
     * @return the score
     */
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\t\tNAME: "+playername+"\t\tSCORE:"+score;
    }
}

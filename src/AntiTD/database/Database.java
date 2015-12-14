package AntiTD.database; /**
 * Created by mattias on 2015-12-09.
 */

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static String dbURL = "jdbc:derby:highscoresDB;create=true;user=me;password=mine";
    private static String tableName = "highscores";
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement prepStmt = null;

    private final static String sqlGetScore =
            "SELECT * FROM "+tableName+" WHERE name = ?";

    private final static String sqlAddScore =
            "INSERT INTO " + tableName + "(name, score) VALUES (?,?)";

    private final static String sqlGetId =
            "SELECT id FROM "+tableName+" WHERE name=?";

    private final static String sqlUpdateScore =
            "UPDATE " + tableName + " SET score=? WHERE id=?";

    public Database() throws DatabaseConnectionIsBusyException {
        createConnection();
        createTable();
    }

    private synchronized void createTable() {
        try {
            stmt = conn.createStatement();
            //INT IDENTITY (1,1) NOT NULL,
            stmt.execute("create table highscores(" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(255) NOT NULL UNIQUE, " +
                    "score INTEGER NOT NULL)");
            stmt.close();
        } catch (SQLException e) {
            if( DatabaseHelper.tableAlreadyExists(e) ) {
                return; // That's OK
            }
            e.printStackTrace();
        }
        
    }


    private synchronized void createConnection() throws DatabaseConnectionIsBusyException{
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL);
        }
        catch (SQLException e) {
            if (DatabaseHelper.databaseConnectionCouldNotBeMade(e)) {
                throw new DatabaseConnectionIsBusyException();
            }
        }
        catch (Exception except) {
            except.printStackTrace();
        }
    }

    /**
     * Insert or update highscore for given name, if there is no post in database with
     * given playername a new post will be created.
     * @param playername name of the player
     * @param score score value
     */
    public synchronized void insertOrUpdateHighscore(String playername, int score) {
        try {
            //stmt = conn.createStatement();
            prepStmt = conn.prepareStatement(sqlAddScore);
            prepStmt.setString(1, playername);
            prepStmt.setInt(2, score);
            prepStmt.execute();
            prepStmt.close();
        } catch (SQLException sqlExcept) {
            if (DatabaseHelper.postAlreadyExists(sqlExcept)) {
                try {
                    prepStmt = conn.prepareStatement(sqlGetId);
                    prepStmt.setString(1, playername);
                    ResultSet results = prepStmt.executeQuery();

                    results.next();
                    int id = results.getInt(1);
                    results.close();
                    prepStmt.close();

                    prepStmt = conn.prepareStatement(sqlUpdateScore);
                    prepStmt.setInt(2, score);
                    prepStmt.setInt(1, id);
                    prepStmt.execute();
                    prepStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sqlExcept.printStackTrace();
            }
        }
    }

    /**
     * Gets highscore for specified playername.
     * @param playername name of the player
     * @return score if player exists exists else -1
     */
    public synchronized DBModel getHighscore(String playername) throws DatabaseEntryDoesNotExistsException {
        DBModel highscore = null;
        try {
            //stmt = conn.createStatement();
            prepStmt = conn.prepareStatement(sqlGetScore);
            prepStmt.setString(1,playername);
            ResultSet results = prepStmt.executeQuery();
            try {
                results.next();
                highscore = new DBModel(results.getInt(1), results.getString(2), results.getInt(3));
            } catch (SQLException sqlExcept) {
                throw new DatabaseEntryDoesNotExistsException();
            }
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        return highscore;
    }

    public synchronized ArrayList<DBModel> getHighscores() {
        ArrayList<DBModel> returnList = null;
        try {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT COUNT(*) FROM "+tableName);
            results.next();
            int size = results.getInt(1);
            results.close();
            stmt.close();

            returnList = new ArrayList<DBModel>(size);

            stmt = conn.createStatement();
            results = stmt.executeQuery("select * from " + tableName);
            while(results.next()) {
                returnList.add(new DBModel(results.getInt(1), results.getString(2), results.getInt(3)));
            }
            results.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * prints highscore
     */
    public synchronized void printHighscores(){
        ArrayList<DBModel> highscores = getHighscores();
        System.out.print("ID\t\t"+"NAME\t\t\t\t"+"SCORE\t\t\n");
        System.out.println("-------------------------------------------------");
        for (DBModel model : highscores) {
            System.out.println(model.getId() + "\t\t" + model.getPlayername() + "\t\t\t\t" + model.getScore());
        }
        System.out.println();
    }

    public synchronized void shutdown()
    {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException sqlExcept) {

        }

    }
}

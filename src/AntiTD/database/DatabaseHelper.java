package AntiTD.database;

import java.sql.SQLException;

/**
 * Created by mattias on 2015-12-09.
 */
public class DatabaseHelper {
    /**
     * Checks if exception is due of already existing table
     * @param e exception to check
     * @return true or false
     */
    public static synchronized boolean tableAlreadyExists(SQLException e) {
        if (e.getSQLState().equals("X0Y32")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if exception is due to post already exists
     * @param e exception to check
     * @return true or false
     */
    public static synchronized boolean postAlreadyExists(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if connection to database could not be made
     * @param e exception to check
     * @return true or false
     */
    public static synchronized boolean databaseConnectionCouldNotBeMade(SQLException e) {
        if (e.getSQLState().equals("XJ040")) {
            return true;
        }
        return false;
    }
}

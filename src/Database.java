/**
 * Created by mattias on 2015-12-09.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class Database {
    private static String dbURL = "jdbc:derby:highscoresDB;create=true;user=me;password=mine";
    private static String tableName = "highscores";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;

    public static void main(String[] args)
    {
        createConnection();
        createTable();
        insertHighscore("LaVals", 100);
        selectHighscore();
        shutdown();
    }

    private static void createTable() {

        try {
            stmt = conn.createStatement();
            //INT IDENTITY (1,1) NOT NULL,
            stmt.execute("create table highscores(" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(255) NOT NULL, " +
                    "score INTEGER NOT NULL)");
            stmt.close();
        } catch (SQLException e) {
            if( DatabaseHelper.tableAlreadyExists(e) ) {
                return; // That's OK
            }
            e.printStackTrace();
        }

    }

    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL);
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }

    private static void insertHighscore(String playername, int score)
    {
        try
        {
            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + "(name, score) values ('" + playername + "'," + score +")");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }

    private static void selectHighscore()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                int id = results.getInt(1);
                String playername = results.getString(2);
                int score = results.getInt(3);
                System.out.println(id + "\t\t" + playername + "\t\t" + score);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }

    private static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        }
        catch (SQLException sqlExcept)
        {

        }

    }
}

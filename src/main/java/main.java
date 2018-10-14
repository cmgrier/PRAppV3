package src.main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// ERROR ON INTIAL STARTUP.
// No Players table so error when trying to drop it.
// Should try to initiate the Jar with tables.

public class main {
    public static void main(String[] args) {
        populatePlayerDB();
    }

    public static void createDatabases(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

        } catch (ClassNotFoundException e){
            System.out.println("No Driver");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = getDBConnection();
        resetTables(connection);

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Players(" +
                    "playerID integer PRIMARY KEY, " +
                    "tag varchar(25)," +
                    "sponser varchar(10)," +
                    "score integer," +
                    "initialScore integer," +
                    "characters varchar(50))";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Players table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Table");
            e.printStackTrace();
        }
    }

    // gets the connection for the derby DB
    public static Connection getDBConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:PRApp;create=true");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        System.out.println("Driver Connected!");
        return connection;
    }

    // drops all tables in the derby DB
    private static void resetTables(Connection connection){
        try{
            Statement stmt = connection.createStatement();
            String str = "drop table Players";
            stmt.execute(str);
            stmt.close();

        }catch (SQLException e){
            System.out.println("Couldn't Drop Tables");
            e.printStackTrace();
            return;
        }
        System.out.println("reset tables");
    }

    private static void populatePlayerDB(){
        createDatabases();
        Connection connection = getDBConnection();
        //read from CSV here
        try {
            FileReader fr = new FileReader("Data/Players");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] player  = line.split(",");
                String str = "insert into Players values(" + player[0] + ", '" + player[1] + "', '" + player[2] + "', " + player[3] + ", " + player[4] + ", '" + player[5] + "')";
                System.out.println(line);
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(str);
                stmt.close();
            }
            System.out.println("Inserted all Players");
        } catch (IOException ioe){
            System.out.println("Could not read Players CSV");
        } catch (SQLException sqle){
            System.out.println("Could not insert into Players table");
        }
    }


}

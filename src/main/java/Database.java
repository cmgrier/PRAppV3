package src.main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    int highestPlayerID;

    public void createDatabases(){
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
                    "characters varchar(50)," +
                    "tournamentsEntered integer)";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Players table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Table");
            e.printStackTrace();
        }

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Matches(" +
                    "matchID integer PRIMARY KEY, " +
                    "player1ID integer," +
                    "player2ID integer," +
                    "player1Count integer," +
                    "player2Count integer," +
                    "Constraint player1ID_FK Foreign Key(player1ID) references Players(playerID)," +
                    "Constraint player2ID_FK Foreign Key(player2ID) references Players(playerID)";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Players table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Table");
            e.printStackTrace();
        }
    }

    // gets the connection for the derby DB
    public Connection getDBConnection(){
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
    private void resetTables(Connection connection){
        try{
            Statement stmt = connection.createStatement();
            String str = "drop table Matches";
            stmt.execute(str);
            str = "drop table Players";
            stmt.execute(str);
            stmt.close();

        }catch (SQLException e){
            System.out.println("Couldn't Drop Tables");
            e.printStackTrace();
            return;
        }
        System.out.println("reset tables");
    }

    private void populateDatabases(){
        createDatabases();
        Connection connection = getDBConnection();
        try {
            FileReader frp = new FileReader("Data/Players");
            BufferedReader brp = new BufferedReader(frp);
            String line;
            while((line = brp.readLine()) != null){
                String[] player  = line.split(",");
                String str = "insert into Players values(" + player[0] + ", '" + player[1] + "', '" + player[2] + "', " + player[3] + ", " + player[4] + ", '" + player[5] + "', " + player[6] + ")";
                System.out.println(line);
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(str);
                stmt.close();
            }
            System.out.println("Inserted all Players");

            FileReader frs = new FileReader("Data/Matches");
            BufferedReader brs = new BufferedReader(frs);
            while((line = brs.readLine()) != null){
                String[] season = line.split(",");

            }
        } catch (IOException ioe){
            System.out.println("Could not read Players CSV");
        } catch (SQLException sqle){
            System.out.println("Could not insert into Players table");
        }
    }
}

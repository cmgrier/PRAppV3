import java.io.*;
import java.sql.*;

public class Database {
    private int highestPlayerID;
    private int initialScore = 1000;

    public Database(){
        highestPlayerID = getHighestPlayerID();
    }

    //Creates Databases
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
            String str = "create table Seasons(" +
                    "seasonID integer Primary Key, " +
                    "name varchar(25)," +
                    "game varchar(50))";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Seasons table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Seasons Table");
            e.printStackTrace();
        }

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Players(" +
                    "playerID integer PRIMARY KEY, " +
                    "tag varchar(25)," +
                    "sponsor varchar(10)," +
                    "score integer," +
                    "initialScore integer," +
                    "characters varchar(50)," +
                    "tournamentsEntered integer," +
                    "seasonID integer," +
                    "Constraint Players_seasonID_FK Foreign Key(seasonID) references Seasons(seasonID))";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Players table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Players Table");
            e.printStackTrace();
        }

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Tournaments(" +
                    "tournamentID integer PRIMARY KEY, " +
                    "name varchar(25)," +
                    "seasonID integer," +
                    "Constraint seasonID_FK Foreign Key (seasonID) references Seasons (seasonID)," +
                    "Constraint name_UQ Unique (name))";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Tournaments table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Tournaments Table");
            e.printStackTrace();
        }

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Matches(" +
                    "matchID integer PRIMARY KEY, " +
                    "tournamentID integer," +
                    "player1ID integer," +
                    "player2ID integer," +
                    "player1Count integer," +
                    "player2Count integer," +
                    "Constraint player1ID_FK Foreign Key(player1ID) references Players(playerID)," +
                    "Constraint Matches_tournamentID_FK Foreign Key(tournamentID) references Tournaments(tournamentID)," +
                    "Constraint player2ID_FK Foreign Key(player2ID) references Players(playerID))";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Matches table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Matches Table");
            e.printStackTrace();
        }

        try{
            Statement stmt = connection.createStatement();
            String str = "create table Placings(" +
                    "tournamentID integer, " +
                    "playerID integer," +
                    "placement integer," +
                    "Constraint Placings_PK Primary Key (tournamentID, playerID)," +
                    "Constraint tournamentID_FK Foreign Key (tournamentID) references Tournaments (tournamentID)," +
                    "Constraint playerID_FK Foreign Key (playerID) references Players (playerID))";
            stmt.execute(str);
            stmt.close();
            System.out.println("Created Placings table");
        }catch (SQLException e){
            System.out.println("Couldn't Create Placings Table");
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

    // checks if player exists in the Players table
    public boolean doesPlayerExist(String tag, int seasonID){
        String query = "select playerID from Players where tag = '" + tag + "' and seasonID = " + seasonID;
        Boolean returnValue = false;
        try{
            Connection connection = getDBConnection();
            System.out.println(query);
            Statement stmt = connection.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            int id = -1;
            if(rst.next()){
                id = rst.getInt("playerID");
                returnValue = true;
            }
            System.out.println("Player's ID = " + id);
            rst.close();
        } catch (SQLException sqle){
            System.out.println("Couldn't check if player " + tag + " exists in the Players table");
            sqle.printStackTrace();
        }

        return returnValue;
    }

    // drops all tables in the derby DB
    public void resetTables(Connection connection){
        String str;
        try{
            Statement stmt = connection.createStatement();
            str = "drop table Placings";
            stmt.execute(str);
            System.out.println("Dropped Placings");
            str = "drop table Matches";
            stmt.execute(str);
            System.out.println("Dropped Matches");
            str = "drop table Tournaments";
            stmt.execute(str);
            System.out.println("Dropped Tournaments");
            str = "drop table Players";
            stmt.execute(str);
            System.out.println("Dropped Players");
            str = "drop table Seasons";
            stmt.execute(str);
            System.out.println("Dropped Seasons");
            stmt.close();

        }catch (SQLException e){
            System.out.println("Couldn't Drop Tables");
            e.printStackTrace();
            return;
        }
        System.out.println("tables reset");
    }

    //get highest playerID
    public int getHighestPlayerID(){
        Connection connection = getDBConnection();
        int maxPlayerID = 0;
        try{
            Statement stmt = connection.createStatement();
            String str = "select Max(playerID) as maxID from Players";
            ResultSet rst = stmt.executeQuery(str);
            if(rst.next()){
                maxPlayerID = rst.getInt("maxID");
            }
            stmt.close();
            System.out.println("Highest Player ID: " + maxPlayerID);
        }catch (SQLException e){
            System.out.println("Couldn't get Highest Player ID");
            e.printStackTrace();
        }
        return maxPlayerID;
    }

    //get highest SeasonID
    public int getHighestSeasonID(){
        Connection connection = getDBConnection();
        int maxSeasonID = 0;
        try{
            Statement stmt = connection.createStatement();
            String str = "select Max(seasonID) as maxID from Seasons";
            ResultSet rst = stmt.executeQuery(str);
            if(rst.next()){
                maxSeasonID = rst.getInt("maxID");
            }
            stmt.close();
            System.out.println("Highest Season ID: " + maxSeasonID);
        }catch (SQLException e){
            System.out.println("Couldn't get Highest Season ID");
            e.printStackTrace();
        }
        return maxSeasonID;
    }

    //get highest TournamentID
    public int getHighestTournamentID(){
        Connection connection = getDBConnection();
        int maxTournamentID = 0;
        try{
            Statement stmt = connection.createStatement();
            String str = "select Max(tournamentID) as maxID from Tournaments";
            ResultSet rst = stmt.executeQuery(str);
            if(rst.next()){
                maxTournamentID = rst.getInt("maxID");
            }
            stmt.close();
            System.out.println("Highest Tournament ID: " + maxTournamentID);
        }catch (SQLException e){
            System.out.println("Couldn't get Highest Tournament ID");
            e.printStackTrace();
        }
        return maxTournamentID;
    }

    //get highest MatchID
    public int getHighestMatchID(){
        Connection connection = getDBConnection();
        int maxMatchID = 0;
        try{
            Statement stmt = connection.createStatement();
            String str = "select Max(matchID) as maxID from Matches";
            ResultSet rst = stmt.executeQuery(str);
            if(rst.next()){
                maxMatchID = rst.getInt("maxID");
            }
            stmt.close();
            System.out.println("Highest Match ID: " + maxMatchID);
        }catch (SQLException e){
            System.out.println("Couldn't get Highest Match ID");
            e.printStackTrace();
        }
        return maxMatchID;
    }

    //populates database with CSVs
    public void populateDatabases(){
        createDatabases();
        Connection connection = getDBConnection();

        try {
            Statement stmt = connection.createStatement();
            String line;
            // fill Seasons table
            FileReader frs = new FileReader("Data/Seasons");
            BufferedReader brs = new BufferedReader(frs);
            while ((line = brs.readLine()) != null) {
                String[] season = line.split(",");
                String str = "insert into Seasons values(" + season[0] + ", '" + season[1] + "', '" + season[2] + "')";
                System.out.println(line);
                System.out.println(str);
                stmt.executeUpdate(str);
            }
            System.out.println("Inserted all Seasons");
        } catch (SQLException sqle){
            System.out.println("Couldn't insert Seasons");
        } catch (IOException ioe){
            System.out.println("Couldn't read from Seasons CSV");
        }

        try {
            Statement stmt = connection.createStatement();
            String line;

            // fill players table
            FileReader frp = new FileReader("Data/Players");
            BufferedReader brp = new BufferedReader(frp);
            highestPlayerID = 0;
            while ((line = brp.readLine()) != null) {
                String[] player = line.split(",");
                System.out.println(player.length);
                String str = "insert into Players values(" + player[0] + ", '" + player[1] + "', '" + player[2] + "', " + player[3] + ", " + player[4] + ", '" + player[5] + "', " + player[6] + ", " + player[7] + ")";
                System.out.println(line);
                System.out.println(str);
                stmt.executeUpdate(str);
                if (Integer.parseInt(player[0]) > highestPlayerID) {
                    highestPlayerID = Integer.parseInt(player[0]);
                }
            }
            System.out.println("Inserted all Players");
        } catch (SQLException sqle){
            System.out.println("Couldn't insert Players");
        } catch (IOException ioe){
            System.out.println("Couldn't read from Players CSV");
        }

        try {
            Statement stmt = connection.createStatement();
            String line;
            // fill Tournaments table
            FileReader frt = new FileReader("Data/Tournaments");
            BufferedReader brt = new BufferedReader(frt);
            while ((line = brt.readLine()) != null) {
                String[] tournament = line.split(",");
                String str = "insert into Tournaments values(" + tournament[0] + ", '" + tournament[1] + "', " + tournament[2] + ")";
                System.out.println(line);
                stmt.executeUpdate(str);
            }
            System.out.println("Inserted all Tournaments");
        } catch (SQLException sqle){
            System.out.println("Couldn't insert Tournaments");
        } catch (IOException ioe){
            System.out.println("Couldn't read from Tournaments CSV");
        }

        try {
            Statement stmt = connection.createStatement();
            String line;
            // fill matches table
            FileReader frm = new FileReader("Data/Matches");
            BufferedReader brm = new BufferedReader(frm);
            while ((line = brm.readLine()) != null) {
                String[] match = line.split(",");
                String str = "insert into Matches values(" + match[0] + ", " + match[1] + ", " + match[2] + ", " + match[3] + ", " + match[4] + ", " + match[5] + ")";
                System.out.println(line);
                stmt.executeUpdate(str);
            }
            System.out.println("Inserted all Matches");
        } catch (SQLException sqle){
            System.out.println("Couldn't insert Matches");
        } catch (IOException ioe){
            System.out.println("Couldn't read from Matches CSV");
        }

        try {
            Statement stmt = connection.createStatement();
            String line;
            // fill Placings table
            FileReader frpl = new FileReader("Data/Placings");
            BufferedReader brpl = new BufferedReader(frpl);
            while((line = brpl.readLine()) != null){
                String[] placing = line.split(",");
                String str = "insert into Placings values(" + placing[0] + ", " + placing[1] + ", " + placing[2] + ")";
                System.out.println(line);
                stmt.executeUpdate(str);
            }
            System.out.println("Inserted all Placings");
        } catch (IOException ioe){
            System.out.println("Could not read CSVs");
        } catch (SQLException sqle){
            System.out.println("Could not insert values into tables");
        }
    }

    //save Database into CSV files
    public void publish(){
        //save the players table into the players csv file
        Connection connection = getDBConnection();
        try{
            Statement stmt = connection.createStatement();
            FileWriter fwp = new FileWriter("Data/Players");
            BufferedWriter bwp = new BufferedWriter(fwp);
            ResultSet players = stmt.executeQuery("Select * from Players");
            while(players.next()){
                String line = players.getInt("playerID") + "," +
                        players.getString("tag") + "," +
                        players.getString("sponsor") + "," +
                        players.getInt("score") + "," +
                        players.getInt("initialScore") + "," +
                        players.getString("characters") + "," +
                        players.getInt("tournamentsEntered") + "," +
                        players.getInt("seasonID");
                bwp.write(line);
                System.out.println("wrote: " + line);
                bwp.newLine();
            }
            bwp.close();
            fwp.close();
            System.out.println("wrote to Players file");
        } catch (IOException ioe){
            System.out.print("Couldn't write to Players file");
        } catch (SQLException sqle){
            System.out.print("Couldn't Query Players Table");
        }

        //save the seasons table into the seasons CSV file
        try{
            Statement stmt = connection.createStatement();
            FileWriter fws = new FileWriter("Data/Seasons");
            BufferedWriter bws = new BufferedWriter(fws);
            ResultSet seasons = stmt.executeQuery("Select * from Seasons");
            while(seasons.next()){
                String line = seasons.getInt("seasonID") + "," +
                        seasons.getString("name") + "," +
                        seasons.getString("game");
                bws.write(line);
                bws.newLine();
                System.out.println("wrote: " + line);
            }
            bws.close();
            fws.close();
            System.out.println("wrote to Seasons file");
        } catch (IOException ioe){
            System.out.print("Couldn't write to Seasons file");
        } catch (SQLException sqle){
            System.out.print("Couldn't Query Seasons Table");
        }

        //save the Matches table into the matches CSV file
        try{
            Statement stmt = connection.createStatement();
            FileWriter fwm = new FileWriter("Data/Matches");
            BufferedWriter bwm = new BufferedWriter(fwm);
            ResultSet matches = stmt.executeQuery("Select * from Matches");
            while(matches.next()){
                String line = matches.getInt("matchID") + "," +
                        matches.getInt("tournamentID") + "," +
                        matches.getInt("player1ID") + "," +
                        matches.getInt("player2ID") + "," +
                        matches.getInt("player1Count") + "," +
                        matches.getInt("player2Count");
                bwm.write(line);
                bwm.newLine();
                System.out.println("wrote: " + line);
            }
            bwm.close();
            fwm.close();
            System.out.println("wrote to Matches file");
        } catch (IOException ioe){
            System.out.print("Couldn't write to Matches file");
        } catch (SQLException sqle){
            System.out.print("Couldn't Query Matches Table");
        }

        //save the Tournaments table into the tournaments CSV file
        try{
            Statement stmt = connection.createStatement();
            FileWriter fwt = new FileWriter("Data/Tournaments");
            BufferedWriter bwt = new BufferedWriter(fwt);
            ResultSet tournaments = stmt.executeQuery("Select * from Tournaments");
            while(tournaments.next()){
                String line = tournaments.getInt("tournamentID") + "," +
                        tournaments.getString("name") + "," +
                        tournaments.getInt("seasonID");
                bwt.write(line);
                bwt.newLine();
                System.out.println("wrote: " + line);
            }
            bwt.close();
            fwt.close();
            System.out.println("wrote to Tournaments file");
        } catch (IOException ioe){
            System.out.print("Couldn't write to Tournaments file");
        } catch (SQLException sqle){
            System.out.print("Couldn't Query Tournaments Table");
        }

        //save the Placings table into the Placings CSV file
        try{
            Statement stmt = connection.createStatement();
            FileWriter fwp = new FileWriter("Data/Placings");
            BufferedWriter bwp = new BufferedWriter(fwp);
            ResultSet placings = stmt.executeQuery("Select * from Placings");
            while(placings.next()){
                String line = placings.getInt("tournamentID") + "," +
                        placings.getInt("playerID") + "," +
                        placings.getInt("placement");
                bwp.write(line);
                bwp.newLine();
                System.out.println("wrote: " + line);
            }
            bwp.close();
            fwp.close();
            System.out.println("wrote to Placings file");
        } catch (IOException ioe){
            System.out.print("Couldn't write to Placings file");
        } catch (SQLException sqle){
            System.out.print("Couldn't Query Placings Table");
        }
    }

    public void addNewPlayer(String tag, int seasonID){
        int ID = getHighestPlayerID() + 1;
        String str = "insert into Players values(" + ID + ", '" + tag + "', null, " + initialScore + ", " + initialScore + ", null, 0, " + seasonID + ")";
        try{
            Connection connection = getDBConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(str);
            System.out.println("Added Player :" + tag + " to season " + seasonID);
        } catch (SQLException sqle){
            System.out.println("Couldn't Add Player: " + tag + " to season " + seasonID);
        }
        highestPlayerID = ID;
    }

    public void addNewSeason(String name, String game){
        int ID = getHighestSeasonID() + 1;
        String str = "insert into Seasons values(" + ID + ", '" + name + "', '" + game + "')";
        try{
            Connection connection = getDBConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(str);
            System.out.println("Added Season: " + name + " to game " + game);
        } catch (SQLException sqle){
            System.out.println("Couldn't Add Season: " + name + " to game " + game);
        }
    }
}

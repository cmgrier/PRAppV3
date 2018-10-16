import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class TournamentReader {
    JSONObject tournament;
    String tournamentName;

    public TournamentReader(String tournamentJSON){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(tournamentJSON);
            tournament = (JSONObject) object.get("tournament");
            tournamentName = (String) tournament.get("name");

        } catch (ParseException pe){
            System.out.println("Couldn't parse tournamentJSON");
        }
    }

    // adds a tournament to the given season and adds
    // players if they are new. Matches and placings are
    // added for this new tournament
    public void addTournament(int seasonID){
        Database DB = new Database();
        int tournamentID = DB.getHighestTournamentID() + 1;
        try {
            Connection connection = DB.getDBConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("insert into Tournaments values(" +
                    tournamentID + ", '" +
                    tournamentName + "', " +
                    seasonID + ")");
            stmt.close();
        } catch (SQLException sqle){
            System.out.println("Couldn't insert into Tournaments");
            sqle.printStackTrace();
        }
        addMatches(seasonID,tournamentID);
        addPlacings(tournamentID, seasonID);
    }

    // returns a hashmap of the players in the JSON file
    // with the JSON IDs mapped to the given names
    private HashMap<Integer, String> readPlayers(){
        HashMap<Integer, String> returnlist = new HashMap<>();
        JSONArray players = (JSONArray) tournament.get("participants");
        //System.out.println(players);
        for(int i = 0; i < players.size(); i++){
            JSONObject obj = (JSONObject) players.get(i);
            JSONObject player = (JSONObject) obj.get("participant");
            String PlayerName = player.get("name").toString();
            Integer PlayerID = Integer.parseInt(player.get("id").toString());
            returnlist.put(PlayerID,PlayerName);
        }
        return returnlist;
    }

    // adds the placings of every participant to the Placings table
    // as well as update each entrants tournaments entered
    private void addPlacings(int tournamentID, int seasonID){
        Database DB = new Database();
        JSONArray participants = (JSONArray) tournament.get("participants");
        for (Object participant: participants){
            JSONObject obj = (JSONObject) participant;
            JSONObject player = (JSONObject) obj.get("participant");
            Long placement = (Long) player.get("final_rank");
            int playerPlacement = placement.intValue();
            String playerTag = (String) player.get("display_name");
            try {
                String update = "update Players set tournamentsEntered = tournamentsEntered + 1 where tag = '" + playerTag + "' and seasonID = " + seasonID;
                Connection connection = DB.getDBConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(update);
                stmt.close();
                System.out.println("updated tournaments entered for: " + playerTag);
            } catch (SQLException sqle){
                System.out.println("Couldn't update tournaments entered for player " + playerTag);
            }

            int playerID = -1;
            try {
                Connection connection = DB.getDBConnection();
                Statement stmt = connection.createStatement();
                ResultSet rst = stmt.executeQuery("select playerID from Players where tag = '" + playerTag + "' and seasonID = " + seasonID);
                rst.next();
                playerID = rst.getInt("playerID");
                System.out.println("got player ID " + playerID);

                stmt.close();
            } catch (SQLException sqle){
                System.out.println("Couldn't get playerID from table");
            }

            try {
                Connection connection = DB.getDBConnection();
                String insert = "insert into Placings values(" + tournamentID + ", " + playerID + ", " + playerPlacement + ")";
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(insert);
                System.out.println("updated Placings");
                stmt.close();
            } catch (SQLException sqle){
                System.out.println("Couldn't update Placings");
                sqle.printStackTrace();
            }
        }
    }

    // adds all matches to the Matches table
    private void addMatches(int seasonID, int tournamentID){
        JSONArray matches = (JSONArray) tournament.get("matches");
        HashMap<Integer, String> players = readPlayers();
        for(int i = 0; i < matches.size(); i++){
            JSONObject obj = (JSONObject) matches.get(i);
            JSONObject match = (JSONObject) obj.get("match");
            Integer Player1ID = Integer.parseInt(match.get("player1_id").toString());
            Integer Player2ID = Integer.parseInt(match.get("player2_id").toString());
            String matchScore = (String) match.get("scores_csv");
            String[] score = matchScore.split("-");
            int player1wins = 0;
            int player2wins = 0;
            if(score.length == 2){
                player1wins = Integer.parseInt(score[0]);
                player2wins = Integer.parseInt(score[1]);
            } else{
                if(matchScore.equals("-1-0")){
                    player1wins = -1;
                    player2wins = 0;
                } else {
                    player1wins = 0;
                    player2wins = -1;
                }
            }

            String Player1 = players.get(Player1ID);
            Database DB = new Database();
            int player1ID = -1;
            try {
                if(!DB.doesPlayerExist(Player1, seasonID)){
                    DB.addNewPlayer(Player1,seasonID);
                }
                Connection connection = DB.getDBConnection();
                Statement stmt = connection.createStatement();
                ResultSet rst = stmt.executeQuery("select playerID from Players where seasonID = " + seasonID + " AND tag = '" + Player1 + "'");
                if(rst.next()){
                    player1ID = rst.getInt("playerID");
                }
                stmt.close();
            } catch (SQLException sqle){
                System.out.println("Couldn't query Players Table for player 1: " + Player1);
                sqle.printStackTrace();
            }

            String Player2 = players.get(Player2ID);
            int player2ID = -2;
            try {
                if(!DB.doesPlayerExist(Player2, seasonID)){
                    DB.addNewPlayer(Player2,seasonID);
                }
                Connection connection = DB.getDBConnection();
                Statement stmt = connection.createStatement();
                ResultSet rst = stmt.executeQuery("select playerID from Players where seasonID = " + seasonID + " AND tag = '" + Player2 + "'");
                if(rst.next()){
                    player2ID = rst.getInt("playerID");
                }
                stmt.close();
            } catch (SQLException sqle){
                System.out.println("Couldn't query Players Table for player 2: " + Player2);
            }

            try {
                int MatchID = DB.getHighestMatchID() + 1;
                String newMatch = "insert into Matches values(" +
                    MatchID + ", " +
                    tournamentID + ", " +
                    player1ID + ", " +
                    player2ID + ", " +
                    player1wins + ", " +
                    player2wins + ")";

                Connection connection = DB.getDBConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(newMatch);
                stmt.close();
            } catch (SQLException sqle){
                System.out.println("Couldn't add match to Matches table");
            }

        }
    }

}

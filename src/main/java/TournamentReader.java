import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void addTournament(int seasonID){
        Database DB = new Database();
        int tournamentID = DB.getHighestTournamentID() + 1;
        DB.executeUpdate("insert into Tournaments values(" +
                tournamentID + ", '" +
                tournamentName + "', " +
                seasonID + ")");

        JSONArray matches = (JSONArray) tournament.get("matches");
    }

    public HashMap<Integer, String> readPlayers(){
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

    public void readMatches(int seasonID){
        ArrayList<String> returnList = new ArrayList<>();
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
            try {
                ResultSet rst1 = DB.executeQuery("select * from Players where seasonID = " + seasonID + " and tag = " + Player1);
                if(rst1.next()){
                    int player1ID = rst1.getInt("playerID");
                }
            } catch (SQLException sqle){
                System.out.print("Couldn't query Players Table");
            }

            String Player2 = players.get(Player2ID);



        }
    }


}

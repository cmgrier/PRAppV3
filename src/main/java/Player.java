import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Player {
    int playerID;
    String tag;
    String sponsor;
    int score;
    int initialScore;
    String characters;
    int tournamentsEntered;
    int seasonID;

    double K = 26;
    double SoSK = 10;

    public Player(int playerID, String tag, String sponsor, int score, int initialScore, String characters, int tournamentsEntered, int seasonID){
        this.playerID = playerID;
        this.tag = tag;
        this.sponsor = sponsor;
        this.score = score;
        this.initialScore = initialScore;
        this.characters = characters;
        this.tournamentsEntered = tournamentsEntered;
        this.seasonID = seasonID;
    }

    public Player(ResultSet rs){
        try {
            if(rs.next()){
                this.playerID = rs.getInt("playerID");
                this.tag = rs.getString("tag");
                this.sponsor = rs.getString("sponsor");
                this.score = rs.getInt("score");
                this.initialScore = rs.getInt("initialScore");
                this.characters = rs.getString("characters");
                this.tournamentsEntered = rs.getInt("tournamentsEntered");
                this.seasonID = rs.getInt("seasonID");
            }
            System.out.println("created player obj with score: " + score);
            rs.close();
        } catch (SQLException sqle){
            System.out.println("Couldn't create player from ResultSet");
            sqle.printStackTrace();
        }
    }

    public Player(int ID, int score){
        this.playerID = ID;
        this.tag = "";
        this.sponsor = "";
        this.score = score;
        this.initialScore = 0;
        this.characters = "";
        this.tournamentsEntered = 0;
        this.seasonID = 0;
    }

    public ArrayList<String> getCharacters(){
        ArrayList<String> returnList = new ArrayList<>();
        if(this.characters.equals(null)){
            return returnList;
        } else {
            String[] characters = this.characters.split("&");
            for (String character:characters) {
                returnList.add(character);
            }
            return returnList;
        }
    }

    public void calculateScore(String Method, Player opponent, Match match){
        System.out.println("Calculating new scores with method " + Method);
        if(Method.equals("Test")){
            this.score = score + 1;
            opponent.score = opponent.score + 1;
        }
        if(Method.contains("Elo")){
            double R1 = Math.pow(10, (double) this.score / 400.0);
            double R2 = Math.pow(10, (double) opponent.score / 400.0);
            double E1 = R1 / (R1 + R2);
            double E2 = R2 / (R1 + R2);
            double S1, S2;
            if (match.player1Count > match.player2Count) {
                S1 = 1;
                S2 = 0;
            } else {
                S1 = 0;
                S2 = 1;
            }
            double N1 = (double) this.score + K * (S1 - E1);
            double N2 = (double) opponent.score + K * (S2 - E2);
            score = (int) N1;
            opponent.score = (int) N2;
        }
        if(Method.contains("Placing Bonus")){

        }
        if(Method.contains("Strength of Schedule")){
            Database DB = new Database();
            int tournamentID = match.tournamentID;
            int player1Placement = DB.getPlacement(this.playerID, tournamentID);
            int player2Placement = DB.getPlacement(opponent.playerID, tournamentID);
            double ratio = (double) player1Placement / (double) player2Placement;
            double scoreBonus = SoSK * ratio;
            if(match.player1Count > match.player2Count){
                this.score += (int) scoreBonus;
                opponent.score -= (int) scoreBonus;
            } else {
                this.score -= (int) scoreBonus;
                opponent.score += (int) scoreBonus;
            }
        }
    }
}

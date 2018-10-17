public class Match {
    int matchID;
    int tournamentID;
    int player1ID;
    int player2ID;
    int player1Count;
    int player2Count;

    public Match(int matchID, int tournamentID, int player1ID, int player2ID, int player1Count, int player2Count){
        this.matchID = matchID;
        this.tournamentID = tournamentID;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.player1Count = player1Count;
        this.player2Count = player2Count;
    }
}

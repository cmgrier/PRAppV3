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
        Database DB = new Database();
        DB.populateDatabases();
        DB.getHighestPlayerID();
        DB.getHighestSeasonID();
        DB.getHighestMatchID();
        DB.getHighestTournamentID();
    }

}

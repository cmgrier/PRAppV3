import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class TutorialController implements Initializable {

    @FXML
    TextArea Text;

    @FXML
    Label Title, PageNumber, SectionTitle;

    @FXML
    ImageView TopRightImage, BottomRightImage, CenterImage;



    Image ChallongeLogo = new Image("Images/challonge-logo.png");
    Image APISnapshot = new Image("Images/ChallongeAPI.jpg");
    Image SeasonSnapshot = new Image("Images/SeasonSnapshot.PNG");
    Image TournamentSnapshot = new Image("Images/TournamentSnapshot.PNG");
    Image PlayerSnapshot = new Image("Images/PlayerSnapshot.PNG");

    int MaxPageNumber = 5;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        Font SFQuartziteObliqueTitle = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 50);
        Font SFQuartziteObliqueSectionTitle = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 30);
        Title.setFont(SFQuartziteObliqueTitle);
        SectionTitle.setFont(SFQuartziteObliqueSectionTitle);
        PageNumber.setText("1");
        Tutorial();
    }

    public void Tutorial(){
        BottomRightImage.setImage(null);
        TopRightImage.setImage(null);
        CenterImage.setImage(null);
        if(PageNumber.getText().equals("1")){
            BottomRightImage.setImage(ChallongeLogo);
            TopRightImage.setImage(APISnapshot);
            SectionTitle.setText("Welcome");
            Text.setText("     Welcome to the PRApp Tutorial.  This tutorial will show you how to use all of the features within this application so you can get the most use out of it.  \n" +
                    "     After this tutorial you should be able to add tournaments and players to seasons as well as create seasons.  \n" +
                    "     This app will require that you be able to access the Challonge API.  To do this, you need to at least have a free challonge account and know your API key.  Follow the link below to learn more about the Challonge API.\n" +
                    "\n" +
                    "     https://api.challonge.com/v1\n" +
                    "\n" +
                    "The next page will teach how to start using the application and create a Season and add a Tournament.");

        } else if(PageNumber.getText().equals("2")){
            CenterImage.setImage(SeasonSnapshot);
            SectionTitle.setText("Create a Season");
            Text.setText("     The first thing to do is to create a Season.  Go to the Season tab on the left hand side.  At the bottom enter a new season title and then select the create season button.\n" +
                    "     Now in the change season drop box the season you just created will appear in the list.  To change to that season, select it in the drop box and select the change season button.  Now on the top right the current season will be the season you just changed to.\n" +
                    "     The next step is to add a tournament to your season.");

        } else if(PageNumber.getText().equals("3")){
            CenterImage.setImage(TournamentSnapshot);
            SectionTitle.setText("Add a Tournament");
            Text.setText("     To add a tournament, enter the following URL into your web browser with the tournament key replaced.  The tournament key for the tournament is the text after Challonge.com/:\n" +
                    "\n" +
                    "https://api.challonge.com/v1/tournaments/{tournamentKey}.json?include_participants=1&include_matches=1\n" +
                    "\n" +
                    "     Once you log in with your username and API key, text will appear on the screen.  Copy and paste all of the text into the \"Tournament JSON\" textfield in the Tournament Tab.  To add the tournament to the current season, select the \"Add Tournament\" button.  \n" +
                    "     You will see new players appear in the table in the center of the application.  They will not have characters and may be in an odd order.  To fix that we must alter the players to match their skill level and characters.");
        } else if(PageNumber.getText().equals("4")){
            CenterImage.setImage(PlayerSnapshot);
            SectionTitle.setText("Edit Players");
            Text.setText("     Once tournaments have been added to a season, players will appear in the PR table on the right.  To alter any player in the season, navigate to the Player tab. \n" +
                    "     Select any player from the \"Select Player\" drop box and the players info will be filled in the textfields below.   The initial score is the power score the player has before any matches are calculated.  The score number is the score after the matches have been calculated for all tournaments in the season.  \n" +
                    "     Once you are done editing a players information, select the Alter Player button to save your changes.  To create a new Player, fill in the \"New Player Tag\" textfield and any other fields above then select the \"Create New Player\" Button.  After editing all the players you would like to edit, navigate to the Season tab and select the \"Recalculate Season\" button.  This will recalculate all the matches with the updated initial scores.  This will also update the table.");
        } else if (PageNumber.getText().equals("5")){
            CenterImage.setImage(PlayerSnapshot);
            SectionTitle.setText("Additional Features");
            Text.setText("Player Merging: \n" +
                    "    If you would like to combine two players together (different tag or misspelling), head to the Season tab and select the base player (the player that will remain after the combination, select the player to be merged, and then select the combine players button.  After selecting the update button, all changes should be represented on the top 10 if relevant.\n" +
                    "\n" +
                    "Changing Games:\n" +
                    "    To change to a different game (and a different list of seasons), head to the Game tab and select the game you would like to change to and select the change game button.  The Current Season will be the first season in the list.  To add a game, follow similar directions to adding a season, but in the Game tab.");
        } else{
            Text.setText("Good Luck :)");
        }
    }

    public void PGUp(){
        if(Integer.parseInt(PageNumber.getText()) < MaxPageNumber){
            int newValue = Integer.parseInt(PageNumber.getText()) + 1;
            PageNumber.setText(String.valueOf(newValue));
        }
        Tutorial();
    }

    public void PGDown(){
        if(Integer.parseInt(PageNumber.getText()) > 1){
            int newValue = Integer.parseInt(PageNumber.getText()) - 1;
            PageNumber.setText(String.valueOf(newValue));
        }
        Tutorial();
    }
}

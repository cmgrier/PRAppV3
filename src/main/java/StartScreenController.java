import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static javafx.scene.paint.Color.LIGHTGREEN;
import static javafx.scene.paint.Color.RED;

public class StartScreenController implements Initializable{

    @FXML
    TextField TournamentText, newSeasonTitle, NewPlayerTag, PlayerScore, ChangeTitle, InitialScore, GameTitle, KvalueSetting;

    @FXML
    TextArea FullPRText;

    @FXML
    Button AddTournamentButton, AddSeasonButton;

    @FXML
    ComboBox<String> ChangeSeason, ChangeGame, SelectPlayer, SelectPlayerStatistics, FirstCharacter, SecondCharacter, ThirdCharacter, DefaultSeasonBox, DefaultGameBox, BasePlayer, MergePlayer, RemoveTournament, DeleteSeason;

    @FXML
    Label CurrentGame, CurrentSeason, TitleText, WinLoss, WinPercentage, TourneysEntered, CurrentRank;

    @FXML
    ListView SetList, TournamentList;

    @FXML
    Label FirstP1,SecondP1,ThirdP1,FirstP2,SecondP2,ThirdP2,FirstP3,SecondP3,ThirdP3,FirstP4,SecondP4,ThirdP4,FirstP5,SecondP5,ThirdP5,
            FirstP6,SecondP6,ThirdP6,FirstP7,SecondP7,ThirdP7,FirstP8,SecondP8,ThirdP8,FirstP9,SecondP9,ThirdP9,FirstP10,SecondP10,ThirdP10 = new Label();

    @FXML
    Label PlayerName1,PlayerName2,PlayerName3,PlayerName4,PlayerName5,PlayerName6,PlayerName7,PlayerName8,PlayerName9,PlayerName10 = new Label();

    @FXML
    Label P1WR, P2WR, P3WR, P4WR, P5WR, P6WR, P7WR, P8WR, P9WR, P10WR, P1SCR, P2SCR, P3SCR, P4SCR, P5SCR, P6SCR, P7SCR, P8SCR, P9SCR, P10SCR;

    @FXML
    ImageView Characters11, Characters12, Characters13, Characters21, Characters22, Characters23, Characters31, Characters32, Characters33 ,
            Characters41, Characters42, Characters43, Characters51, Characters52, Characters53, Characters61, Characters62, Characters63,
            Characters71, Characters72, Characters73, Characters81, Characters82, Characters83, Characters91, Characters92, Characters93,
            Characters101, Characters102, Characters103 = new ImageView();

    @FXML
    Pane P1Arrow, P2Arrow, P3Arrow, P4Arrow, P5Arrow, P6Arrow, P7Arrow, P8Arrow, P9Arrow, P10Arrow;

    //Character images below
    private Image Miigunner = new Image("CharacterIcons/stock_90_miigunner_01.png");
    private Image Bayo = new Image("CharacterIcons/stock_90_bayonetta_01.png");
    private Image Falcon = new Image("CharacterIcons/stock_90_captain_01.png");
    private Image Cloud = new Image("CharacterIcons/stock_90_cloud_01.png");
    private Image DeDeDe = new Image("CharacterIcons/stock_90_dedede_01.png");
    private Image Diddy = new Image("CharacterIcons/stock_90_diddy_01.png");
    private Image DK = new Image("CharacterIcons/stock_90_donkey_01.png");
    private Image DrMario = new Image("CharacterIcons/stock_90_drmario_01.png");
    private Image DuckHunt = new Image("CharacterIcons/stock_90_duckhunt_01.png");
    private Image Falco = new Image("CharacterIcons/stock_90_falco_01.png");
    private Image Fox = new Image("CharacterIcons/stock_90_fox_01.png");
    private Image GameAndWatch = new Image("CharacterIcons/stock_90_gamewatch_01.png");
    private Image Ganon = new Image("CharacterIcons/stock_90_ganon_01.png");
    private Image Greninja = new Image("CharacterIcons/stock_90_gekkouga_01.png");
    private Image Ike = new Image("CharacterIcons/stock_90_ike_01.png");
    private Image Corrin = new Image("CharacterIcons/stock_90_kamui_01.png");
    private Image Kirby = new Image("CharacterIcons/stock_90_kirby_01.png");
    private Image Bowser = new Image("CharacterIcons/stock_90_koopa_01.png");
    private Image BowserJr = new Image("CharacterIcons/stock_90_koopajr_01.png");
    private Image Link = new Image("CharacterIcons/stock_90_link_01.png");
    private Image LittleMac = new Image("CharacterIcons/stock_90_littlemac_01.png");
    private Image Charizard = new Image("CharacterIcons/stock_90_lizardon_01.png");
    private Image Lucario = new Image("CharacterIcons/stock_90_lucario_01.png");
    private Image Lucas = new Image("CharacterIcons/stock_90_lucas_01.png");
    private Image Lucina = new Image("CharacterIcons/stock_90_lucina_01.png");
    private Image Luigi = new Image("CharacterIcons/stock_90_luigi_01.png");
    private Image Mario = new Image("CharacterIcons/stock_90_mario_01.png");
    private Image Marth = new Image("CharacterIcons/stock_90_marth_01.png");
    private Image MetaKnight = new Image("CharacterIcons/stock_90_metaknight_01.png");
    private Image MewTwo = new Image("CharacterIcons/stock_90_mewtwo_01.png");
    private Image MiiBrawler = new Image("CharacterIcons/stock_90_miifighter_01.png");
    private Image MiiSwordfighter = new Image("CharacterIcons/stock_90_miiswordsman_01.png");
    private Image Villager = new Image("CharacterIcons/stock_90_murabito_01.png");
    private Image Ness = new Image("CharacterIcons/stock_90_ness_01.png");
    private Image Pacman = new Image("CharacterIcons/stock_90_pacman_01.png");
    private Image Palutena = new Image("CharacterIcons/stock_90_palutena_01.png");
    private Image Peach = new Image("CharacterIcons/stock_90_peach_01.png");
    private Image Pikachu = new Image("CharacterIcons/stock_90_pikachu_01.png");
    private Image Olimar = new Image("CharacterIcons/stock_90_pikmin_01.png");
    private Image Pit = new Image("CharacterIcons/stock_90_pit_01.png");
    private Image DarkPit = new Image("CharacterIcons/stock_90_pitb_01.png");
    private Image JigglyPuff = new Image("CharacterIcons/stock_90_purin_01.png");
    private Image Robin = new Image("CharacterIcons/stock_90_reflet_01.png");
    private Image Rob = new Image("CharacterIcons/stock_90_robot_01.png");
    private Image MegaMan = new Image("CharacterIcons/stock_90_rockman_01.png");
    private Image Rosalina = new Image("CharacterIcons/stock_90_rosetta_01.png");
    private Image Roy = new Image("CharacterIcons/stock_90_roy_01.png");
    private Image Ryu = new Image("CharacterIcons/stock_90_ryu_01.png");
    private Image Samus = new Image("CharacterIcons/stock_90_samus_01.png");
    private Image Sheik = new Image("CharacterIcons/stock_90_sheik_01.png");
    private Image Shulk = new Image("CharacterIcons/stock_90_shulk_01.png");
    private Image Sonic = new Image("CharacterIcons/stock_90_sonic_01.png");
    private Image ZeroSuitSamus = new Image("CharacterIcons/stock_90_szerosuit_01.png");
    private Image ToonLink = new Image("CharacterIcons/stock_90_toonlink_01.png");
    private Image Wario = new Image("CharacterIcons/stock_90_wario_01.png");
    private Image WiiFit = new Image("CharacterIcons/stock_90_wiifit_01.png");
    private Image Yoshi = new Image("CharacterIcons/stock_90_yoshi_01.png");
    private Image Zelda = new Image("CharacterIcons/stock_90_zelda_01.png");
    private Image Random = new Image("CharacterIcons/stock_90_omakase_01.png");

    private Image MeleeBowser = new Image("CharacterIcons/ssbM/Bowser1.png");
    private Image MeleeCF = new Image("CharacterIcons/ssbM/CF1.png");
    private Image MeleeDK = new Image("CharacterIcons/ssbM/DK1.png");
    private Image MeleeDoc = new Image("CharacterIcons/ssbM/Doc1.png");
    private Image MeleeFalco = new Image("CharacterIcons/ssbM/Falco1.png");
    private Image MeleeFox = new Image("CharacterIcons/ssbM/Fox1.png");
    private Image MeleeGanon = new Image("CharacterIcons/ssbM/Ganon1.png");
    private Image MeleeGW = new Image("CharacterIcons/ssbM/GW1.png");
    private Image MeleeICs = new Image("CharacterIcons/ssbM/IC1.png");
    private Image MeleeJigs = new Image("CharacterIcons/ssbM/Jig1.png");
    private Image MeleeKirby = new Image("CharacterIcons/ssbM/Kirby1.png");
    private Image MeleeLink = new Image("CharacterIcons/ssbM/Link1.png");
    private Image MeleeLuigi = new Image("CharacterIcons/ssbM/Luigi1.png");
    private Image MeleeMario = new Image("CharacterIcons/ssbM/Mario1.png");
    private Image MeleeMarth = new Image("CharacterIcons/ssbM/Marth1.png");
    private Image MeleeMew2 = new Image("CharacterIcons/ssbM/Mew1.png");
    private Image MeleeNess = new Image("CharacterIcons/ssbM/Ness1.png");
    private Image MeleePeach = new Image("CharacterIcons/ssbM/Peach1.png");
    private Image MeleePichu = new Image("CharacterIcons/ssbM/Pichu1.png");
    private Image MeleePikachu = new Image("CharacterIcons/ssbM/Pika1.png");
    private Image MeleeRoy = new Image("CharacterIcons/ssbM/Roy1.png");
    private Image MeleeSamus = new Image("CharacterIcons/ssbM/Samus1.png");
    private Image MeleeSheik = new Image("CharacterIcons/ssbM/Sheik1.png");
    private Image MeleeYoshi = new Image("CharacterIcons/ssbM/Yoshi1.png");
    private Image MeleeYoungLink = new Image("CharacterIcons/ssbM/YoungLink1.png");
    private Image MeleeZelda = new Image("CharacterIcons/ssbM/Zelda1.png");

    private Image UltimateBayo = new Image("CharacterIcons/UltimateIcons/bayonetta.png");
    private Image UltimateBowser = new Image("CharacterIcons/UltimateIcons/bowser.png");
    private Image UltimateBowserJr = new Image("CharacterIcons/UltimateIcons/bowser_jr.png");
    private Image UltimateCF = new Image("CharacterIcons/UltimateIcons/captain_falcon.png");
    private Image UltimateChrom = new Image("CharacterIcons/UltimateIcons/chrom.png");
    private Image UltimateCloud = new Image("CharacterIcons/UltimateIcons/cloud.png");
    private Image UltimateCorrin = new Image("CharacterIcons/UltimateIcons/corrin.png");
    private Image UltimateDaisy = new Image("CharacterIcons/UltimateIcons/daisy.png");
    private Image UltimateDarkPit = new Image("CharacterIcons/UltimateIcons/dark_pit.png");
    private Image UltimateDarkSamus = new Image("CharacterIcons/UltimateIcons/dark_samus.png");
    private Image UltimateDiddy = new Image("CharacterIcons/UltimateIcons/diddy_kong.png");
    private Image UltimateDK = new Image("CharacterIcons/UltimateIcons/donkey_kong.png");
    private Image UltimateDoc = new Image("CharacterIcons/UltimateIcons/dr_mario.png");
    private Image UltimateDuckHunt = new Image("CharacterIcons/UltimateIcons/duck_hunt.png");
    private Image UltimateFalco = new Image("CharacterIcons/UltimateIcons/falco.png");
    private Image UltimateFox = new Image("CharacterIcons/UltimateIcons/fox.png");
    private Image UltimateGanondorf = new Image("CharacterIcons/UltimateIcons/ganondorf.png");
    private Image UltimateGreninja = new Image("CharacterIcons/UltimateIcons/greninja.png");
    private Image UltimateIceClimbers = new Image("CharacterIcons/UltimateIcons/ice_climbers.png");
    private Image UltimateIke = new Image("CharacterIcons/UltimateIcons/ike.png");
    private Image UltimateInkling = new Image("CharacterIcons/UltimateIcons/inkling.png");
    private Image UltimateJigglypuff = new Image("CharacterIcons/UltimateIcons/jigglypuff.png");
    private Image UltimateKingDDD = new Image("CharacterIcons/UltimateIcons/king_dedede.png");
    private Image UltimateKingKRool = new Image("CharacterIcons/UltimateIcons/king_k_rool.png");
    private Image UltimateKirby = new Image("CharacterIcons/UltimateIcons/kirby.png");
    private Image UltimateLink = new Image("CharacterIcons/UltimateIcons/link.png");
    private Image UltimateLittleMac = new Image("CharacterIcons/UltimateIcons/little_mac.png");
    private Image UltimateLucario = new Image("CharacterIcons/UltimateIcons/lucario.png");
    private Image UltimateLucas = new Image("CharacterIcons/UltimateIcons/lucas.png");
    private Image UltimateLucina = new Image("CharacterIcons/UltimateIcons/lucina.png");
    private Image UltimateLuigi = new Image("CharacterIcons/UltimateIcons/luigi.png");
    private Image UltimateMario = new Image("CharacterIcons/UltimateIcons/mario.png");
    private Image UltimateMarth = new Image("CharacterIcons/UltimateIcons/marth.png");
    private Image UltimateMegaMan = new Image("CharacterIcons/UltimateIcons/mega_man.png");
    private Image UltimateMetaKnight = new Image("CharacterIcons/UltimateIcons/meta_knight.png");
    private Image UltimateMewTwo = new Image("CharacterIcons/UltimateIcons/mewtwo.png");
    private Image UltimateMiiFighter = new Image("CharacterIcons/UltimateIcons/mii_fighter.png");
    private Image UltimateMrGameAndWatch = new Image("CharacterIcons/UltimateIcons/mr_game_and_watch.png");
    private Image UltimateNess = new Image("CharacterIcons/UltimateIcons/ness.png");
    private Image UltimateOlimar = new Image("CharacterIcons/UltimateIcons/olimar.png");
    private Image UltimatePacMan = new Image("CharacterIcons/UltimateIcons/pac_man.png");
    private Image UltimatePalutena = new Image("CharacterIcons/UltimateIcons/palutena.png");
    private Image UltimatePeach = new Image("CharacterIcons/UltimateIcons/peach.png");
    private Image UltimatePichu = new Image("CharacterIcons/UltimateIcons/pichu.png");
    private Image UltimatePikachu = new Image("CharacterIcons/UltimateIcons/pikachu.png");
    private Image UltimatePit = new Image("CharacterIcons/UltimateIcons/pit.png");
    private Image UltimatePokemonTrainer = new Image("CharacterIcons/UltimateIcons/pokemon_trainer.png");
    private Image UltimateRichter = new Image("CharacterIcons/UltimateIcons/richter.png");
    private Image UltimateRidley = new Image("CharacterIcons/UltimateIcons/ridley.png");
    private Image UltimateRob = new Image("CharacterIcons/UltimateIcons/rob.png");
    private Image UltimateRobin = new Image("CharacterIcons/UltimateIcons/robin.png");
    private Image UltimateRosalina = new Image("CharacterIcons/UltimateIcons/rosalina_and_luma.png");
    private Image UltimateRoy = new Image("CharacterIcons/UltimateIcons/roy.png");
    private Image UltimateRyu = new Image("CharacterIcons/UltimateIcons/ryu.png");
    private Image UltimateSamus = new Image("CharacterIcons/UltimateIcons/samus.png");
    private Image UltimateSheik = new Image("CharacterIcons/UltimateIcons/sheik.png");
    private Image UltimateShulk = new Image("CharacterIcons/UltimateIcons/shulk.png");
    private Image UltimateSimon = new Image("CharacterIcons/UltimateIcons/simon.png");
    private Image UltimateSnake = new Image("CharacterIcons/UltimateIcons/snake.png");
    private Image UltimateSonic = new Image("CharacterIcons/UltimateIcons/sonic.png");
    private Image UltimateToonLink = new Image("CharacterIcons/UltimateIcons/toon_link.png");
    private Image UltimateVillager = new Image("CharacterIcons/UltimateIcons/villager.png");
    private Image UltimateWario = new Image("CharacterIcons/UltimateIcons/wario.png");
    private Image UltimateWiiFit = new Image("CharacterIcons/UltimateIcons/wii_fit_trainer.png");
    private Image UltimateWolf = new Image("CharacterIcons/UltimateIcons/wolf.png");
    private Image UltimateYoshi = new Image("CharacterIcons/UltimateIcons/yoshi.png");
    private Image UltimateYoungLink = new Image("CharacterIcons/UltimateIcons/young_link.png");
    private Image UltimateZelda = new Image("CharacterIcons/UltimateIcons/zelda.png");
    private Image UltimateZeroSuitSamus = new Image("CharacterIcons/UltimateIcons/zero_suit_samus.png");


    Font SFQuartziteOblique = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 27);
    Font SFQuartziteObliqueS = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 23);
    Font SFQuartziteObliqueXS = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 19);
    Font SFQuartziteObliqueXXS = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 15);
    Font SFQuartziteShadedOblique = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartziteShaded-Oblique.ttf"), 24);
    Font SFQuartziteExtendedOblique = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartziteExtended-Oblique.ttf"), 20);
    Font AbandonedItalic = Font.loadFont(getClass().getResourceAsStream("/Fonts/Abandoned-Italic.ttf"), 24);
    Font AbandonedBoldItalic = Font.loadFont(getClass().getResourceAsStream("/Fonts/Abandoned-BoldItalic.ttf"), 24);
    Font BROKEREN = Font.loadFont(getClass().getResourceAsStream("/Fonts/BROKEREN.ttf"), 24);
    Font BROKERENMIRING = Font.loadFont(getClass().getResourceAsStream("/Fonts/BROKEREN MIRING.ttf"), 24);
    Font SFQuartziteObliqueTitle = Font.loadFont(getClass().getResourceAsStream("/Fonts/SFQuartzite-Oblique.ttf"), 70);

    String defaultGame = "";
    String defaultSeason = "";
    int minimumTE = 0;
    int K = 24;
    String currentMethod = "Placement";

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // do all things here that happen when this screen is started
        readSettings();
        CurrentGame.setText(defaultGame);
        CurrentSeason.setText(defaultSeason);
        updateSeasonList();
        fillCharacterList();
        fillPlayerBox();
        updateTopTen();//
        //updateCharactersAndPlacings();
        updateTournamentList();
        WinLoss.setVisible(false);
        WinPercentage.setVisible(false);
        TourneysEntered.setVisible(false);
        CurrentRank.setVisible(false);
        setFont(SFQuartziteOblique);
        TitleText.setFont(SFQuartziteObliqueTitle);
        SetList.setVisible(false);
        fillGameBox();
        DefaultSeasonBox.setDisable(true);
    }

    private void setFont(Font font){
        PlayerName1.setFont(font);
        PlayerName2.setFont(font);
        PlayerName3.setFont(font);
        PlayerName4.setFont(font);
        PlayerName5.setFont(font);
        PlayerName6.setFont(font);
        PlayerName7.setFont(font);
        PlayerName8.setFont(font);
        PlayerName9.setFont(font);
        PlayerName10.setFont(font);
    }

    private void readSettings(){
        try {
            FileReader fr = new FileReader("Data/Settings");
            BufferedReader br = new BufferedReader(fr);
            int lineCnt = 0;
            String line;
            while((line = br.readLine()) != null){
                lineCnt++;
                if(lineCnt == 1){
                    TitleText.setText(line);
                }
                if(lineCnt == 2){
                    defaultSeason = line;
                    if(defaultSeason.equals("<first>")){
                        Database DB = new Database();
                        ArrayList<String> seasons = DB.getSeasons("all");
                        defaultSeason = seasons.get(0);
                    }
                }
                if(lineCnt == 3){
                    defaultGame = line;
                    if(defaultGame.equals("default")){
                        Database DB = new Database();
                    }
                }
                if(lineCnt == 4){
                    K = Integer.parseInt(line);
                }
                if(lineCnt == 5){
                    minimumTE = Integer.parseInt(line);
                }
            }
            br.close();
            fr.close();

        }catch (IOException ioe){}
    }

    public void defaultGameSettings(){
        if(DefaultGameBox.getValue().equals("") || DefaultGameBox.getValue() == null){
            DefaultSeasonBox.setValue("Default Season");
            DefaultSeasonBox.getItems().clear();
            DefaultSeasonBox.setDisable(true);
        } else {
            DefaultSeasonBox.setDisable(false);
            File[] seasonFiles = new File("Data/" + DefaultGameBox.getValue() + "/Seasons").listFiles();
            ArrayList<String> seasonTitles = new ArrayList<>();
            for (File seasonFile:seasonFiles) {
                seasonTitles.add(seasonFile.getName());
            }
            Collections.sort(seasonTitles);
            DefaultSeasonBox.getItems().clear();
            DefaultSeasonBox.getItems().addAll(seasonTitles);
        }
    }

    public void updateSeasonList(){
        Database DB = new Database();
        ArrayList<String> seasonTitles = DB.getSeasons(CurrentGame.getText());
        Collections.sort(seasonTitles);
        ChangeSeason.getItems().clear();
        ChangeSeason.getItems().addAll(seasonTitles);
        DefaultSeasonBox.getItems().clear();
        DefaultSeasonBox.getItems().addAll(seasonTitles);
        DeleteSeason.getItems().clear();
        DeleteSeason.getItems().addAll(seasonTitles);
    }

    public void fillCharacterList(){
        ArrayList<String> options = new ArrayList<>();
        options.add("Bayonetta");
        options.add("Falcon");
        options.add("Cloud");
        options.add("DeDeDe");
        options.add("Diddy");
        options.add("DK");
        options.add("DrMario");
        options.add("DuckHunt");
        options.add("Falco");
        options.add("Fox");
        options.add("GameAndWatch");
        options.add("Ganon");
        options.add("Greninja");
        options.add("Ike");
        options.add("Corrin");
        options.add("Kirby");
        options.add("Bowser");
        options.add("BowserJr");
        options.add("Link");
        options.add("LittleMac");
        options.add("Charizard");
        options.add("Lucario");
        options.add("Lucas");
        options.add("Lucina");
        options.add("Luigi");
        options.add("Mario");
        options.add("Marth");
        options.add("MetaKnight");
        options.add("MewTwo");
        options.add("MiiBrawler");
        options.add("MiiGunner");
        options.add("MiiSwordfighter");
        options.add("Villager");
        options.add("Ness");
        options.add("Pacman");
        options.add("Palutena");
        options.add("Peach");
        options.add("Pikachu");
        options.add("Olimar");
        options.add("Pit");
        options.add("DarkPit");
        options.add("JigglyPuff");
        options.add("Robin");
        options.add("MegaMan");
        options.add("Rob");
        options.add("Rosalina");
        options.add("Roy");
        options.add("Ryu");
        options.add("Samus");
        options.add("Sheik");
        options.add("Shulk");
        options.add("Sonic");
        options.add("ZeroSuitSamus");
        options.add("ToonLink");
        options.add("Wario");
        options.add("WiiFit");
        options.add("Yoshi");
        options.add("Zelda");

        options.add("<Clear>");

        if(CurrentGame.getText().contains("Melee")){
            options.clear();
            options.add("Falcon");
            options.add("DK");
            options.add("DrMario");
            options.add("Falco");
            options.add("Fox");
            options.add("GameAndWatch");
            options.add("Ganon");
            options.add("Kirby");
            options.add("Bowser");
            options.add("Link");
            options.add("Luigi");
            options.add("Mario");
            options.add("Marth");
            options.add("MewTwo");
            options.add("IceClimbers");
            options.add("Ness");
            options.add("Pichu");
            options.add("Peach");
            options.add("Pikachu");
            options.add("JigglyPuff");
            options.add("Roy");
            options.add("Samus");
            options.add("Sheik");
            options.add("YoungLink");
            options.add("Yoshi");
            options.add("Zelda");

            options.add("<Clear>");
        }
        if(CurrentGame.getText().contains("Ultimate")){
            options.clear();
            options.add("Bayonetta");
            options.add("Falcon");
            options.add("Cloud");
            options.add("DeDeDe");
            options.add("Diddy");
            options.add("DK");
            options.add("DrMario");
            options.add("DuckHunt");
            options.add("Falco");
            options.add("Fox");
            options.add("GameAndWatch");
            options.add("Ganon");
            options.add("Greninja");
            options.add("Ike");
            options.add("Corrin");
            options.add("Kirby");
            options.add("Bowser");
            options.add("BowserJr");
            options.add("Link");
            options.add("LittleMac");
            options.add("Lucario");
            options.add("Lucas");
            options.add("Lucina");
            options.add("Luigi");
            options.add("Mario");
            options.add("Marth");
            options.add("MetaKnight");
            options.add("MewTwo");
            options.add("MiiBrawler");
            options.add("MiiGunner");
            options.add("MiiSwordfighter");
            options.add("Villager");
            options.add("Ness");
            options.add("Pacman");
            options.add("Palutena");
            options.add("Peach");
            options.add("Pikachu");
            options.add("Olimar");
            options.add("Pit");
            options.add("DarkPit");
            options.add("JigglyPuff");
            options.add("Robin");
            options.add("MegaMan");
            options.add("Rob");
            options.add("Rosalina");
            options.add("Roy");
            options.add("Ryu");
            options.add("Samus");
            options.add("Sheik");
            options.add("Shulk");
            options.add("Sonic");
            options.add("ZeroSuitSamus");
            options.add("ToonLink");
            options.add("Wario");
            options.add("WiiFit");
            options.add("Yoshi");
            options.add("Zelda");
            options.add("YoungLink");
            options.add("Pichu");
            options.add("IceClimbers");
            options.add("Snake");
            options.add("Wolf");
            options.add("Ridley");
            options.add("Inkling");
            options.add("PokemonTrainer");
            options.add("Chrom");
            options.add("DarkSamus");
            options.add("KingKRool");
            options.add("Richter");
            options.add("Simon");
            options.add("<Clear>");
        }

        FirstCharacter.getItems().clear();
        SecondCharacter.getItems().clear();
        ThirdCharacter.getItems().clear();
        Collections.sort(options);
        FirstCharacter.getItems().addAll(options);
        SecondCharacter.getItems().addAll(options);
        ThirdCharacter.getItems().addAll(options);
    }


    // Todo needs fixing for V3
    public void createSeason(){
        String game = CurrentGame.getText();
        if(newSeasonTitle.getText() != null) {
            String seasonTitle = newSeasonTitle.getText();
            try {
                FileWriter fw = new FileWriter("Data/" + game + "/Seasons/" + seasonTitle + ".csv");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(seasonTitle);
                bw.close();
                fw.close();
            } catch (IOException ioe) {
                System.out.println("couldn't create season");
            }
        } else {
            String seasonTitle = "NewSeason";
            try {
                FileWriter fw = new FileWriter("Data/" + game + "/Seasons/" + seasonTitle + ".csv");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(seasonTitle);
                bw.close();
                fw.close();
            } catch (IOException ioe) {
                System.out.println("couldn't create season");
            }
        }
        updateSeasonList();
        updateTournamentList();
    }

    public void changeSeason(){
        CurrentSeason.setText(ChangeSeason.getValue());
        updateTopTen();
        fillPlayerBox();
        updateTournamentList();
        WinLoss.setVisible(false);
        WinPercentage.setVisible(false);
        TourneysEntered.setVisible(false);
        CurrentRank.setVisible(false);
        SetList.getItems().clear();
        SetList.setVisible(false);
        update();
    }

    public void updateTournamentList(){
        TournamentList.getItems().clear();
        RemoveTournament.getItems().clear();
        Database DB = new Database();
        ArrayList<String> tournaments = DB.getTournaments(DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText()));
        TournamentList.getItems().addAll(tournaments);
        RemoveTournament.getItems().addAll(tournaments);
    }

    public void changeGame(){
        if(!ChangeGame.getValue().equals("Change Game")) {
            CurrentGame.setText(ChangeGame.getValue());
            Database DB = new Database();
            ArrayList<String> seasons = DB.getSeasons(CurrentGame.getText());
            CurrentSeason.setText(seasons.get(0));
            update();
            updateTournamentList();
            updateSeasonList();
            fillPlayerBox();
            fillCharacterList();
            ChangeGame.setValue("Change Game");
        }
    }

    // Todo needs to be redone for V3
    public void addGame() {
//        try {
//            Files.createDirectories(Paths.get("Data/" + GameTitle.getText() + "/Seasons"));
//            Files.createDirectories(Paths.get("Data/" + GameTitle.getText() + "/Tournaments/CSVFiles"));
//            Files.createDirectories(Paths.get("Data/" + GameTitle.getText() + "/Tournaments/JSONFiles"));
//            FileWriter fw = new FileWriter("Data/" + GameTitle.getText() + "/Seasons/EmptySeason.csv");
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write("EmptySeason");
//            bw.close();
//            fw.close();
//            CurrentGame.setText(GameTitle.getText());
//            CurrentSeason.setText("EmptySeason.csv");
//            GameTitle.clear();
//        } catch (IOException ioe) {
//            System.out.println("Could not create Game");
//        }
//        update();
//        updateTournamentList();
//        updateSeasonList();
//        fillPlayerBox();
//        fillGameBox();
    }

    public void fillGameBox(){
        Database DB = new Database();
        ArrayList<String> games = DB.getGames();
        Collections.sort(games);
        ChangeGame.getItems().clear();
        DefaultGameBox.getItems().clear();
        ChangeGame.getItems().addAll(games);
        DefaultGameBox.getItems().addAll(games);
    }

    public void updateTagsScoresArrows(){
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        ArrayList<Player> players = DB.getOrderedList(seasonID, minimumTE);
        clearTable();
        setPlayerName(PlayerName10, players.get(9).tag);
        updateScore(P10SCR,players.get(9).score);
        setPlayerName(PlayerName9, players.get(8).tag);
        updateScore(P9SCR,players.get(8).score);
        setPlayerName(PlayerName8, players.get(7).tag);
        updateScore(P8SCR,players.get(7).score);
        setPlayerName(PlayerName7, players.get(6).tag);
        updateScore(P7SCR,players.get(6).score);
        setPlayerName(PlayerName6, players.get(5).tag);
        updateScore(P6SCR,players.get(5).score);
        setPlayerName(PlayerName5, players.get(4).tag);
        updateScore(P5SCR,players.get(4).score);
        setPlayerName(PlayerName4, players.get(3).tag);
        updateScore(P4SCR,players.get(3).score);
        setPlayerName(PlayerName3, players.get(2).tag);
        updateScore(P3SCR,players.get(2).score);
        setPlayerName(PlayerName2, players.get(1).tag);
        updateScore(P2SCR,players.get(1).score);
        setPlayerName(PlayerName1, players.get(0).tag);
        updateScore(P1SCR,players.get(0).score);

        addArrow(players.get(0),P1Arrow);
        addArrow(players.get(1),P2Arrow);
        addArrow(players.get(2),P3Arrow);
        addArrow(players.get(3),P4Arrow);
        addArrow(players.get(4),P5Arrow);
        addArrow(players.get(5),P6Arrow);
        addArrow(players.get(6),P7Arrow);
        addArrow(players.get(7),P8Arrow);
        addArrow(players.get(8),P9Arrow);
        addArrow(players.get(9),P10Arrow);
    }

    private void setPlayerName(Label label, String tag){
        label.setText(tag);
        label.setFont(SFQuartziteOblique);
        if(tag.length() > 12 && tag.length() < 15){
            label.setFont(SFQuartziteObliqueS);
        } else if(tag.length() >= 15 && tag.length() < 18){
            label.setFont(SFQuartziteObliqueXS);
        } else if(tag.length() >= 18){
            label.setFont(SFQuartziteObliqueXXS);
        }
    }

    private void addArrow(Player player, Pane pane){
        Polygon Arrow = new Polygon();
        Arrow.getPoints().addAll(
                0.0, 5.0,
                0.0, 20.0,
                -5.0, 20.0,
                5.0, 30.0,
                15.0, 20.0,
                10.0, 20.0,
                10.0, 5.0
        );
        Arrow.setVisible(true);
        if(player.score > (player.initialScore + 35)){
            Arrow.setFill(LIGHTGREEN);
            Arrow.setRotate(180);
        } else if(player.score < (player.initialScore - 35)) {
            Arrow.setFill(RED);
            Arrow.setOpacity(.5);
        } else {
            Arrow.setVisible(false);
        }
        if(player.score == 0){
            Arrow.setVisible(false);
        }
        pane.getChildren().clear();
        pane.getChildren().add(Arrow);
    }

    private void updateEntry(Player player, ImageView character1, ImageView character2, ImageView character3, Label firstPlace, Label secondPlace, Label thirdPlace, Label WR){
        if(player.tag.equals("")){
            character1.setImage(null);
            character2.setImage(null);
            character3.setImage(null);
            firstPlace.setText(null);
            secondPlace.setText(null);
            thirdPlace.setText(null);
        } else{
            ArrayList<String> characters = player.getCharacters();
            if(characters.size() > 1){
                character2.setImage(getCharacterImage(characters.get(1)));
            } else {
                character2.setImage(null);
            }
            if(player.getCharacters().size() > 2){
                character3.setImage(getCharacterImage(characters.get(2)));
            } else {
                character3.setImage(null);
            }

            character1.setImage(getCharacterImage(characters.get(0)));
            Database DB = new Database();
            ArrayList<Integer> characterPlacings = DB.getPlayerPlacements(player.playerID);
            int firstPlaces = 0;
            int secondPlaces = 0;
            int thirdPlaces = 0;
            for (Integer placing:characterPlacings) {
                if(placing == 1){
                    firstPlaces++;
                } else if(placing == 2){
                    secondPlaces++;
                } else if(placing == 3){
                    thirdPlaces++;
                }
            }
            firstPlace.setText(String.valueOf(firstPlaces));
            secondPlace.setText(String.valueOf(secondPlaces));
            thirdPlace.setText(String.valueOf(thirdPlaces));

            updateWinRate(player, WR);

        }
    }

    private void updateTopTen(){
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        updateTagsScoresArrows();
        ArrayList<Player> players = DB.getOrderedList(seasonID, minimumTE);
        updateEntry(players.get(0), Characters11, Characters12, Characters13, FirstP1, SecondP1, ThirdP1, P1WR);
        updateEntry(players.get(1), Characters21, Characters22, Characters23, FirstP2, SecondP2, ThirdP2, P2WR);
        updateEntry(players.get(2), Characters31, Characters32, Characters33, FirstP3, SecondP3, ThirdP3, P3WR);
        updateEntry(players.get(3), Characters41, Characters42, Characters43, FirstP4, SecondP4, ThirdP4, P4WR);
        updateEntry(players.get(4), Characters51, Characters52, Characters53, FirstP5, SecondP5, ThirdP5, P5WR);
        updateEntry(players.get(5), Characters61, Characters62, Characters63, FirstP6, SecondP6, ThirdP6, P6WR);
        updateEntry(players.get(6), Characters71, Characters72, Characters73, FirstP7, SecondP7, ThirdP7, P7WR);
        updateEntry(players.get(7), Characters81, Characters82, Characters83, FirstP8, SecondP8, ThirdP8, P8WR);
        updateEntry(players.get(8), Characters91, Characters92, Characters93, FirstP9, SecondP9, ThirdP9, P9WR);
        updateEntry(players.get(9), Characters101, Characters102, Characters103, FirstP10, SecondP10, ThirdP10, P10WR);
    }

    // Todo need to add new characters recently released
    private Image getCharacterImage(String character){
        Image returnImage = Random;
        if(character.equals("Bayonetta")){
            returnImage = Bayo;
        }
        if(character.equals("Falcon")){
            returnImage = Falcon;
        }
        if(character.equals("Cloud")){
            returnImage = Cloud;
        }
        if(character.equals("DeDeDe")){
            returnImage = DeDeDe;
        }
        if(character.equals("Diddy")){
            returnImage = Diddy;
        }
        if(character.equals("DK")){
            returnImage = DK;
        }
        if(character.equals("DrMario")){
            returnImage = DrMario;
        }
        if(character.equals("DuckHunt")){
            returnImage = DuckHunt;
        }
        if(character.equals("Falco")){
            returnImage = Falco;
        }
        if(character.equals("Fox")){
            returnImage = Fox;
        }
        if(character.equals("GameAndWatch")){
            returnImage = GameAndWatch;
        }
        if(character.equals("Ganon")){
            returnImage = Ganon;
        }
        if(character.equals("Greninja")){
            returnImage = Greninja;
        }
        if(character.equals("Ike")){
            returnImage = Ike;
        }
        if(character.equals("Corrin")){
            returnImage = Corrin;
        }
        if(character.equals("Kirby")){
            returnImage = Kirby;
        }
        if(character.equals("Bowser")){
            returnImage = Bowser;
        }
        if(character.equals("BowserJr")){
            returnImage = BowserJr;
        }
        if(character.equals("Link")){
            returnImage = Link;
        }
        if(character.equals("LittleMac")){
            returnImage = LittleMac;
        }
        if(character.equals("Charizard")){
            returnImage = Charizard;
        }
        if(character.equals("Lucario")){
            returnImage = Lucario;
        }
        if(character.equals("Lucas")){
            returnImage = Lucas;
        }
        if(character.equals("Lucina")){
            returnImage = Lucina;
        }
        if(character.equals("Luigi")){
            returnImage = Luigi;
        }
        if(character.equals("Mario")){
            returnImage = Mario;
        }
        if(character.equals("Marth")){
            returnImage = Marth;
        }
        if(character.equals("MetaKnight")){
            returnImage = MetaKnight;
        }
        if(character.equals("MewTwo")){
            returnImage = MewTwo;
        }
        if(character.equals("MiiGunner")){
            returnImage = Miigunner;
        }
        if(character.equals("MiiBrawler")){
            returnImage = MiiBrawler;
        }
        if(character.equals("MiiSwordfighter")){
            returnImage = MiiSwordfighter;
        }
        if(character.equals("Villager")){
            returnImage = Villager;
        }
        if(character.equals("Ness")){
            returnImage = Ness;
        }
        if(character.equals("Pacman")){
            returnImage = Pacman;
        }
        if(character.equals("Palutena")){
            returnImage = Palutena;
        }
        if(character.equals("Peach")){
            returnImage = Peach;
        }
        if(character.equals("Pikachu")){
            returnImage = Pikachu;
        }
        if(character.equals("Olimar")){
            returnImage = Olimar;
        }
        if(character.equals("Pit")){
            returnImage = Pit;
        }
        if(character.equals("DarkPit")){
            returnImage = DarkPit;
        }
        if(character.equals("JigglyPuff")){
            returnImage = JigglyPuff;
        }
        if(character.equals("Robin")){
            returnImage = Robin;
        }
        if(character.equals("Rob")){
            returnImage = Rob;
        }
        if(character.equals("MegaMan")){
            returnImage = MegaMan;
        }
        if(character.equals("Rosalina")){
            returnImage = Rosalina;
        }
        if(character.equals("Roy")){
            returnImage = Roy;
        }
        if(character.equals("Ryu")){
            returnImage = Ryu;
        }
        if(character.equals("Samus")){
            returnImage = Samus;
        }
        if(character.equals("Sheik")){
            returnImage = Sheik;
        }
        if(character.equals("Shulk")){
            returnImage = Shulk;
        }
        if(character.equals("Sonic")){
            returnImage = Sonic;
        }
        if(character.equals("ZeroSuitSamus")){
            returnImage = ZeroSuitSamus;
        }
        if(character.equals("ToonLink")){
            returnImage = ToonLink;
        }
        if(character.equals("Wario")){
            returnImage = Wario;
        }
        if(character.equals("WiiFit")){
            returnImage = WiiFit;
        }
        if(character.equals("Yoshi")){
            returnImage = Yoshi;
        }
        if(character.equals("Zelda")){
            returnImage = Zelda;
        }
        if(CurrentGame.getText().contains("Melee")){
            if(character.equals("Falcon")){
                returnImage = MeleeCF;
            }
            if(character.equals("DK")){
                returnImage = MeleeDK;
            }
            if(character.equals("DrMario")){
                returnImage = MeleeDoc;
            }
            if(character.equals("Falco")){
                returnImage = MeleeFalco;
            }
            if(character.equals("Fox")){
                returnImage = MeleeFox;
            }
            if(character.equals("GameAndWatch")){
                returnImage = MeleeGW;
            }
            if(character.equals("Ganon")){
                returnImage = MeleeGanon;
            }
            if(character.equals("Kirby")){
                returnImage = MeleeKirby;
            }
            if(character.equals("Bowser")){
                returnImage = MeleeBowser;
            }
            if(character.equals("Link")){
                returnImage = MeleeLink;
            }
            if(character.equals("Luigi")){
                returnImage = MeleeLuigi;
            }
            if(character.equals("Mario")){
                returnImage = MeleeMario;
            }
            if(character.equals("Marth")){
                returnImage = MeleeMarth;
            }
            if(character.equals("MewTwo")){
                returnImage = MeleeMew2;
            }
            if(character.equals("IceClimbers")){
                returnImage = MeleeICs;
            }
            if(character.equals("Ness")){
                returnImage = MeleeNess;
            }
            if(character.equals("Pichu")){
                returnImage = MeleePichu;
            }
            if(character.equals("Peach")){
                returnImage = MeleePeach;
            }
            if(character.equals("Pikachu")){
                returnImage = MeleePikachu;
            }
            if(character.equals("JigglyPuff")){
                returnImage = MeleeJigs;
            }
            if(character.equals("Roy")){
                returnImage = MeleeRoy;
            }
            if(character.equals("Samus")){
                returnImage = MeleeSamus;
            }
            if(character.equals("Sheik")){
                returnImage = MeleeSheik;
            }
            if(character.equals("YoungLink")){
                returnImage = MeleeYoungLink;
            }
            if(character.equals("Yoshi")){
                returnImage = MeleeYoshi;
            }
            if(character.equals("Zelda")){
                returnImage = MeleeZelda;
            }
        } else if(CurrentGame.getText().contains("Ultimate") || CurrentGame.getText().contains("SmashU")){
            if(character.equals("Bayonetta")){
                returnImage = UltimateBayo;
            }
            if(character.equals("Falcon")){
                returnImage = UltimateCF;
            }
            if(character.equals("Cloud")){
                returnImage = UltimateCloud;
            }
            if(character.equals("DeDeDe")){
                returnImage = UltimateKingDDD;
            }
            if(character.equals("Diddy")){
                returnImage = UltimateDiddy;
            }
            if(character.equals("DK")){
                returnImage = UltimateDK;
            }
            if(character.equals("DrMario")){
                returnImage = UltimateDoc;
            }
            if(character.equals("DuckHunt")){
                returnImage = UltimateDuckHunt;
            }
            if(character.equals("Falco")){
                returnImage = UltimateFalco;
            }
            if(character.equals("Fox")){
                returnImage = UltimateFox;
            }
            if(character.equals("GameAndWatch")){
                returnImage = UltimateMrGameAndWatch;
            }
            if(character.equals("Ganon")){
                returnImage = UltimateGanondorf;
            }
            if(character.equals("Greninja")){
                returnImage = UltimateGreninja;
            }
            if(character.equals("Ike")){
                returnImage = UltimateIke;
            }
            if(character.equals("Corrin")){
                returnImage = UltimateCorrin;
            }
            if(character.equals("Kirby")){
                returnImage = UltimateKirby;
            }
            if(character.equals("Bowser")){
                returnImage = UltimateBowser;
            }
            if(character.equals("BowserJr")){
                returnImage = UltimateBowserJr;
            }
            if(character.equals("Link")){
                returnImage = UltimateLink;
            }
            if(character.equals("LittleMac")){
                returnImage = UltimateLittleMac;
            }
            if(character.equals("PokemonTrainer")){
                returnImage = UltimatePokemonTrainer;
            }
            if(character.equals("Lucario")){
                returnImage = UltimateLucario;
            }
            if(character.equals("Lucas")){
                returnImage = UltimateLucas;
            }
            if(character.equals("Lucina")){
                returnImage = UltimateLucina;
            }
            if(character.equals("Luigi")){
                returnImage = UltimateLuigi;
            }
            if(character.equals("Mario")){
                returnImage = UltimateMario;
            }
            if(character.equals("Marth")){
                returnImage = UltimateMarth;
            }
            if(character.equals("MetaKnight")){
                returnImage = UltimateMetaKnight;
            }
            if(character.equals("MewTwo")){
                returnImage = UltimateMewTwo;
            }
            if(character.equals("MiiGunner")){
                returnImage = UltimateMiiFighter;
            }
            if(character.equals("MiiBrawler")){
                returnImage = UltimateMiiFighter;
            }
            if(character.equals("MiiSwordfighter")){
                returnImage = UltimateMiiFighter;
            }
            if(character.equals("Villager")){
                returnImage = UltimateVillager;
            }
            if(character.equals("Ness")){
                returnImage = UltimateNess;
            }
            if(character.equals("Pacman")){
                returnImage = UltimatePacMan;
            }
            if(character.equals("Palutena")){
                returnImage = UltimatePalutena;
            }
            if(character.equals("Peach")){
                returnImage = UltimatePeach;
            }
            if(character.equals("Daisy")){
                returnImage = UltimateDaisy;
            }
            if(character.equals("Snake")){
                returnImage = UltimateSnake;
            }
            if(character.equals("Pikachu")){
                returnImage = UltimatePikachu;
            }
            if(character.equals("Olimar")){
                returnImage = UltimateOlimar;
            }
            if(character.equals("Pit")){
                returnImage = UltimatePit;
            }
            if(character.equals("DarkPit")){
                returnImage = UltimateDarkPit;
            }
            if(character.equals("JigglyPuff")){
                returnImage = UltimateJigglypuff;
            }
            if(character.equals("Robin")){
                returnImage = UltimateRobin;
            }
            if(character.equals("Rob")){
                returnImage = UltimateRob;
            }
            if(character.equals("MegaMan")){
                returnImage = UltimateMegaMan;
            }
            if(character.equals("Rosalina")){
                returnImage = UltimateRosalina;
            }
            if(character.equals("Roy")){
                returnImage = UltimateRoy;
            }
            if(character.equals("Ryu")){
                returnImage = UltimateRyu;
            }
            if(character.equals("Samus")){
                returnImage = UltimateSamus;
            }
            if(character.equals("Sheik")){
                returnImage = UltimateSheik;
            }
            if(character.equals("Shulk")){
                returnImage = UltimateShulk;
            }
            if(character.equals("Sonic")){
                returnImage = UltimateSonic;
            }
            if(character.equals("ZeroSuitSamus")){
                returnImage = UltimateZeroSuitSamus;
            }
            if(character.equals("ToonLink")){
                returnImage = UltimateToonLink;
            }
            if(character.equals("Wario")){
                returnImage = UltimateWario;
            }
            if(character.equals("WiiFit")){
                returnImage = UltimateWiiFit;
            }
            if(character.equals("Yoshi")){
                returnImage = UltimateYoshi;
            }
            if(character.equals("Zelda")){
                returnImage = UltimateZelda;
            }
            if(character.equals("Wolf")){
                returnImage = UltimateWolf;
            }
            if(character.equals("YoungLink")){
                returnImage = UltimateYoungLink;
            }
            if(character.equals("Ridley")){
                returnImage = UltimateRidley;
            }
            if(character.equals("Inkling")){
                returnImage = UltimateInkling;
            }
            if(character.equals("IceClimbers")){
                returnImage = UltimateIceClimbers;
            }
            if(character.equals("Pichu")){
                returnImage = UltimatePichu;
            }
            if(character.equals("Chrom")){
                returnImage = UltimateChrom;
            }
            if(character.equals("DarkSamus")){
                returnImage = UltimateDarkSamus;
            }
            if(character.equals("KingKRool")){
                returnImage = UltimateKingKRool;
            }
            if(character.equals("Richter")){
                returnImage = UltimateRichter;
            }
            if(character.equals("Simon")){
                returnImage = UltimateSimon;
            }
        }
        return returnImage;
    }

    private void clearTable(){
        PlayerName1.setText("");
        PlayerName2.setText("");
        PlayerName3.setText("");
        PlayerName4.setText("");
        PlayerName5.setText("");
        PlayerName6.setText("");
        PlayerName7.setText("");
        PlayerName8.setText("");
        PlayerName9.setText("");
        PlayerName10.setText("");
    }

//    // Todo needs fixing for V3
//    public Season getSeason(String game, String seasonTitle){
//        Season s = new Season("Could Not Find Season");
//        try {
//            FileReader fr = new FileReader("Data/" + game + "/Seasons/" + seasonTitle);
//            BufferedReader br = new BufferedReader(fr);
//            String line;
//            int lineCnt = 0;
//            ArrayList<String> tournamentList = new ArrayList<>();
//            ArrayList<Player> players = new ArrayList<>();
//            while((line = br.readLine()) != null){
//                lineCnt++;
//                if(lineCnt == 2){
//                    String[] tourneyList = line.split(",");
//                    for (String tournament:tourneyList) {
//                        tournamentList.add(tournament);
//                    }
//                } else if(lineCnt > 2){
//                    String[] thePlayer = line.split(",");
//                    String[] theCharacters = thePlayer[2].split(":");
//                    ArrayList<String> characters = new ArrayList<>();
//                    for (String character:theCharacters) {
//                        characters.add(character);
//                    }
//                    Player p = new Player(thePlayer[0],Double.parseDouble(thePlayer[1]),characters,Double.parseDouble(thePlayer[3]));
//                    players.add(p);
//                }
//            }
//            s = new Season(seasonTitle,tournamentList,players);
//        }catch (IOException ioe){
//            System.out.println("Could not read Season");
//        }
//        return s;
//    }

    // Todo needs fixing for V3
    public void addTournament(){
        String game = CurrentGame.getText();
        String seasonTitle = CurrentSeason.getText();
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        ArrayList<String> tournaments = DB.getTournaments(seasonID);
        try {
            JSONObject tourney = (JSONObject) new JSONParser().parse(TournamentText.getText());
            JSONObject tournament = (JSONObject) tourney.get("tournament");
            String tournamentName = (String) tournament.get("name");


            if(!TournamentText.getText().isEmpty() && !tournaments.contains(tournamentName)){
                TournamentReader TR = new TournamentReader(TournamentText.getText());
                TR.addTournament(seasonID);
                DB.resetScores(seasonID);
                DB.updateScores(currentMethod,seasonID);
            }

        }catch (ParseException pe){}
        updateTopTen();
        updateTournamentList();
        fillPlayerBox();
        TournamentText.clear();
    }

    // Todo needs to be redone for V3
    public void removeTournament(){
//        Season s = getSeason(CurrentGame.getText(),CurrentSeason.getText());
//        if(RemoveTournament.getValue() != null){
//            s.tournaments.remove(RemoveTournament.getValue());
//            s.writeSeason(CurrentGame.getText());
//        }
//        updateTournamentList();
    }

    // Todo needs to be redone for V3
    public void alterPlayer(){
//        Season s = getSeason(CurrentGame.getText(),CurrentSeason.getText());
//        for (Player p:s.players) {
//            if(p.tag.equals(SelectPlayer.getValue())){
//                ArrayList<String> newCharacterList = new ArrayList<>();
//                if((FirstCharacter.getValue() != null) || (!FirstCharacter.getValue().equals("<Clear>"))){
//                    newCharacterList.add(FirstCharacter.getValue());
//                    System.out.println(FirstCharacter.getValue());
//                }
//                if(((SecondCharacter.getValue() != null) || (!FirstCharacter.getValue().equals("<Clear>"))) && (!FirstCharacter.getValue().equals(SecondCharacter.getValue()))){
//                    newCharacterList.add(SecondCharacter.getValue());
//                    System.out.println(SecondCharacter.getValue());
//                }
//                if(((ThirdCharacter.getValue() != null) || (!FirstCharacter.getValue().equals("<Clear>"))) && (!SecondCharacter.getValue().equals(ThirdCharacter.getValue())) && (!FirstCharacter.getValue().equals(ThirdCharacter.getValue()))){
//                    newCharacterList.add(ThirdCharacter.getValue());
//                    System.out.println(ThirdCharacter.getValue());
//                }
//                newCharacterList.remove("<Clear>");
//                newCharacterList.remove(null);
//                newCharacterList.remove("First Character");
//                newCharacterList.remove("Second Character");
//                newCharacterList.remove("Third Character");
//                p.setCharacters(newCharacterList);
//                if(PlayerScore.getText() != null){
//                    p.setScore(Double.parseDouble(PlayerScore.getText()));
//                }
//                if(InitialScore.getText() != null){
//                    p.setInitialScore(Double.parseDouble(InitialScore.getText()));
//                }
//            }
//        }
//        s.writeSeason(CurrentGame.getText());
//        updateCharactersAndPlacings();
//        updateTopTen();
//        fillCharacterList();
    }

    // Todo needs to be redone for V3
    public void addPlayer(){
//        Season s = getSeason(CurrentGame.getText(),CurrentSeason.getText());
//        ArrayList<String> newCharacterList = new ArrayList<>();
//        if(FirstCharacter.getValue() != null){
//            newCharacterList.add(FirstCharacter.getValue());
//        }
//        if((SecondCharacter.getValue() != null) && (!FirstCharacter.getValue().equals(SecondCharacter.getValue()))){
//            newCharacterList.add(SecondCharacter.getValue());
//        }
//        if((ThirdCharacter.getValue() != null) && (!SecondCharacter.getValue().equals(ThirdCharacter.getValue())) && (!FirstCharacter.getValue().equals(ThirdCharacter.getValue()))){
//            newCharacterList.add(ThirdCharacter.getValue());
//        }
//        Player newPlayer;
//        if(InitialScore.getText() != null){
//            newPlayer = new Player(NewPlayerTag.getText(), Double.parseDouble(InitialScore.getText()), newCharacterList);
//        } else {
//            newPlayer = new Player(NewPlayerTag.getText(), 1600);
//        }
//        s.players.add(newPlayer);
//        s.writeSeason(CurrentGame.getText());
//        updateCharactersAndPlacings();
//        updateTopTen();
//        fillCharacterList();
    }

    public void fillPlayerBox(){
        SelectPlayer.getItems().clear();
        BasePlayer.getItems().clear();
        MergePlayer.getItems().clear();
        SelectPlayerStatistics.getItems().clear();
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        ArrayList<Player> players = DB.getPlayers(seasonID);
        ArrayList<String> playerTags = new ArrayList<>();
        for (Player p:players) {
            playerTags.add(p.tag);
        }
        Collections.sort(playerTags);
        SelectPlayer.getItems().addAll(playerTags);
        BasePlayer.getItems().addAll(playerTags);
        MergePlayer.getItems().addAll(playerTags);
        SelectPlayerStatistics.getItems().addAll(playerTags);
    }

    public void fillCharacterBoxes(){
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        ArrayList<Player> players = DB.getPlayers(seasonID);
        for (Player p:players) {
            if(p.tag.equals(SelectPlayer.getValue())){
                FirstCharacter.setValue("First Character");
                SecondCharacter.setValue("Second Character");
                ThirdCharacter.setValue("Third Character");
                if((p.getCharacters().size() > 0) && (!p.getCharacters().get(0).equals(""))){
                    FirstCharacter.setValue(p.getCharacters().get(0));
                }
                if(p.getCharacters().size() > 1){
                    SecondCharacter.setValue(p.getCharacters().get(1));
                }
                if(p.getCharacters().size() > 2){
                    ThirdCharacter.setValue(p.getCharacters().get(2));
                }
                PlayerScore.setText(String.valueOf(p.score));
                InitialScore.setText(String.valueOf(p.initialScore));
            }
        }
    }

    // Todo needs fixing for V3
    public void alterSettings(){
        try {
            FileReader fr = new FileReader("Data/Settings");
            BufferedReader br = new BufferedReader(fr);

            String Title = "";
            String DefaultSeason = "";
            String DefaultGame = "";
            String Kvalue = "";

            int lineCnt = 0;
            String line;
            while((line = br.readLine()) != null){
                lineCnt++;
                if(lineCnt == 1){
                    Title = line;
                }
                if(lineCnt == 2){
                    DefaultSeason = line;
                }
                if(lineCnt == 3){
                    DefaultGame = line;
                }
                if(lineCnt == 4){
                    Kvalue = line;
                }
            }
            br.close();
            fr.close();

            FileWriter fw = new FileWriter("Data/Settings");
            BufferedWriter bw = new BufferedWriter(fw);

            if(!ChangeTitle.getText().equals("")){
                bw.write(ChangeTitle.getText());
            }else {
                bw.write(Title);
            }
            bw.newLine();
            if(DefaultSeasonBox.getValue() != null){
                bw.write(DefaultSeasonBox.getValue());
            }else {
                bw.write(DefaultSeason);
            }
            bw.newLine();
            if(DefaultGameBox.getValue() != null){
                bw.write(DefaultGameBox.getValue());
            }else {
                bw.write(DefaultGame);
            }
            bw.newLine();
            if(!KvalueSetting.getText().equals("") || KvalueSetting.getText() != null){
                bw.write(KvalueSetting.getText());
            } else {
                bw.write(Kvalue);
            }
            bw.close();
            fw.close();

            DefaultSeasonBox.setDisable(true);
            KvalueSetting.clear();
            ChangeTitle.clear();
            K = Integer.parseInt(Kvalue);
        }catch (IOException ioe){}
    }

    public void recalculateSeason(){
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        DB.resetScores(seasonID);
        DB.updateScores(currentMethod, seasonID);
        updateTopTen();
    }

//    // Todo needs fixing for V3
//    public void updatePlacings(){
//        Season s = getSeason(CurrentGame.getText(),CurrentSeason.getText());
//
//        String P1 = PlayerName1.getText();
//        Integer[] p1Placings = getPlayerPlacings(s,P1);
//        if(p1Placings[0] != 0){
//            FirstP1.setText(String.valueOf(p1Placings[0]));
//        }
//        if(p1Placings[1] != 0){
//            SecondP1.setText(String.valueOf(p1Placings[1]));
//        }
//        if(p1Placings[2] != 0){
//            ThirdP1.setText(String.valueOf(p1Placings[2]));
//        }
//
//        String P2 = PlayerName2.getText();
//        Integer[] p2Placings = getPlayerPlacings(s,P2);
//        if(p2Placings[0] != 0){
//            FirstP2.setText(String.valueOf(p2Placings[0]));
//        }
//        if(p2Placings[1] != 0){
//            SecondP2.setText(String.valueOf(p2Placings[1]));
//        }
//        if(p2Placings[2] != 0){
//            ThirdP2.setText(String.valueOf(p2Placings[2]));
//        }
//
//        String P3 = PlayerName3.getText();
//        Integer[] p3Placings = getPlayerPlacings(s,P3);
//        if(p3Placings[0] != 0){
//            FirstP3.setText(String.valueOf(p3Placings[0]));
//        }
//        if(p3Placings[1] != 0){
//            SecondP3.setText(String.valueOf(p3Placings[1]));
//        }
//        if(p3Placings[2] != 0){
//            ThirdP3.setText(String.valueOf(p3Placings[2]));
//        }
//
//        String P4 = PlayerName4.getText();
//        Integer[] p4Placings = getPlayerPlacings(s,P4);
//        if(p4Placings[0] != 0){
//            FirstP4.setText(String.valueOf(p4Placings[0]));
//        }
//        if(p4Placings[1] != 0){
//            SecondP4.setText(String.valueOf(p4Placings[1]));
//        }
//        if(p4Placings[2] != 0){
//            ThirdP4.setText(String.valueOf(p4Placings[2]));
//        }
//
//        String P5 = PlayerName5.getText();
//        Integer[] p5Placings = getPlayerPlacings(s,P5);
//        if(p5Placings[0] != 0){
//            FirstP5.setText(String.valueOf(p5Placings[0]));
//        }
//        if(p5Placings[1] != 0){
//            SecondP5.setText(String.valueOf(p5Placings[1]));
//        }
//        if(p5Placings[2] != 0){
//            ThirdP5.setText(String.valueOf(p5Placings[2]));
//        }
//
//        String P6 = PlayerName6.getText();
//        Integer[] p6Placings = getPlayerPlacings(s,P6);
//        if(p6Placings[0] != 0){
//            FirstP6.setText(String.valueOf(p6Placings[0]));
//        }
//        if(p6Placings[1] != 0){
//            SecondP6.setText(String.valueOf(p6Placings[1]));
//        }
//        if(p6Placings[2] != 0){
//            ThirdP6.setText(String.valueOf(p6Placings[2]));
//        }
//
//        String P7 = PlayerName7.getText();
//        Integer[] p7Placings = getPlayerPlacings(s,P7);
//        if(p7Placings[0] != 0){
//            FirstP7.setText(String.valueOf(p7Placings[0]));
//        }
//        if(p7Placings[1] != 0){
//            SecondP7.setText(String.valueOf(p7Placings[1]));
//        }
//        if(p7Placings[2] != 0){
//            ThirdP7.setText(String.valueOf(p7Placings[2]));
//        }
//
//        String P8 = PlayerName8.getText();
//        Integer[] p8Placings = getPlayerPlacings(s,P8);
//        if(p8Placings[0] != 0){
//            FirstP8.setText(String.valueOf(p8Placings[0]));
//        }
//        if(p8Placings[1] != 0){
//            SecondP8.setText(String.valueOf(p8Placings[1]));
//        }
//        if(p8Placings[2] != 0){
//            ThirdP8.setText(String.valueOf(p8Placings[2]));
//        }
//
//        String P9 = PlayerName9.getText();
//        Integer[] p9Placings = getPlayerPlacings(s,P9);
//        if(p9Placings[0] != 0){
//            FirstP9.setText(String.valueOf(p9Placings[0]));
//        }
//        if(p9Placings[1] != 0){
//            SecondP9.setText(String.valueOf(p9Placings[1]));
//        }
//        if(p9Placings[2] != 0){
//            ThirdP9.setText(String.valueOf(p9Placings[2]));
//        }
//
//        String P10 = PlayerName10.getText();
//        Integer[] p10Placings = getPlayerPlacings(s,P10);
//        if(p10Placings[0] != 0){
//            FirstP10.setText(String.valueOf(p10Placings[0]));
//        }
//        if(p10Placings[1] != 0){
//            SecondP10.setText(String.valueOf(p10Placings[1]));
//        }
//        if(p10Placings[2] != 0){
//            ThirdP10.setText(String.valueOf(p10Placings[2]));
//        }
//    }

//    // Todo needs fixing for V3
//    private Integer[] getPlayerPlacings(Season s, String playerTag){
//        Integer[] placings = new Integer[3];
//        placings[0] = 0;
//        placings[1] = 0;
//        placings[2] = 0;
//        for (String tournament:s.tournaments) {
//            try {
//                String fileLocation = "Data/" + CurrentGame.getText() + "/Tournaments/CSVFiles/" + tournament + ".csv";
//                FileReader fr = new FileReader(fileLocation);
//                BufferedReader br = new BufferedReader(fr);
//                int lineCnt = 0;
//                String line = "";
//                while((line = br.readLine()) != null){
//                    lineCnt++;
//                    if(lineCnt == 2){
//                        String[] top3 = line.split(",");
//                        if(playerTag.equals(top3[0])){
//                            placings[0] = placings[0] + 1;
//                        }
//                        if(playerTag.equals(top3[1])){
//                            placings[1] = placings[1] + 1;
//                        }
//                        if(playerTag.equals(top3[2])){
//                            placings[2] = placings[2] + 1;
//                        }
//                    }
//                }
//            }catch (IOException ioe){
//                System.out.println("Could not read Tournament");
//            }
//        }
//        return placings;
//    }

    public void updatePlayerStatistics(){
        String Player = SelectPlayerStatistics.getValue();
        Database DB = new Database();
        ResultSet thePlayer = DB.getPlayer2(Player, DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText()));
        Player player = new Player(thePlayer);
        // ToDo need to add set counts query in Database class
        HashMap<String,Integer[]> Sets = new HashMap<>();
        Set<String> opponents = Sets.keySet();
        ArrayList<String> SetStrings = new ArrayList<>();
        int wins = 0;
        int losses = 0;
        for (String opponent:opponents) {
            Integer[] setCount = Sets.get(opponent);
            String set = setCount[0] + " W " + setCount[1] + " L vs. " + opponent;
            SetStrings.add(set);
            wins = wins + setCount[0];
            losses = losses + setCount[1];
        }
        TourneysEntered.setVisible(true);
        TourneysEntered.setText("Tournaments Attended: " + String.valueOf(player.tournamentsEntered));
        CurrentRank.setVisible(true);
        CurrentRank.setText("Current Rank: " + String.valueOf(getPlayerRank(Player)));
        SetList.getItems().clear();
        SetList.getItems().addAll(SetStrings);
        SetList.setVisible(true);
        WinLoss.setVisible(true);
        WinPercentage.setVisible(true);
        WinLoss.setText(wins + "W - " + losses + "L");
        double percentage = Math.round(((double) wins / (wins + losses)) * 100);
        if(wins == 0 && losses == 0){
            WinPercentage.setText("-");
        } else {
            WinPercentage.setText(String.valueOf(percentage) + "%");
        }
    }

    // add other unusable tags
    public int getPlayerRank(String player){
        Database DB = new Database();
        int seasonID = DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText());
        ArrayList<Player> orderedPlayersOld = DB.getOrderedList(seasonID, minimumTE);;
        ArrayList<String> orderedPlayers = new ArrayList<>();
        int j = 0;
        for (Player p: orderedPlayersOld) {
            if(!p.tag.startsWith("Bye")){
                orderedPlayers.add(p.tag);
            }
            j++;
        }
        int i = 1;
        for (String p:orderedPlayers) {
            if(p.equals(player)){
                return i;
            }
            i++;
        }
        return 999;
    }

//    // Todo needs fixing for V3
//    private int tournamentsEntered(String player){
//        int numberOfTourneys = 0;
//        Season s = getSeason(CurrentGame.getText(), CurrentSeason.getText());
//        for (String tournament:s.tournaments) {
//            try {
//                String fileLocation = "Data/" + CurrentGame.getText() + "/Tournaments/CSVFiles/" + tournament + ".csv";
//                FileReader fr = new FileReader(fileLocation);
//                BufferedReader br = new BufferedReader(fr);
//                int lineCnt = 0;
//                String line;
//                boolean inTournament = false;
//                while((line = br.readLine()) != null){
//                    lineCnt++;
//                    if(lineCnt > 1){
//                        if(line.contains(player)){
//                            inTournament = true;
//                        }
//                    }
//                }
//                if(inTournament){
//                    numberOfTourneys++;
//                }
//            }catch (IOException ioe){
//                System.out.println("Couldn't read tournement: " + tournament);
//            }
//        }
//        return numberOfTourneys;
//    }

    // Todo needs to be added for V3
    public void combinePlayers(){
//        String basePlayer = BasePlayer.getValue();
//        String mergePlayer = MergePlayer.getValue();
//        Season s = getSeason(CurrentGame.getText(), CurrentSeason.getText());
//        s.combinePlayers(CurrentGame.getText(),basePlayer,mergePlayer);
//
//        updateTopTen();
//        updateCharactersAndPlacings();
//        fillPlayerBox();
    }

//    public void updateWinRates(){
//        if(PlayerName1.getText().equals("")){
//            P1WR.setText("");
//        } else {
//            updateWinRate(PlayerName1,P1WR);
//        }
//
//        if(PlayerName2.getText().equals("")){
//            P2WR.setText("");
//        } else {
//            updateWinRate(PlayerName2,P2WR);
//        }
//
//        if(PlayerName3.getText().equals("")){
//            P3WR.setText("");
//        } else {
//            updateWinRate(PlayerName3,P3WR);
//        }
//
//        if(PlayerName4.getText().equals("")){
//            P4WR.setText("");
//        } else {
//            updateWinRate(PlayerName4,P4WR);
//        }
//
//        if(PlayerName5.getText().equals("")){
//            P5WR.setText("");
//        } else {
//            updateWinRate(PlayerName5,P5WR);
//        }
//
//        if(PlayerName6.getText().equals("")){
//            P6WR.setText("");
//        } else {
//            updateWinRate(PlayerName6,P6WR);
//        }
//
//        if(PlayerName7.getText().equals("")){
//            P7WR.setText("");
//        } else {
//            updateWinRate(PlayerName7,P7WR);
//        }
//
//        if(PlayerName8.getText().equals("")){
//            P8WR.setText("");
//        } else {
//            updateWinRate(PlayerName8,P8WR);
//        }
//
//        if(PlayerName9.getText().equals("")){
//            P9WR.setText("");
//        } else {
//            updateWinRate(PlayerName9,P9WR);
//        }
//
//        if(PlayerName10.getText().equals("")){
//            P10WR.setText("");
//        } else {
//            updateWinRate(PlayerName10,P10WR);
//        }
//    }

    private void updateWinRate(Player player, Label WR){
        Database DB = new Database();
        ResultSet rst = DB.executeQuery("Select * from Matches where player1ID = " + player.playerID + " or player2ID = " + player.playerID);
        int wins = 0;
        int total = 0;
        try {
            while (rst.next()){
                total++;
                int player1ID = rst.getInt("player1ID");
                int p1wins = rst.getInt("player1Count");
                int p2wins = rst.getInt("player2Count");
                if(player1ID == player.playerID){
                    if(p1wins > p2wins){
                        wins++;
                    }
                } else {
                    if(p2wins > p1wins){
                        wins++;
                    }
                }
            }
        } catch (SQLException sqle){
            System.out.println("Couldn't get Matches for player " + player.playerID);
            sqle.printStackTrace();
        }
        double percentage = Math.round(((double) wins / (double) total) * 100);
        if(total == 0){
            WR.setText("-");
        } else {
            WR.setText(String.valueOf(percentage) + "%");
        }
    }

    // Todo needs fixing for V3
    private void updateScore(Label LabelScore, double score){
        if(score == 0.0){
            LabelScore.setVisible(false);
        } else {
            LabelScore.setVisible(true);
            int intScore = (int) score;
            LabelScore.setText(String.valueOf(intScore));
        }
    }

    // Todo needs fixing for V3
    public void update(){
        try {
            updateTopTen();
            Thread.sleep(20);
            fillCharacterList();
            Thread.sleep(20);
            fillPlayerBox();
            Thread.sleep(20);
            fillFullPR();
        }catch (InterruptedException IE){
            System.out.println("couldn't update Slowly");
        }
    }

    private void fillFullPR(){
        Database DB = new Database();
        ArrayList<Player> players = DB.getOrderedList(DB.getSeasonID(CurrentSeason.getText(), CurrentGame.getText()),minimumTE);
        ArrayList<String> tags = new ArrayList<>();
        for (Player P:players) {
            if(!P.tag.startsWith("Bye")){
                tags.add(P.tag);
            }
        }
        int i = 1;
        System.out.println(tags);
        String text = "";
        for (String tag:tags) {
            text = text + String.valueOf(i) + ": " + tag + System.lineSeparator();
            i++;
        }
        System.out.println(text);
        FullPRText.setText(text);
    }

    public void openTutorial(){
        StagesClass.tutorialStage.show();
    }

    // Todo needs to be added in Database class for V3
    public void deleteSeason(){
//        if(DeleteSeason.getValue() != null){
//            File deletedSeason = new File("Data/" + CurrentGame.getText() + "/Seasons/" + DeleteSeason.getValue());
//            if(deletedSeason.delete()){
//                System.out.println("Season Deleted");
//            } else {
//                System.out.println("Season not Deleted");
//            }
//        }
//        updateSeasonList();

    }
}
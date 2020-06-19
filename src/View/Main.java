package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import com.oracle.jrockit.jfr.client.FlightRecordingClient;
import com.sun.javafx.css.Stylesheet;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.audio.AudioPlayer;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static MyViewModel viewModel;
    public static MyModel model;
    public static MainMenuViewControl menuControl;
    public static MediaPlayer player;
    private static Scene mainMenuScene,helpScene,aboutScene,optionsScene;
    public Parent aboutFXML,alertFXML,helpFXML,loginFXML,mainMenuFXML,gameFXML,optionFXML,winningFXML;
    public Scene alertScene;
    public Scene loginScene;
    public Scene gameScene;
    public Scene winningScene;
    public static Stage mainMenuStage,alertStage,gameStage;
    public Stage winningStage;
    public static MediaPlayer generalMusic;




    @Override
    public void start(Stage primaryStage) throws Exception{
        //MainMenu window
        mainMenuStage = primaryStage;
        mainMenuStage.getIcons().add(new Image("/Images/gameIcon.png"));
        mainMenuStage.initStyle(StageStyle.DECORATED);
        mainMenuStage.setResizable(true);
        //in the end of this section, got to put login Scene as mainMenu set scene and show

        //Loading all FXMLs
        aboutFXML = FXMLLoader.load(getClass().getResource("../View/AboutView.fxml"));
        alertFXML = FXMLLoader.load(getClass().getResource("../View/AlertView.fxml"));
        helpFXML = FXMLLoader.load(getClass().getResource("../View/HelpView.fxml"));
        loginFXML = FXMLLoader.load(getClass().getResource("../View/LoginView.fxml"));
        mainMenuFXML= FXMLLoader.load(getClass().getResource("../View/MainMenuView.fxml"));
        gameFXML = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
        optionFXML= FXMLLoader.load(getClass().getResource("../View/OptionView.fxml"));
        winningFXML = FXMLLoader.load(getClass().getResource("../View/WinningView.fxml"));

        //Loading scenes
        aboutScene = new Scene(aboutFXML,800,600);
        alertScene = new Scene(alertFXML,500,250);
        helpScene = new Scene(helpFXML,800,484);
        loginScene = new Scene(loginFXML,600,400);
        gameScene = new Scene(gameFXML,1024,768);
        optionsScene = new Scene(optionFXML,800,600);
        winningScene = new Scene(winningFXML,600,400);

        //loading main menu control
        FXMLLoader tmp = new FXMLLoader(getClass().getResource("../View/MainMenuView.fxml"));
        tmp.load();
        menuControl = tmp.getController();
        mainMenuScene = new Scene(tmp.getRoot(),800,600);

        //Update model and view model information
        model = new MyModel();
        model.runMyServer();
        viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        //start music
        //File musicPath = new File("Resources/Music/menusMusic.mp3");
       // generalMusic = new MediaPlayer(new Media(musicPath.toURI().toString()));
        //generalMusic.play();


        mainMenuStage.setScene(loginScene);
        mainMenuStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }



    public static void WinningView() {

    }



    public static void backFromGame() {
    }

    public static void settings() throws IOException {

        // Loading first properties for the game f
        FXMLLoader optionsTMP = new FXMLLoader(Main.class.getResource("../View/OptionView.fxml"));
        optionsTMP.load();
        OptionsViewControl optionsControl = optionsTMP.getController();
        optionsControl.init(viewModel);
        optionsControl.initBasicProp();

        mainMenuStage.setScene(optionsScene);

    }

    public static void showMainScreen() { // back from option menu
        mainMenuStage.setScene(mainMenuScene); // this is not working yet!!
        //TODO: setCharacter in OptionsViewControl doesn't work

    }

    public static void help() { mainMenuStage.setScene(helpScene);
    }



    public static void play() {
    }

    public static void load() {
    }

    public static void loginDone(String userName) throws IOException {
        menuControl.setUser(userName);
        mainMenuStage.setScene(mainMenuScene);
        // Loading first properties for the game f
        FXMLLoader optionsTMP = new FXMLLoader(Main.class.getResource("../View/OptionView.fxml"));
        optionsTMP.load();
        OptionsViewControl optionsControl = optionsTMP.getController();
        optionsControl.init(viewModel);
        optionsControl.initBasicProp();


    }
    //alert handle
    public static void showAlert() { alertStage.show();  }
    public static void backFromAlert() {
        alertStage.hide();
        gameStage.show();
    }

    //about handle
    public static void about() { mainMenuStage.setScene(aboutScene);  }
    public static void backFromAbout() {  mainMenuStage.setScene(mainMenuScene);  }

    //Winning handle
    public static void backToMainMenu() {
    }
    public static void restartGame() {
    }

}




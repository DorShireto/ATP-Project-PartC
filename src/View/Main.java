package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import com.sun.javafx.css.Stylesheet;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.audio.AudioPlayer;

import java.io.IOException;

public class Main extends Application {
    public static MyViewModel viewModel;
    public static MyModel model;
    public static MainMenuViewControl menuControl;
    private static Scene mainMenuScene;
    public Parent aboutFXML,alertFXML,helpFXML,loginFXML,mainMenuFXML,gameFXML,optionFXML,winningFXML;
    public Scene aboutScene,alertScene,helpScene,loginScene,gameScene,optionsScene,winningScene;
    public static Stage mainMenuStage;
    public Stage gameStage;
    public Stage alertStage;
    public Stage winningStage;
    public static MediaPlayer player;






    @Override
    public void start(Stage primaryStage) throws Exception{
        //MainMenu window
        mainMenuStage = primaryStage;
        mainMenuStage.getIcons().add(new Image("/Images/gameIcon.png"));
        mainMenuStage.initStyle(StageStyle.DECORATED);
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
        helpScene = new Scene(helpFXML,800,600);
        loginScene = new Scene(loginFXML,800,600);
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

        mainMenuStage.setScene(loginScene);
        mainMenuStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }




    public static void showAlert() {

    }

    public static void WinningView() {

    }

    public static void showMainScreen() { // back from option menu

    }

    public static void backFromGame() {
    }

    public static void settings() {
    }

    public static void help() {
    }

    public static void about() {
    }

    public static void play() {
    }

    public static void load() {
    }

    public static void loingDone(String userName) throws IOException {
        menuControl.setUser(userName);
        mainMenuStage.setScene(mainMenuScene);
        // Loading first properties for the game f
        FXMLLoader optionsTMP = new FXMLLoader(Main.class.getResource("../View/OptionView.fxml"));
        optionsTMP.load();
        OptionsViewControl optionsControl = optionsTMP.getController();
        optionsControl.init(viewModel);
        optionsControl.initBasicProp();




    }

    public static void backToMainMenu() {
    }

    public static void restartGame() {
    }

    public static void backFromAlert() {
    }

    public static void backFromAbout() {}

}




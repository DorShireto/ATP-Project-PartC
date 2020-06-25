package View;

import Model.MazeCharacter;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
//import com.sun.javafx.css.Stylesheet;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//import sun.audio.AudioPlayer;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static MyViewModel viewModel;
    public static MyModel model;
    public static MainMenuViewControl menuControl;
    public static MediaPlayer player;
    private static Scene mainMenuScene,helpScene,aboutScene,optionsScene,alertScene,winningScene;
    private Scene loginScene;
    public Parent aboutFXML,alertFXML,helpFXML,loginFXML,mainMenuFXML,optionFXML,winningFXML,gameFXML;
    public static Stage mainMenuStage,alertStage,winningStage;
    public static MediaPlayer generalMusic,gameMusic,winningMusic;
    public static FXMLLoader winnerFxmlLoader;
    //public static StringProperty wall,backGround,characterImage;

    /////
    public static Stage gameStage;
    public static Scene gameScene;
    public static FXMLLoader gameFxmlLoader;
    /////



    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        //MainMenu window
        mainMenuStage = primaryStage;
        mainMenuStage.getIcons().add(new Image("/Images/gameIcon.png"));
        mainMenuStage.initStyle(StageStyle.DECORATED);
        //mainMenuStage.setResizable(true);
        //in the end of this section, got to put login Scene as mainMenu set scene and show

        //Loading all FXMLs
        aboutFXML = FXMLLoader.load(getClass().getResource("../View/AboutView.fxml"));
        alertFXML = FXMLLoader.load(getClass().getResource("../View/AlertView.fxml"));
        helpFXML = FXMLLoader.load(getClass().getResource("../View/HelpView.fxml"));
        loginFXML = FXMLLoader.load(getClass().getResource("../View/LoginView.fxml"));
        mainMenuFXML= FXMLLoader.load(getClass().getResource("../View/MainMenuView.fxml"));
        optionFXML= FXMLLoader.load(getClass().getResource("../View/OptionView.fxml"));
        winningFXML = FXMLLoader.load(getClass().getResource("../View/WinningView.fxml"));
        winnerFxmlLoader = new FXMLLoader((getClass().getResource("../View/WinningView.fxml")));
        winnerFxmlLoader.load();
        gameFxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
        gameFXML = gameFxmlLoader.load();


        //Loading scenes
        gameScene = new Scene(gameFXML,800,600);
        aboutScene = new Scene(aboutFXML,800,600);
        alertScene = new Scene(alertFXML,700,350);
        helpScene = new Scene(helpFXML,800,600);
        loginScene = new Scene(loginFXML,800,600);
        optionsScene = new Scene(optionFXML,800,600);
        winningScene = new Scene(winningFXML,600,400);

        //loading main menu control
        FXMLLoader tmp = new FXMLLoader(getClass().getResource("../View/MainMenuView.fxml"));
        tmp.load();
        menuControl = tmp.getController();
        mainMenuScene = new Scene(tmp.getRoot(),800,600);

        //Update model and view model information
        model = new MyModel();
        //model.runMyServer();
        viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //start music
        File musicPath = new File("Resources/Music/menusMusic.mp3");
        generalMusic = new MediaPlayer(new Media(musicPath.toURI().toString()));
        generalMusic.play();


        mainMenuStage.setScene(loginScene);
        mainMenuStage.setResizable(false);
        mainMenuStage.show();

    }




    public static void WinningView() {
        gameMusic.stop();
        File winningSongFile = new File("Resources/Music/winningSong.mp3");
        winningMusic = new MediaPlayer(new Media(winningSongFile.toURI().toString()));
        winningMusic.play();
        WinningViewControl winningControl = winnerFxmlLoader.getController();
        winningStage.initStyle(StageStyle.UNDECORATED);
        winningStage.setResizable(false);
        winningStage.show();

    }



    public static void backFromGame() {
        gameStage.hide();
        gameMusic.stop();
        generalMusic.play();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();

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

    }

    public static void help() { mainMenuStage.setScene(helpScene);
    }



    public static void play()  {

        Main.loadGameStage();

        MyViewController myViewController = gameFxmlLoader.getController();
        myViewController.init(viewModel,gameScene,gameStage);
        viewModel.addObserver(myViewController);
        myViewController.getSaveMaze().setDisable(true);
        myViewController.getSolveMaze().setDisable(true);

        Main.loadAlertStage();
        Main.loadWinningStage();
        Main.loadMusic();
    }



    public static void load() {
        FileChooser fileChooser = new FileChooser();
        File saveFile = fileChooser.showOpenDialog(null);
        try {
            saveFile.createNewFile();
        } catch (Exception e) {
            Main.showMainScreen();
            return;
        }
        if(!viewModel.model.loadMaze(saveFile)) {
            Main.showMainScreen();
            return;
        }

        Main.loadGameStage();

        MyViewController myViewController = gameFxmlLoader.getController();
        myViewController.init(viewModel,gameScene,gameStage);
        viewModel.addObserver(myViewController);
        myViewController.drawFirst();

        Main.loadAlertStage();
        Main.loadWinningStage();
        Main.loadMusic();
    }

    private static void loadMusic()  {
        //loading game music
        File gameMusicFile = new File("Resources/Music/gameMusic.mp3");
        gameMusic = new MediaPlayer(new Media(gameMusicFile.toURI().toString()));
        mainMenuStage.hide();
        gameStage.show();
        generalMusic.stop();
        gameMusic.play();
    }

    private static void loadWinningStage()  {
        //loading winning stage and scene
        //winningStage.getIcons().add(new Image("/Images/gameIcon.png"));
        winningStage = new Stage();
        winningStage.setScene(winningScene);
        //winningStage.initModality(Modality.WINDOW_MODAL);
        winningStage.initModality(Modality.APPLICATION_MODAL);
    }

    private static void loadAlertStage()  {
        alertStage = new Stage();
        alertStage.setScene(alertScene);
        alertStage.initModality(Modality.WINDOW_MODAL);

    }

    private static void loadGameStage()  {
        gameStage = new Stage();
        gameStage.getIcons().add(new Image("/Images/gameIcon.png"));
        gameStage.setScene(gameScene);

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
    public static void showAlert() {
        alertStage.setResizable(false);
        alertStage.show();
    }

    public static void backFromAlert() {
        alertStage.hide();
        gameStage.show();
    }

    //about handle
    public static void about() {  mainMenuStage.setScene(aboutScene);  }
    public static void backFromAbout() {  mainMenuStage.setScene(mainMenuScene);  }

    //Winning handle
    public static void backToMainMenu() {
        winningMusic.stop();
        generalMusic.play();
        viewModel.model.initForMain();
        gameStage.close();
        winningStage.hide();
        gameStage.hide();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }
    public static void restartGame()  {
        winningMusic.stop();
        winningStage.hide();
        gameStage.hide();
        mainMenuStage.hide();
        viewModel.model.init();
        MazeCharacter mazeCharacter = viewModel.model.getLoadedCharacter();
        viewModel.setCharacterImage(mazeCharacter.getCharacterName());
        play();
    }

    public static MyViewModel getViewModel(){return viewModel;}

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        Main.viewModel.model.stopAllCommunication();
    }

}




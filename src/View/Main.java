package View;

import Model.MazeCharacter;
import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import com.sun.javafx.css.Stylesheet;
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
import sun.audio.AudioPlayer;

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
        optionFXML= FXMLLoader.load(getClass().getResource("../View/OptionView.fxml"));
        winningFXML = FXMLLoader.load(getClass().getResource("../View/WinningView.fxml"));
        winnerFxmlLoader = new FXMLLoader((getClass().getResource("../View/WinningView.fxml")));
        winnerFxmlLoader.load();


////////////////////////
        gameFXML = FXMLLoader.load(getClass().getResource("../View/MyView.fxml"));
        gameFxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
        gameFxmlLoader.load();
        gameScene = new Scene(gameFXML,700,482);

// ////////////////////

        //Loading scenes
        aboutScene = new Scene(aboutFXML,800,600);
        alertScene = new Scene(alertFXML,500,250);
        helpScene = new Scene(helpFXML,800,484);
        loginScene = new Scene(loginFXML,600,400);
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
        //viewModel.addObserver(gameFxmlLoader.getController()); //TODO: Dor added, I think we need this? (still no change)
        //start music TODO
        //File musicPath = new File("Resources/Music/menusMusic.mp3");
        //generalMusic = new MediaPlayer(new Media(musicPath.toURI().toString()));
        //generalMusic.play();


        mainMenuStage.setScene(loginScene);
        mainMenuStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }



    public static void WinningView() {
        //gameMusic.stop();TODO
        File winningSongFile = new File("Resources/Music/winningSong.mp3");
        winningMusic = new MediaPlayer(new Media(winningSongFile.toURI().toString()));
        //winningMusic.play();TODO
        WinningViewControl winningControl = winnerFxmlLoader.getController();
        System.out.println("winningControl: "+winningControl);
        winningControl.updateBackground();
        winningStage.show();

    }



    public static void backFromGame() {
        gameStage.hide();
        //gameMusic.stop(); TODO
        //generalMusic.play(); TODO
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



    public static void play() throws IOException {
        //loading game stage and scene
        gameStage = new Stage();
        gameStage.getIcons().add(new Image("/Images/gameIcon.png"));
        gameStage.setScene(gameScene);


        MyViewController myViewController = gameFxmlLoader.getController();
        myViewController.init(viewModel,gameScene,gameStage);
        viewModel.addObserver(myViewController);

        //loading alert stage and scene
       // alertStage.getIcons().add(new Image("/Images/gameIcon.png"));
        alertStage = new Stage();
        alertStage.setScene(alertScene);
        alertStage.initModality(Modality.WINDOW_MODAL);

        //loading winning stage and scene
        //winningStage.getIcons().add(new Image("/Images/gameIcon.png"));
        winningStage = new Stage();
        winningStage.setScene(winningScene);
        winningStage.initModality(Modality.WINDOW_MODAL);

        //loading game music
        File gameMusicFile = new File("Resources/Music/gameMusic.mp3");
        gameMusic = new MediaPlayer(new Media(gameMusicFile.toURI().toString()));
        mainMenuStage.hide();
        gameStage.show();
        //generalMusic.stop();
        //gameMusic.play();

    }

    public static void playAgain() throws IOException {
    }

    public static void load() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File saveFile = fileChooser.showOpenDialog(null);
        saveFile.createNewFile(); // if file already exists will do nothing
        viewModel.model.loadMaze(saveFile);

        gameStage = new Stage();
        gameStage.getIcons().add(new Image("/Images/gameIcon.png"));
        gameStage.setScene(gameScene);

        MyViewController myViewController = gameFxmlLoader.getController();
        myViewController.init(viewModel,gameScene,gameStage);
        viewModel.addObserver(myViewController);
        myViewController.load();
        //TODO: DOR - CONTINUE FROM HERE
        alertStage = new Stage();
        alertStage.setScene(alertScene);
        alertStage.initModality(Modality.WINDOW_MODAL);

        //loading winning stage and scene
        //winningStage.getIcons().add(new Image("/Images/gameIcon.png"));
        winningStage = new Stage();
        winningStage.setScene(winningScene);
        winningStage.initModality(Modality.WINDOW_MODAL);

        //loading game music
        File gameMusicFile = new File("Resources/Music/gameMusic.mp3");
        gameMusic = new MediaPlayer(new Media(gameMusicFile.toURI().toString()));
        mainMenuStage.hide();
        gameStage.show();

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
    public static void about() {
        //AboutViewControl.aboutImage.setCache(true);
        mainMenuStage.setScene(aboutScene);  }
    public static void backFromAbout() {  mainMenuStage.setScene(mainMenuScene);  }

    //Winning handle
    public static void backToMainMenu() {
        //winningMusic.stop();
        //generalMusic.play();
        gameStage.close();
        winningStage.hide();
        gameStage.hide();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }
    public static void restartGame() throws IOException {
        winningMusic.stop();
        winningStage.hide();
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




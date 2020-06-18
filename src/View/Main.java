package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.audio.AudioPlayer;

public class Main extends Application {
    public static MyViewModel viewModel;
    public static MyModel model;
    public Parent aboutFXML,alertFXML,gameFXML,loginFXML,mainMenuFXML;



    public static void showAlert() {

    }

    public static void WinningView() {

    }

    public static void showMainScreen() {

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

    public static void loingDone(String userName) {
    }

    public static void backToMainMenu() {
    }

    public static void restartGame() {
    }

    public static void backFromAlert() {
    }

    public static void backFromAbout() {
    }




    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

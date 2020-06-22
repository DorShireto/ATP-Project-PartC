package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainMenuViewControl {

    @FXML
    private Label userName;
    @FXML
    private Button loadGameB,newGameB;


    public void setUser(String Name){
        this.userName.setText("Hello   "+Name);
        System.out.println("name is: "+Name);
    }


    public void settingButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.settings();

    }
    
    public void helpButtonClicked(ActionEvent actionEvent) {
        Main.help();

    }
    
    public void exitButtonClicked(ActionEvent actionEvent) {
        Main.viewModel.model.stopAllCommunication();
        Platform.exit();
        System.exit(0);
    }

    public void aboutButtonClicked(ActionEvent actionEvent) {
        Main.about();

    }

    public void newGameButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.play();
    }

    public void loadGameButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.load();
    }
}

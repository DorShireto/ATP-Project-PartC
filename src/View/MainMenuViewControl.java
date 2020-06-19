package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainMenuViewControl {
    @FXML
    private AnchorPane newLoadP;
    @FXML
    private Label userName;
    @FXML
    private Button loadGameB,newGameB;


    public void setUser(String Name){
        this.userName.setStyle("-fx-alignment: center");
        this.userName.setText(Name);
    }

    public void playButtonClicked(ActionEvent actionEvent) {
        boolean loadB = loadGameB.isVisible();
        boolean newB = newGameB.isVisible();

        loadGameB.setVisible(!loadB);
        newGameB.setVisible(!newB);
        newLoadP.setVisible(true);

    }

    public void settingButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.settings();
        newLoadP.setVisible(false);
    }
    
    public void helpButtonClicked(ActionEvent actionEvent) {
        Main.help();
        newLoadP.setVisible(false);
    }
    
    public void exitButtonClicked(ActionEvent actionEvent) {
        Main.viewModel.model.stopAllCommunication();
        Platform.exit();
        System.exit(0);
    }

    public void aboutButtonClicked(ActionEvent actionEvent) {
        Main.about();
        newLoadP.setVisible(false);
    }

    public void newGameButtonClicked(ActionEvent actionEvent) throws IOException {
        Main.play();
    }

    public void loadGameButtonClicked(ActionEvent actionEvent) {
        Main.load();
    }
}

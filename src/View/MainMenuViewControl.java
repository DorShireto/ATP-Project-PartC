package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainMenuViewControl {
    @FXML
    private AnchorPane newLoadP;
    @FXML
    private Label userName;


    public void setUser(String Name){
        this.userName.setStyle("-fx-alignment: center");
        this.userName.setText(Name);
    }

    public void playButtonClicked(ActionEvent actionEvent) {
        newLoadP.setVisible(true);
    }

    public void settingButtonClicked(ActionEvent actionEvent) {
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

    public void newGameButtonClicked(ActionEvent actionEvent) {
        Main.play();
    }

    public void loadGameButtonClicked(ActionEvent actionEvent) {
        Main.load();
    }
}

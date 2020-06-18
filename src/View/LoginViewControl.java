package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class LoginViewControl {
    @FXML
    private TextField userID;


    public void exit(ActionEvent actionEvent) {

        Main.viewModel.model.stopAllCommunication();
        Platform.exit();
        System.exit(0);
    }

    public void startButtonClicked(ActionEvent actionEvent) {
        String userName = userID.getText();
        Main.loingDone(userName);

    }
}

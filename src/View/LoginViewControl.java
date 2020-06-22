package View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class LoginViewControl {
    @FXML
    private TextField userID;



    public void exit(ActionEvent actionEvent) {

        Main.viewModel.model.stopAllCommunication();
        Platform.exit();
        System.exit(0);
    }

    public void startButtonClicked(ActionEvent actionEvent) throws IOException {
        String userName = userID.getText();
        Main.loginDone(userName);

    }
}

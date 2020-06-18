package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class WinningViewControl {

    @FXML
    private AnchorPane backG;

    public void updateBackground()
    {
        String usedChar = Main.model.player;
        if(usedChar.equals("Mulan")) {
            backG.setStyle("-fx-background-image: url(/Images/MulanW.jpg)");
        }

        else if(usedChar.equals("LiShang")) {
            backG.setStyle("-fx-background-image: url(/Images/LiShangW.png)");
        }
        else if(usedChar.equals("Mushu")) {
            backG.setStyle("-fx-background-image: url(/Images/MushuW.jpg)");
        }
        else// CHEIN PO
            backG.setStyle("-fx-background-image: url(/Images/cheinPoW.jpg)");
    }



    public void playAgainClicked(ActionEvent actionEvent) {
        //Main.musicPlayer.stop(); // TODO: this one is for the music!!!!!!!
        Main.restartGame();
    }

    public void backClicked(ActionEvent actionEvent) {
        //Main.musicPlayer.stop(); // TODO: this one is for the music!!!!!!!
        Main.backToMainMenu();
    }
}

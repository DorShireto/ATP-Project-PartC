package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WinningViewControl {

    public ImageView charWinningScreen;
    @FXML
    private AnchorPane backG;

    public void updateBackground() {
        String usedChar = Main.character.getCharacterName();
        if (usedChar.equals("Mulan")) {
            charWinningScreen.setImage(new Image("/Images/MulanW.jpg"));
            //backG.setStyle("-fx-background-image: url(/Images/MulanW.jpg)");
        } else if (usedChar.equals("LiShang")) {
            charWinningScreen.setImage(new Image("/Images/LiShangW.png"));
            //backG.setStyle("-fx-background-image: url(/Images/LiShangW.png)");
        } else if (usedChar.equals("Mushu")) {
            charWinningScreen.setImage(new Image("/Images/MushuW.jpg"));
            //backG.setStyle("-fx-background-image: url(/Images/MushuW.jpg)");
        } else{//cheinPoW
            charWinningScreen.setImage(new Image("/Images/cheinPoW.jpg"));
            //backG.setStyle("-fx-background-image: url(/Images/cheinPoW.jpg)");
        }
    }



    public void playAgainClicked(ActionEvent actionEvent) throws IOException {
        Main.restartGame();
    }

    public void backClicked(ActionEvent actionEvent) {
        Main.backToMainMenu();
    }
}

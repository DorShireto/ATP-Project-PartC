package View;

import javafx.event.ActionEvent;

public class WinningViewControl {
    public void playAgainClicked(ActionEvent actionEvent)  { Main.restartGame(); }
    public void backClicked(ActionEvent actionEvent) {
        Main.backToMainMenu();
    }
}

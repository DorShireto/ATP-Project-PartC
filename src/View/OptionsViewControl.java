package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;


public class OptionsViewControl {
    @FXML
    private Button DFS,BFS,BestFS,simpleM,myM,emptyM,backB;
    @FXML
    private ChoiceBox charPicker;
    private String solvingAlgorithm = null;
    private String generateAlgorithm = null;

    public void init(MyViewModel myViewModel)
    {

        if (myViewModel!=null){
            if (solvingAlgorithm != null){
                myViewModel.setSolvingAlgorithm(solvingAlgorithm);
            }
            if (generateAlgorithm != null){
                myViewModel.setGenerateAlgorithm(generateAlgorithm);
            }

        }
    }

    public void dfsA() {
        DFS.setStyle("-fx-background-image: url(/Images/redStone.png) no-repeat; ");
        BFS.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        BestFS.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        solvingAlgorithm = "DepthFirstSearch";
    }

    public void bfsA() {
        BFS.setStyle("-fx-background-image: url(/Images/redStone.png) no-repeat; ");
        DFS.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        BestFS.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        solvingAlgorithm = "BreadthFirstSearch";
    }

    public void bestA() {
        BestFS.setStyle("-fx-background-image: url(/Images/redStone.png) no-repeat; ");
        BFS.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        DFS.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        solvingAlgorithm = "BestFirstSearch";
    }

    public void simpleMazeC() {
        simpleM.setStyle("-fx-background-image: url(/Images/redStone.png) no-repeat; ");
        myM.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        emptyM.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        generateAlgorithm = "SimpleMazeGenerator";
    }

    public void myMazeC() {
        myM.setStyle("-fx-background-image: url(/Images/redStone.png) no-repeat; ");
        simpleM.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        emptyM.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        generateAlgorithm = "MyMazeGenerator";
    }

    public void emptyMazeC() {
        emptyM.setStyle("-fx-background-image: url(/Images/redStone.png) no-repeat; ");
        myM.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        simpleM.setStyle("-fx-background-image: url(/Images/stoneButton.png) no-repeat; ");
        generateAlgorithm = "EmptyMazeGenerator";
    }


    public void saveClicked() {
        if (solvingAlgorithm != null){
            Main.viewModel.setSolvingAlgorithm(solvingAlgorithm);
        }
        if (generateAlgorithm != null){
            Main.viewModel.setGenerateAlgorithm(generateAlgorithm);
        }

        String charecter = charPicker.getValue().toString();
        Main.viewModel.setCharacterImage(charecter);
        Main.showMainScreen();
    }

    public void initBasicProp(){ // called from Main when moving to Option window
        String algorithm = Configurations.getSolvingAlgorithm();
        switch (algorithm){
            case "DepthFirstSearch":
                dfsA();
                break;
            case "BreadthFirstSearch":
                bfsA();
                break;
            case "BestFirstSearch":
                bestA();
                break;
        }

        String generatAlgo = Configurations.getGenerateType();
        switch (generatAlgo){
            case "SimpleMazeGenerator":
                simpleMazeC();
                break;
            case "MyMazeGenerator":
                myMazeC();
                break;
            case "EmptyMazeGenerator":
                emptyMazeC();
                break;
        }
        //setCharacter();
    }

}

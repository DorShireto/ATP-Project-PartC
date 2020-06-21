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
        DFS.setStyle("-fx-background-color: #3434eb; ");
        BFS.setStyle("-fx-background-color: #a7a7a8; ");
        BestFS.setStyle("-fx-background-color: #a7a7a8; ");
        solvingAlgorithm = "DepthFirstSearch";
    }

    public void bfsA() {
        DFS.setStyle("-fx-background-color: #a7a7a8; ");
        BFS.setStyle("-fx-background-color: #3434eb; ");
        BestFS.setStyle("-fx-background-color: #a7a7a8; ");
        solvingAlgorithm = "BreadthFirstSearch";
    }

    public void bestA() {
        DFS.setStyle("-fx-background-color: #a7a7a8; ");
        BFS.setStyle("-fx-background-color: #a7a7a8; ");
        BestFS.setStyle("-fx-background-color: #3434eb; ");
        solvingAlgorithm = "BestFirstSearch";
    }

    public void simpleMazeC() {
        simpleM.setStyle("-fx-background-color: #3434eb; ");
        myM.setStyle("-fx-background-color: #a7a7a8; ");
        emptyM.setStyle("-fx-background-color: #a7a7a8; ");
        generateAlgorithm = "SimpleMazeGenerator";
    }

    public void myMazeC() {
        simpleM.setStyle("-fx-background-color: #a7a7a8; ");
        myM.setStyle("-fx-background-color: #3434eb; ");
        emptyM.setStyle("-fx-background-color: #a7a7a8; ");
        generateAlgorithm = "MyMazeGenerator";
    }

    public void emptyMazeC() {
        simpleM.setStyle("-fx-background-color: #a7a7a8; ");
        myM.setStyle("-fx-background-color: #a7a7a8; ");
        emptyM.setStyle("-fx-background-color: #3434eb; ");
        generateAlgorithm = "EmptyMazeGenerator";
    }


    public void saveClicked() {
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

package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.applet.Main;


public class GameViewControl implements Observer,IView {
    public MazeDisplayer mazeDisplayer;
    private MyViewModel viewModel;
    private Scene gameScene;
    private Stage mainStage;
    public Button backB;
    public Button saveMaze;
    public Button solveMaze;
    public Button generateMaze;
    public TextField mazeRowField;
    public TextField mazeColField;
    public Solution solution;
    public boolean needToSolve;
    int charXPos,charYPos;

    public void init(MyViewModel viewModel,Scene gameScene, Stage mainStage)
    {
        if(viewModel != null && gameScene != null && mainStage != null)
        {
            this.viewModel = viewModel;
            this.gameScene = gameScene;
            this.mainStage = mainStage;
            needToSolve = false;
            bindAll();
            mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){ // handle closing game
                @Override
                public void handle(WindowEvent e)
                {
                    viewModel.model.stopAllCommunication();
                    Platform.exit();
                    System.exit(0);
                }
            });

        }
    }

    private void bindAll()
    {

    }

    public void generateMaze(){}
    public void solveMaze(){}
    public void saveMaze(){}
    public void backButton(){}

    @Override
    public void displayMaze(Maze maze) {
        if(maze!=null) {
            int x, y;
            x = viewModel.getCharecterRowPos();
            y = viewModel.getCharecterColPos();
            mazeDisplayer.setCharecterPos(x, y);
            mazeDisplayer.setMaze(maze);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o == viewModel)
        {
            displayMaze(viewModel.getMaze());
            generateMaze.setDisable(false);
            solution = viewModel.getSolution();
            charXPos = viewModel.getCharecterRowPos();
            charYPos = viewModel.getCharecterColPos();
            endGame();


        }
    }

    private void endGame()
    {
        int goalX,goalY;
        goalX = mazeDisplayer.maze.getGoalPosition().getRowIndex();
        goalY = mazeDisplayer.maze.getGoalPosition().getRowIndex();
        if(charXPos == goalX && charYPos == goalY)
            Main.WinningView();//TODO:

    }
}

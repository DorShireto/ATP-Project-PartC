package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MyViewController implements Observer,IView {
    public MazeDisplayer mazeDisplayer;
    private MyViewModel viewModel;
    @FXML
    private AnchorPane gameWin;
    @FXML
    private Pane gamePane;
    @FXML
    private GridPane panel;
    @FXML
    private Stage mainStage;
    @FXML
    private Button backB,saveMaze,solveMaze;
    @FXML
    private TextField mazeRowField,mazeColField;
    public Button generateMaze;
    private Solution solution;
    private Scene gameScene;
    public boolean showSolution;
    int charXPos,charYPos;
    private Maze maze;

    /**
     * Initializing view model, scene and stage
     * Function will be called from menu after starting a new game or loading one
     * */
    public void init(MyViewModel viewModel,Scene gameScene, Stage mainStage)
    {
        if(viewModel != null && gameScene != null && mainStage != null)
        {
            this.viewModel = viewModel;
            this.gameScene = gameScene;
            this.mainStage = mainStage;
            showSolution = false;
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

    /**
     * Updating maze displayer with the current position of the player,
     * and updating the maze
     * */
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


    /**
     * This method is called whenever the observed object is changed.
     * observers notified of the change.
     * checking if player have gotten to the goal position
     * */
    @Override
    public void update(Observable o, Object arg) {
        if(o == viewModel)
        {
            mazeDisplayer.setCharecterPos(viewModel.getCharecterRowPos(), viewModel.getCharecterColPos());
            mazeDisplayer.setMaze(maze);
            displayMaze(viewModel.getMaze());
            generateMaze.setDisable(false);
            solution = viewModel.getSolution();
            charXPos = viewModel.getCharecterRowPos();
            charYPos = viewModel.getCharecterColPos();
            endGame();


        }
    }

    /**
     * Binding all needed properties together
     * */
    private void bindAll()
    {
        gamePane.prefHeightProperty().bind(gameWin.heightProperty());
        panel.layoutYProperty().bind((gameWin.heightProperty()/*.subtract(200.0)*/));
        gameWin.prefHeightProperty().bind(mainStage.heightProperty()/*.subtract(30)*/);
        gameWin.prefWidthProperty().bind(mainStage.widthProperty()/*.subtract(280)*/);
        System.out.println("char image from Maze dis before bind:" + mazeDisplayer.characterImageProperty().toString());
        System.out.println("char image from viewModel before bind:" + viewModel.imageString.toString());
        viewModel.imageString.bindBidirectional(mazeDisplayer.characterImageProperty());

        //mazeDisplayer.characterImageProperty().bind(viewModel.imageString);
        System.out.println("char image from Maze dis after bind:" + mazeDisplayer.characterImageProperty().toString());
        System.out.println("char image from viewModel after bind:" + viewModel.imageString.toString());

    }

    /**
     * On-Action function for Generate button
     * trying to read data from two Text fields
     * binding maze displayer properties with current game window properties
     * calling displayer function with the new generated maze
     * */

    public void generateMaze(){
        showSolution = false;
        int rows,cols;
        try{
            rows = Integer.parseInt(mazeRowField.getText());
            cols = Integer.parseInt(mazeColField.getText());
        }
        catch (Exception e)
        {
            Main.showAlert();
            return;
        }
        if(rows < 2 || cols <2)
        {
            Main.showAlert();
            return;
        }
        if(viewModel==null) viewModel=Main.getViewModel();
        viewModel.generate(rows,cols);
        mazeDisplayer.heightProperty().bind(gameWin.heightProperty());
        mazeDisplayer.widthProperty().bind(gameWin.widthProperty());
        this.maze = viewModel.getMaze();
        displayMaze(this.maze);
        solveMaze.setDisable(false);
        saveMaze.setDisable(false);
        mazeDisplayer.requestFocus();

    }

    /**
     * Help function - called from solveMaze after Solve button was clicked,
     * Function changing the current Boolean value of showSolution
     * */
    private void updateSolve()
    {
        if(!showSolution){
            showSolution = true;
        }
        else
        {
            showSolution = false;
            mazeDisplayer.draw();
        }
        return;
    }


    /**
     * On-Action function for Solve button
     * */
    public void solveMaze(){
        updateSolve();
        showSol();
        mazeDisplayer.requestFocus();
    }

    /**
     * Saving maze with current position as future start position
     * */

    public void saveMaze(){
        try{
            int currentX,currentY;
            currentX = charXPos;
            currentY = charYPos;
            Position newStartingPos = new Position(currentX,currentY);
            viewModel.getMaze().setStartPosition(newStartingPos);
            FileOutputStream fos = new FileOutputStream("/Resource/mulanGameSave.txt");
            byte[] mazeToByte = viewModel.getMaze().toByteArray();
            fos.write(mazeToByte);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * On-Action function for Back button
     * Stopping music and moving back to main screen
     * */
    public void backButton(ActionEvent e){
        //Main.player.stop(); TODO: unable when music is on!
        //mainStage.hide();
        //Main.checkUser(); // check login confirmed
        Main.backFromGame();
        Main.mainMenuStage.show();
    }

    /**
     * On-Key-Press function for the game
     * Calling move player function with the input
     * Updating "start position" to be the current position
     * By doing that, it will allow us to present the solution path from the current cell
     * */
    public void keyPadPress(javafx.scene.input.KeyEvent ke) { // sending to view model the wanted move
        viewModel.movePlayer(ke.getCode());
        // Pause listener, updating location
        ke.consume();
        Position currentPos = new Position(charXPos,charYPos);
        this.viewModel.getMaze().setStartPosition(currentPos);
        showSol();

    }

    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        //click have no meaning
        mazeDisplayer.requestFocus();

    }

    /**
     * Show on maze the solution from current location
     * */
    private void showSol()
    {
        if(showSolution) {
            viewModel.solve();
            mazeDisplayer.setSolution(this.solution);
        }
    }

    /**
     * Checking if player reached the goal position
     * If so: moving to winning screen
     * */
    private void endGame()
    { // checking if current location is at goal position
        int goalX,goalY;
        goalX = mazeDisplayer.maze.getGoalPosition().getRowIndex();
        goalY = mazeDisplayer.maze.getGoalPosition().getColumnIndex();
        if(charXPos == goalX && charYPos == goalY)
            Main.WinningView();

    }


}

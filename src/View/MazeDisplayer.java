package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.Socket;

import static View.Main.gameFxmlLoader;
import static java.awt.Color.*;

public class MazeDisplayer extends Canvas {

    public Maze maze;
    private Solution solution;
    private Position startPosition;
    private Position goalPosition;
    private int [][] arrMaze;
    private int charRowIndex,charColIndex;

    // Texture prop
    private StringProperty characterImage ;
    private StringProperty wall= new SimpleStringProperty("Images/wall.png");
    private StringProperty backGround = new SimpleStringProperty("Images/boardBG.jpeg");

    // Cells props
    double canvasHeight,canvasWidth,cellHeight,cellWidth;

    public MazeDisplayer(){
        widthProperty().addListener(e->draw());
        heightProperty().addListener(e->draw());
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0,0,canvasWidth,canvasHeight);
    }

    // General properties
    @Override
    public boolean isResizable() {
        return true;
    }
    @Override
    public double prefWidth(double height) {
        return getWidth();
    }
    @Override
    public double prefHeight(double width) {
        return getHeight();
    }

    public boolean setMaze(Maze maze)
    {
        if(maze != null){
            this.maze=maze;
            this.arrMaze = maze.getMaze();
            //drawChar();
            return true;
        }
        return false;
    }


    public void draw() {
        MyViewController myViewController = gameFxmlLoader.getController();
        if(maze != null && !myViewController.getSolveMaze().isDisabled())
        {
            canvasHeight = getHeight();
            canvasWidth = getWidth();
            int row = arrMaze.length;
            int col = arrMaze[0].length;
            cellHeight = canvasHeight/row;
            cellWidth = canvasWidth/col;

            try{
            Image boardBG = new Image((this.backGround.get()));
            Image wall = new Image((this.wall.get()));
            String charURL =Main.viewModel.getCharacterPicPath();
            Image characterImage = new Image(charURL);
            Image trophy = new Image("/Images/trophy.png");
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0,0,canvasWidth,canvasHeight);

            Image BGIcon = new Image("Images/corckBG.jpg");

            for (int i = 0; i < arrMaze.length; i++) {
            for (int j = 0; j < arrMaze[i].length; j++) {
                gc.drawImage(BGIcon, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                }
            }
            //Draw maze
                for (int i = 0; i < arrMaze.length; i++) {
                for (int j = 0; j < arrMaze[i].length; j++) {
                    if (arrMaze[i][j] == 1) {
                        gc.drawImage(wall, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }
                }
            }

                //Draw trophy at goal position
                gc.drawImage(trophy, maze.getGoalPosition().getColumnIndex() * cellWidth, maze.getGoalPosition().getRowIndex() * cellHeight, cellWidth, cellHeight);
                //Draw character
                gc.drawImage(characterImage, charColIndex * cellWidth, charRowIndex * cellHeight, cellWidth, cellHeight);
            }
            catch (Exception e) {
                System.out.println("reDraw failed");
            }
        }
    }


    public void redraw(){
        if(maze!=null)
        {
            canvasHeight = getHeight();
            canvasWidth = getWidth();
            int row = arrMaze.length;
            int col = arrMaze[0].length;
            cellHeight = canvasHeight/row;
            cellWidth = canvasWidth/col;

            try{
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0,0,canvasWidth,canvasHeight);
                gc.setFill(javafx.scene.paint.Color.TRANSPARENT);
            //Draw maze
                for (int i = 0; i < arrMaze.length; i++) {
                    for (int j = 0; j < arrMaze[i].length; j++) {
                            gc.fillRect( j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                    }
            }
            catch (Exception e) {
                System.out.println("reDraw failed");
            }
        }
    }




    public void setSolution(Solution solution) {
        if(solution!=null)
        {
            this.solution = solution;
            printSolution();
        }
    }
    private void printSolution() {
        canvasHeight = getHeight();
        canvasWidth = getWidth();
        cellHeight = canvasHeight/arrMaze.length;
        cellWidth = canvasWidth/arrMaze[0].length;
        Image characterImage = new Image(Main.getViewModel().getCharacterPicPath());
        Image trophy = new Image("/Images/trophy.png");
        GraphicsContext graphicsContext = getGraphicsContext2D();
        int pathSize = solution.getSolutionPath().size();
        for(int index = 0; index < pathSize; index++) {
            int rowIndex,colIndex;
            rowIndex = ((MazeState)solution.getSolutionPath().get(index)).getMyState().getRowIndex();
            colIndex = ((MazeState)solution.getSolutionPath().get(index)).getMyState().getColumnIndex();
            graphicsContext.drawImage(index < pathSize-1 ?characterImage:trophy ,colIndex*cellWidth,rowIndex*cellHeight,cellWidth,cellWidth);
        }
    }


    public void setCharecterPos(int x, int y) {
        this.charRowIndex = x;
        this.charColIndex = y;
    }

    public void Zoom() {
        setOnScroll(event -> {
            if (event.isControlDown()) {
                double change = event.getDeltaY();
                double zoomConst = 1.03;
                if (change < 0) {
                    zoomConst = 0.97;
                }

                setScaleY(getScaleY() * zoomConst);
                setScaleX(getScaleX() * zoomConst);
                event.consume();
            }
        });
    }
}

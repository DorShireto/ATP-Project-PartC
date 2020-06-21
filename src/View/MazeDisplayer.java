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

import static java.awt.Color.*;

public class MazeDisplayer extends Canvas {

    public Maze maze;
    //private Maze maze;

    private Solution solution;
    private Position startPosition;
    private Position goalPosition;
    private int [][] arrMaze;
    private int charRowIndex,charColIndex;//TODO: might need to be init

    // Texture prop
    private StringProperty wall ;
    private StringProperty backGround ;
    private StringProperty characterImage ;


    //wall,backGround,characterImage;


    // Cells props
    double canvasHeight,canvasWidth,cellHeight,cellWidth;



    public MazeDisplayer(){
        wall = new SimpleStringProperty("Images/flowerWall.jpeg");
        backGround = new SimpleStringProperty("Images/boardBG.jpeg");
        characterImage = new SimpleStringProperty("Images/MulanI.png");
        System.out.println("image at Maze Displayer const is " + characterImage);

        widthProperty().addListener(e->draw());
        heightProperty().addListener(e->draw());
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
            draw();
            return true;
        }
        return false;
    }
    //canvasHeight = getHeight();
    //canvasWidth = getWidth();
    //cellHeight = canvasHeight/arrMaze.length;
    //cellWidth = canvasWidth/arrMaze[0].length;

    public void draw()
    {
        if(maze!=null)
        {
            canvasHeight = getHeight();
            canvasWidth = getWidth();
            int row = arrMaze.length;
            int col = arrMaze[0].length;
            cellHeight = canvasHeight/row;
            cellWidth = canvasWidth/col;

            try{
                //Image boardBG = new Image((this.backGround.get()));
                Image wall = new Image((this.wall.get()));
                System.out.println("image at Maze draw is " + characterImage);
                String charURL =this.characterImage.get();
                Image characterImage = new Image(charURL);
                Image trophy = new Image("/Images/trophy.png");
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0,0,canvasWidth,canvasHeight);
                //gc.setFill();

                //Image BGIcon = new Image("Images/BGIcon.jpg");TODO
                Image BGIcon = new Image("Images/blackB.png");
                for (int i = 0; i < arrMaze.length; i++) {
                    for (int j = 0; j < arrMaze[i].length; j++) {
                        gc.setFill(javafx.scene.paint.Color.BLACK);//TODO:
                        //gc.fillRect(charRowIndex * cellWidth, charColIndex * cellHeight, cellWidth, cellHeight);
                        gc.fillRect( j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                       // gc.drawImageנ, j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                    }
                }

                //Draw back ground picture
                //gc.drawImage(boardBG,0,0,canvasWidth,canvasHeight);
                //Draw maze

                for (int i = 0; i < arrMaze.length; i++) {
                    for (int j = 0; j < arrMaze[i].length; j++) {
                        if (arrMaze[i][j] == 1) {
                            //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            gc.drawImage(wall, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                    }
                }

                //Draw trophy at goal position
                gc.drawImage(trophy, maze.getGoalPosition().getColumnIndex() * cellWidth, maze.getGoalPosition().getRowIndex() * cellHeight, cellWidth, cellHeight);
                //Draw character

                gc.setFill(javafx.scene.paint.Color.ORANGE);//TODO:
                System.out.println("Row Index: " + charRowIndex + " Col Index: " + charColIndex);
                gc.fillRect(cellWidth * charColIndex, charRowIndex * cellHeight, cellWidth, cellHeight);

                //gc.drawImage(COLOR, charRowIndex * cellWidth, charColIndex * cellHeight, cellWidth, cellHeight);
            }
            catch (Exception e) {
                e.printStackTrace();
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
        Image characterImage = new Image(characterImageProperty().get());
        Image trophy = new Image("/Images/trophy.png");
        GraphicsContext graphicsContext = getGraphicsContext2D();
        int pathSize = solution.getSolutionPath().size();
        for(int index = 0; index > pathSize; index++)
        {
            int rowIndex,colIndex;
            rowIndex = ((MazeState)solution.getSolutionPath().get(index)).getMyState().getRowIndex();
            colIndex = ((MazeState)solution.getSolutionPath().get(index)).getMyState().getColumnIndex();
            if(index != pathSize-1)
            {// charcarter not in the final position
                graphicsContext.drawImage(characterImage,rowIndex*cellWidth,colIndex*cellHeight,cellWidth,cellWidth);
            }
            else{ //Character at final position,
                graphicsContext.drawImage(trophy,rowIndex*cellWidth,colIndex*cellHeight,cellWidth,cellWidth);
            }
        }
    }
    public void setCharecterPos(int x, int y) {
        //System.out.println("char before change " + charRowIndex + " , " + charColIndex);
        this.charRowIndex = x;
        this.charColIndex = y;
        //System.out.println("char after change " + charRowIndex + " , " + charColIndex);

        draw();
    }
    public StringProperty characterImageProperty() {
        return characterImage;
    }


}

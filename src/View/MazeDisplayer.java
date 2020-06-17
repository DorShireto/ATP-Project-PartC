package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    public Maze maze;
    //private Maze maze;
    private Solution solution;
    private Position startPosition;
    private Position goalPosition;
    private int [][] arrMaze;



    public MazeDisplayer(){
        widthProperty().addListener(e->draw());
        heightProperty().addListener(e->draw());
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

    public void draw()
    {
        if(maze!=null)
        {
            double canvasHeight,canvasWidth,cellHeight,cellWidth;
            canvasHeight = getHeight();
            canvasWidth = getWidth();
            cellHeight = canvasHeight/arrMaze.length;
            cellWidth = canvasWidth/arrMaze[0].length;

            try{
                Image boardBG = new Image("/Images/boardBG.jpeg");
                Image wall = new Image("/Images/flowerWall.jpeg");
                Image characterImage = new Image("/Images/MulanI.png");
                Image trophy = new Image("/Images/trophy.png");
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0,0,canvasWidth,canvasHeight);

                //Draw back ground picture
                gc.drawImage(boardBG,0,0,canvasWidth,canvasHeight);

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
                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth, cellHeight);




            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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


    public void setCharecterPos(int x, int y) {
    }
}

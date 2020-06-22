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
    //private StringProperty backGround ;
    private StringProperty characterImage ;
    private int counter=0;
    private StringProperty wall= new SimpleStringProperty("Images/flowerWall.jpeg");
    private StringProperty backGround = new SimpleStringProperty("Images/boardBG.jpeg");

    //wall,backGround,characterImage;


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
            //draw(); TODO NEW
            //drawChar();
            return true;
        }
        return false;
    }



    public void draw()
    {
        System.out.println(counter);
        counter++;
        if(maze!=null)
        {
            canvasHeight = getHeight();
            System.out.println("canvasHeight = " + canvasHeight);
            canvasWidth = getWidth();
            System.out.println("canvasWidth = " + canvasWidth);
            int row = arrMaze.length;
            int col = arrMaze[0].length;
            cellHeight = canvasHeight/row;
            cellWidth = canvasWidth/col;

            try{
            //Image boardBG = new Image((this.backGround.get()));
            Image wall = new Image((this.wall.get()));
            String charURL =Main.viewModel.getCharacterPicPath();
            Image characterImage = new Image(charURL);
            Image trophy = new Image("/Images/trophy.png");
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0,0,canvasWidth,canvasHeight);
            //gc.setFill();
                System.out.println((Object) gc.getCanvas());

            //Image BGIcon = new Image("Images/BGIcon.jpg");TODO
            Image BGIcon = new Image("Images/blackB.png");
//            for (int i = 0; i < arrMaze.length; i++) {
//                for (int j = 0; j < arrMaze[i].length; j++) {
//                    gc.setFill(javafx.scene.paint.Color.BLACK);//TODO:
//                    //gc.fillRect(charRowIndex * cellWidth, charColIndex * cellHeight, cellWidth, cellHeight);
//
//                    gc.fillRect( j * cellWidth, i * cellHeight, cellWidth, cellHeight);
//                    // gc.drawImageנ, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
//
//                }
//            }
            //Draw back ground picture
            //gc.drawImage(boardBG,0,0,canvasWidth,canvasHeight);
            //Draw maze
            for (int i = 0; i < arrMaze.length; i++) {
                for (int j = 0; j < arrMaze[i].length; j++) {
                    if (arrMaze[i][j] == 1) {
                        //gc.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        gc.drawImage(wall, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }else{
                        gc.setFill(javafx.scene.paint.Color.TRANSPARENT);//TODO:
                        //gc.fillRect(charRowIndex * cellWidth, charColIndex * cellHeight, cellWidth, cellHeight);
                        gc.fillRect( j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }
                }
            }

                //Draw trophy at goal position
                gc.drawImage(trophy, maze.getGoalPosition().getColumnIndex() * cellWidth, maze.getGoalPosition().getRowIndex() * cellHeight, cellWidth, cellHeight);
                //Draw character

//                gc.setFill(javafx.scene.paint.Color.ORANGE);//TODO:

                System.out.println("Row Index: " + charRowIndex + " Col Index: " + charColIndex);
                System.out.println("cellHeight Index: " + cellHeight + " cellWidth Index: " + cellWidth);

                //gc.fillRect(cellWidth * charColIndex, charRowIndex * cellHeight, cellWidth, cellHeight);
                //drawChar();
                //gc.drawImage(characterImage,  charColIndex * cellHeight,charRowIndex * cellWidth, cellWidth, cellHeight);
                gc.drawImage(characterImage, charColIndex * cellWidth, charRowIndex * cellHeight, cellWidth, cellHeight);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void redraw(){
        System.out.println(counter);
        counter++;
        if(maze!=null)
        {
            canvasHeight = getHeight();
            System.out.println("canvasHeight = " + canvasHeight);
            canvasWidth = getWidth();
            System.out.println("canvasWidth = " + canvasWidth);
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
                e.printStackTrace();
            }
        }
    }




    public void setSolution(Solution solution) {
        System.out.println("sol; "+solution);
        if(solution!=null)
        {
            this.solution = solution;
            printSolution();
        }
    }
    private void printSolution() {
        System.out.println("printSolution");
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
//        System.out.println("char before change " + charRowIndex + " , " + charColIndex);
        this.charRowIndex = x;
        this.charColIndex = y;
//        System.out.println("char after change " + charRowIndex + " , " + charColIndex);

        //draw(); TODO NEW
        //drawChar();
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

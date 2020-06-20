package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.Configurations;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends Observable implements IModel {
    private MazeCharacter mainCharacter = new MazeCharacter();
    private Maze maze;
    private Solution mazeSolution;
    private boolean isAtTheEnd;
    private boolean isStartNewGame;
    private boolean isSolved;
    private Server serverMazeGenerator;
    private Server serverSolveMaze;


    private ExecutorService threadPool = Executors.newCachedThreadPool();

    /******* Constructor *******/
    public MyModel() {
        Configurations.runConfiguration();
        isAtTheEnd = false;
        isStartNewGame = false;
        serverSolveMaze = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        serverMazeGenerator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        serverMazeGenerator.start();
        serverSolveMaze.start();
    }

    @Override
    public void generateMaze(int row, int col) {
        /**
         * Dor: this section causes server to crush - trying to run a server that already runs
         * */
        //if (!isStartNewGame)
        //    isStartNewGame = true;
        //else {
        //    serverMazeGenerator.stop();
        //    serverMazeGenerator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        //}
        //serverMazeGenerator.start();
        if (isSolved) {
            serverSolveMaze.stop();
            serverSolveMaze = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
            isSolved = false;
        }
        try {
            Client clientMazeGenerator = new Client(InetAddress.getLocalHost(), 5400,
                    (inFromServer, outToServer) -> {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            int[] mazeDimensions = new int[]{row,col};
                            toServer.writeObject(mazeDimensions); //send maze dimensions to server
                            toServer.flush();
                            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                            byte[] decompressedMaze = new byte[mazeDimensions[0] * mazeDimensions[1]+12]; //allocating byte[] for the decompressed maze -
                            is.read(decompressedMaze); //Fill decompressedMaze with bytes
                            maze = new Maze(decompressedMaze);
                            toServer.close();
                            fromServer.close();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    });
            clientMazeGenerator.communicateWithServer();
            mainCharacter = new MazeCharacter(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex());
            isAtTheEnd = false;
            setChanged();
            notifyObservers("Maze");
        } catch(Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public void movePlayer(KeyCode movement) {
        boolean legitKey = false;
        int mainCharacterPositionRow = mainCharacter.getCharacterRow();
        int mainCharacterPositionCol = mainCharacter.getCharacterCol();
        switch(movement) {
            /***** UP *****/
            case UP:
            case W:
            case NUMPAD8:
                if (mainCharacterPositionRow-1 < 0)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("back");
                if (isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol))
                    mainCharacter.setCharacterRow(mainCharacterPositionRow-1);
                break;
            /***** DOWN *****/
            case DOWN:
            case X:
            case NUMPAD2:
                if (mainCharacterPositionRow+1 >= maze.getMaze().length)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("front");
                if (isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol))
                    mainCharacter.setCharacterRow(mainCharacterPositionRow+1);
                break;
            /***** LEFT *****/
            case LEFT:
            case A:
            case NUMPAD4:
                if (mainCharacterPositionCol-1 < 0)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("left");
                if (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol-1))
                    mainCharacter.setCharacterCol(mainCharacterPositionCol-1);
                break;
            /***** RIGHT *****/
            case RIGHT:
            case D:
            case NUMPAD6:
                if (mainCharacterPositionCol+1 >= maze.getMaze()[0].length)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("right");
                if (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol+1))
                    mainCharacter.setCharacterCol(mainCharacterPositionCol+1);
                break;
            /***** UP & LEFT *****/
            case Q:
            case NUMPAD7:
                if (mainCharacterPositionCol-1 < 0 || mainCharacterPositionRow-1 < 0)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("left");
                if (isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol-1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol-1) || isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol))) {
                    mainCharacter.setCharacterRow(mainCharacterPositionRow-1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol-1);
                }
                break;
            /***** UP & RIGHT *****/
            case E:
            case NUMPAD9:
                if (mainCharacterPositionCol+1 >= maze.getMaze()[0].length || mainCharacterPositionRow-1 < 0)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("right");
                if (isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol+1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol+1) || isNotWall(mainCharacterPositionRow-1, mainCharacterPositionCol))) {
                    mainCharacter.setCharacterRow(mainCharacterPositionRow-1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol+1);
                }
                break;
            /***** DOWN & LEFT *****/
            case Z:
            case NUMPAD1:
                if (mainCharacterPositionCol-1 < 0 || mainCharacterPositionRow+1 >= maze.getMaze().length)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("left");
                if (isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol-1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol-1) || isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol))) {
                    mainCharacter.setCharacterRow(mainCharacterPositionRow+1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol-1);
                }
                break;
            /***** DOWN & RIGHT *****/
            case C:
            case NUMPAD3:
                if (mainCharacterPositionCol+1 >= maze.getMaze()[0].length || mainCharacterPositionRow+1 >= maze.getMaze().length)
                    break;
                legitKey = true;
                mainCharacter.setCharacterDirection("right");
                if (isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol+1) && (isNotWall(mainCharacterPositionRow, mainCharacterPositionCol+1) || isNotWall(mainCharacterPositionRow+1, mainCharacterPositionCol))) {
                    mainCharacter.setCharacterRow(mainCharacterPositionRow+1);
                    mainCharacter.setCharacterCol(mainCharacterPositionCol+1);
                }
                break;

            default:
                break;

        }
        if (maze.getGoalPosition().equals(new Position(mainCharacter.getCharacterRow(),mainCharacter.getCharacterCol())))
            isAtTheEnd = true;
        if (legitKey) {
            setChanged();
            notifyObservers("Character");
        }

    }

    @Override
    public void solve() {
        if (!isSolved)
            isSolved = true;
        else {
            serverSolveMaze.stop();
            serverSolveMaze = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        }
        serverSolveMaze.start();
        try {
            Client clientSolveMaze = new Client(InetAddress.getLocalHost(), 5401,
                    (inFromServer, outToServer) -> {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            toServer.writeObject(maze);
                            toServer.flush();
                            mazeSolution = (Solution) fromServer.readObject();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    });
            clientSolveMaze.communicateWithServer();
            setChanged();
            notifyObservers("Solution");
        } catch(Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public void stopAllCommunication() {
        System.out.println("Close Model");
        serverMazeGenerator.stop();
        if (serverSolveMaze != null) {
            serverSolveMaze.stop();
        }
        threadPool.shutdown();
    }

    @Override
    public void saveOriginalMaze(File file, String name) {
        try {
            FileOutputStream fileWriter;
            fileWriter = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
            MazeCharacter mazeCharacter = new MazeCharacter(name, maze.getStartPosition().getRowIndex(), maze.getStartPosition().getColumnIndex());
            MazeDisplayState mazeDisplayState = new MazeDisplayState(maze, mazeCharacter);
            objectOutputStream.writeObject(mazeDisplayState);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveCurrentMaze(File file, String name) {
        try {
            FileOutputStream fileWriter;
            fileWriter = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileWriter);
            Maze currentMaze = getCurrentMaze();
            MazeCharacter mazeCharacter = new MazeCharacter(name, mainCharacter.getCharacterRow(), mainCharacter.getCharacterCol());
            MazeDisplayState mazeDisplayState = new MazeDisplayState(currentMaze, mazeCharacter);
            objectOutputStream.writeObject(mazeDisplayState);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileWriter.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private Maze getCurrentMaze() {
        try {
            return new Maze(maze);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void loadMaze(File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fin);
            MazeDisplayState loadedMazeDisplayState = (MazeDisplayState) oin.readObject();
            if (loadedMazeDisplayState != null) {
                maze = loadedMazeDisplayState.getMaze();
                mainCharacter = loadedMazeDisplayState.getMazeCharacter();
                isAtTheEnd = false;
                setChanged();
                notifyObservers("Maze Load");
            }
            fin.close();
            oin.close();
        } catch(IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Loaded Maze ERROR");
            alert.setHeaderText("Exception caught trying to load:\n"+file.getName());
            alert.setGraphic(null);
            alert.setContentText("Loaded file was not a saved maze!\nPlease load the right type of file");
            alert.show();
        }
    }

    private boolean isNotWall(int row, int col) {
        Position p =new  Position(row,col);
        return (p.equals(maze.getStartPosition()) || p.equals(maze.getGoalPosition()) || maze.getMaze()[row][col] == 1);
    }

    @Override
    public void setGenerateAlgorithm(String algorithm) {
        Configurations.setGenerateAlgorithm(algorithm);
    }

    @Override
    public void setSolvingAlgorithm(String algorithm) {
        Configurations.setSolvingAlgorithm(algorithm);
    }

    @Override
    public void setCharacterPosition(int charRowPositionI, int charColPositionI) {
        this.mainCharacter.setCharacterPosition(charRowPositionI,charColPositionI);
    }


    @Override
    public MazeCharacter getLoadedCharacter() {
        return mainCharacter;
    }

    @Override
    public void setCharacter(String name) {
        this.mainCharacter.setCharacterName(name);
    }


    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public int getCharRowPosition() {
        return mainCharacter.getCharacterRow();
    }

    @Override
    public int getCharColPosition() {
        return mainCharacter.getCharacterCol();
    }

    @Override
    public String getMainCharacterDirection() {
        return mainCharacter.getCharacterDirection();
    }

    @Override
    public Solution getSolution() {
        return mazeSolution;
    }

    @Override
    public void setObserver(Observer o) {

    }

    @Override
    public boolean isAtTheEnd() {
        return isAtTheEnd;
    }
}
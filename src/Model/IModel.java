package Model;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observer;

public interface IModel {

    void stopAllCommunication();

    Maze getMaze();

    int getCharRowPosition();

    int getCharColPosition();

    Solution getSolution();

    void setObserver(Observer o);

    void generateMaze(int rows, int cols);

    void solve();

    void setSolvingAlgorithm(String algorithm);

//    void setMaze(Maze maze);

    void movePlayer(KeyCode keyCode);

    void setGenerateAlgorithm(String algorithm);

    void setCharacterPosition(int charRowPositionI, int charColPositionI);

    String getMainCharacterDirection();

    boolean isAtTheEnd();

    void saveCurrentMaze(File file, String name);

    void saveOriginalMaze(File file, String name);

    void loadMaze(File file);

    MazeCharacter getLoadedCharacter();

    void setCharacter(String name, String url);

    void init();
}

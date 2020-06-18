package Model;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.util.Observer;

public interface IModel {
    void stopAllCommunication();

    int getCharRowPosition();

    Maze getMaze();

    int getCharColPosition();

    Solution getSolution();

    void setObserver(Observer o);

    void generateMaze(int rows, int cols);

    void solve();

    void setSolvingAlgorithm(String algorithm);

    void setMaze(Maze maze);

    void movePlayer(KeyCode keyCode);

    void setGenerateAlgorithm(String algorithm);

    void setCharcterPosition(int charRowPositionI, int charColPositionI);
}

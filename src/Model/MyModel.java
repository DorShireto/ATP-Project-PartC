package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    public String player;

    public void setCharacter(String mulan) {
    }

    public void runMyServer() {
    }

    @Override
    public void stopAllCommunication() {

    }

    @Override
    public int getCharRowPosition() {
        return 0;
    }

    @Override
    public Maze getMaze() {
        return null;
    }

    @Override
    public int getCharColPosition() {
        return 0;
    }

    @Override
    public Solution getSolution() {
        return null;
    }

    @Override
    public void setObserver(Observer o) {

    }

    @Override
    public void generateMaze(int rows, int cols) {

    }

    @Override
    public void solve() {

    }

    @Override
    public void setSolvingAlgorithm(String algorithm) {

    }

    @Override
    public void setMaze(Maze maze) {

    }

    @Override
    public void movePlayer(KeyCode keyCode) {

    }

    @Override
    public void setGenerateAlgorithm(String algorithm) {

    }

    @Override
    public void setCharcterPosition(int charRowPositionI, int charColPositionI) {

    }
}

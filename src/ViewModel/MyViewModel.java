package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    public IModel model;
    public StringProperty imageString;


    @Override
    public void update(Observable o, Object arg) {

    }

    public int getCharecterColPos() {
    }

    public int getCharecterRowPos() {
    }

    public Maze getMaze() {
    }

    public Solution getSolution() {
    }

    public void generate(int rows, int cols) {

    }

    public void solve() {
    }

    public void setSolution(Solution solution) {
    }

    public void movePlayer(KeyCode keyCode) {

    }
}

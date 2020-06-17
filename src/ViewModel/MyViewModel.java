package ViewModel;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
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
}

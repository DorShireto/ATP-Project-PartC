package ViewModel;

import Model.IModel;
import View.Main;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    public IModel model;
    private int charColPositionI,charRowPositionI;

    private Solution solution;

    //Using StringProperty since those filled are been observed
    public StringProperty imageString,charRowPosition,charColPosition;

    public MyViewModel(IModel model) {
        this.model = model;
        // init the StringProperty
        model.setObserver(this);
        this.imageString = new SimpleStringProperty("0");
        this.charRowPosition = new SimpleStringProperty("0");
        this.charColPosition = new SimpleStringProperty("0");

    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof IModel)
        {
            charRowPositionI = model.getCharRowPosition();
            charRowPosition.setValue(String.valueOf(charRowPositionI));
            charColPositionI = model.getCharColPosition();
            charColPosition.setValue(String.valueOf(charColPositionI));
            solution = model.getSolution();
            setChanged();
            notifyObservers();
        }


    }

    private void setMaze(Maze maze) { // created for the option of loading a maze from saved game
        this.model.setMaze(maze);
    }

    public int getCharecterColPos() { return charColPositionI;    }

    public int getCharecterRowPos() { return charRowPositionI;    }

    public Maze getMaze() { return this.model.getMaze();  }

    public Solution getSolution() { return this.solution;    }

    public void generate(int rows, int cols) { this.model.generateMaze(rows,cols);    }

    public void solve() { this.model.solve();    }

    public void setSolvingAlgorithm(String algorithm) { this.model.setSolvingAlgorithm(algorithm);  }

    public void setGenerateAlgorithm(String algorithm) {  this.model.setGenerateAlgorithm(algorithm);  }

    public void movePlayer(KeyCode keyCode) { this.model.movePlayer(keyCode);  }

    public void setCharacterImage(String characterImage) {  this.imageString.setValue(characterImage);  }

    public void updateCharacterPosition(Position position)
    {
        this.charRowPositionI = position.getRowIndex();
        this.charColPositionI = position.getColumnIndex();
        this.model.setCharcterPosition(charRowPositionI,charColPositionI);

    }



}

package ViewModel;

import Model.MyModel;
import algorithms.mazeGenerators.Maze;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by idanr on 18/06/2017.
 */
public class MyViewModel extends Observable implements Observer {

    MyModel model;
    public MyViewModel(MyModel model){
    this.model=model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            setChanged();
            notifyObservers();
        }
    }
    public void loadMaze(File chosen)throws Exception{ model.loadMaze(chosen);}
    public void generateMaze(int width, int height){
            model.generateMaze(width, height);
    }
    public Maze getMaze() {
        return model.getMaze();
    }
    public void stopservers(){model.stopServers();}
    public int getCharacterPositionRow() {
        return model.getCharacterPositionRow();
    }
    public int getCharacterPositionColumn() { return model.getCharacterPositionColumn(); }

}


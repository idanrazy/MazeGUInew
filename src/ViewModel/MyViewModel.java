package ViewModel;

import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by idanr on 18/06/2017.
 */
public class MyViewModel extends Observable implements Observer {

    private MyModel model;
    public MyViewModel(MyModel model){
    this.model=model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model && arg.equals("gen")) {
            setChanged();
            notifyObservers("gen");
        }
        if(o==model && arg.equals("solution")){
            setChanged();
            notifyObservers("solution");
        }
        if(o==model && arg.equals("keymove")){
            setChanged();
            notifyObservers("keymove");
        }
    }
    public void moveCharacter(KeyCode movement){ model.moveCharacter(movement); }
    public void loadMaze(File chosen)throws Exception{ model.loadMaze(chosen);}
    public void generateMaze(int width, int height){
            model.generateMaze(width, height);
    }
    public void solveMaze(){ model.SolveMaze();}
    public Maze getMaze() {
        return model.getMaze();
    }
    public void stopservers(){model.stopServers();}
    public int getCharacterPositionRow() {
        return model.getCharacterPositionRow();
    }
    public int getCharacterPositionColumn() { return model.getCharacterPositionColumn(); }
    public Solution getSlution(){return model.getSolution();}
    public void resetSolution(){model.resetSolution();}

}


package ViewModel;


import View.MazeDisplayer;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MyViewModel implements Initializable {

     @FXML
     MazeDisplayer mazeDisplay;
     SimpleMazeGenerator maze=new SimpleMazeGenerator();

    //not happen just when initilaize, also we can call to initilize when we need
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Maze m= maze.generate(5,5);
        mazeDisplay.setMazeData(m.getMaze());


    }

    public void start() {
        System.out.println("Hey!");
    }

    public void newGame() {





    }
}
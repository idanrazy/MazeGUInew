package ViewModel;


import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MyViewModel implements Initializable {

     @FXML
     MazeDisplayer mazeDisplay;
    MyMazeGenerator maze=new MyMazeGenerator();
    Maze m;

    //not happen just when initilaize, also we can call to initilize when we need
    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public void start() {

        Maze m= maze.generate(15,15);
        mazeDisplay.setMazeData(m.getMaze());
        

    }

    public void openFIle() {
        FileChooser fc = new FileChooser();
        fc.setTitle("open Maze File");
        fc.setInitialDirectory(new File("C:\\Users\\idanr\\Desktop\\src\\resources"));
        File chosen = fc.showOpenDialog(null);
        if(chosen!=null)
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(chosen.getAbsoluteFile()));
                byte[] mazearr =(byte[])in.readObject();
                m = new Maze(mazearr);
                mazeDisplay.setMazeData(m.getMaze());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

    }

    public void saveFile(){

         FileChooser fs = new FileChooser();
         fs.setTitle("save Maze file");
         fs.setInitialDirectory(new File("C:\\Users\\idanr\\Desktop\\src\\resources"));
         File chosen = fs.showSaveDialog(null);
         try {
             ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chosen.getAbsoluteFile()));
             out.writeObject(m);
         }
         catch (IOException e){
             e.printStackTrace();
         }


    }

}
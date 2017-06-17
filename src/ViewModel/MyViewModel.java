package ViewModel;


import View.MazeDisplayer;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MyViewModel implements Initializable {

     @FXML
     MazeDisplayer mazeDisplay;
     @FXML
     public javafx.scene.control.TextField text_col;
     @FXML
     public javafx.scene.control.TextField text_row;
     public Button start_but;
     public Button stop_but;
     public MenuItem prop;
    MyMazeGenerator maze=new MyMazeGenerator();
    Maze m;
    Properties myProp ;

    //not happen just when initilaize, also we can call to initilize when we need
    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    public void start() {

         m= maze.generate(Integer.parseInt(text_row.getText()),Integer.parseInt(text_col.getText()));
        mazeDisplay.setMazeData(m.getMaze());
        start_but.setDisable(true);

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
         fs.setInitialFileName("mynewMaze.dat");
         fs.setInitialDirectory(new File("C:\\Users\\idanr\\Desktop\\src\\resources"));
         File chosen = fs.showSaveDialog(null);
         try {
             if(chosen!=null) {

                 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chosen.getAbsoluteFile()));
                 System.out.println(m.toString());
                 out.writeObject(m.toByteArray());
                 out.flush();
                 System.out.println("1");

             }
         }
         catch (IOException e){
             e.printStackTrace();
         }


    }
    public void stop(){
        mazeDisplay.clear();
        start_but.setDisable(false);

    }

    public void exit(){
        Platform.exit();
        System.exit(0);
    }
    public void properties(ActionEvent event) throws Exception{
/*
        Stage stage = new Stage();
        Parent root;
        if(event.getSource()==prop){
        root = FXMLLoader.load(getClass().getResource("../View/Properties.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Properties");
        */
        myProp = new Properties();
        myProp.initialize();
        /*
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        }
        */
    }


}
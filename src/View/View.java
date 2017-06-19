package View;


import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

     @FXML
     MazeDisplayer mazeDisplay;
     @FXML
     public javafx.scene.control.TextField text_col;
     @FXML
     public javafx.scene.control.TextField text_row;
     public Button start_but;
     public Button stop_but;
     public MenuItem prop;


    public MyViewModel viewModel;
    public Properties p;
    Stage stage;


    //not happen just when initilaize, also we can call to initilize when we need
    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            mazeDisplay.setMazeData(viewModel.getMaze().getMaze());

        }
        if(o == p){
                
            }
        }


    public void setViewModel(MyViewModel myview){
        viewModel=myview;

    }
    public void setProperties(Properties myp){
        p=myp;
    }

    public void start() {

        viewModel.generateMaze(Integer.parseInt(text_row.getText()),Integer.parseInt(text_col.getText()));
        start_but.setDisable(true);

    }


    public void openFIle() {
        FileChooser fc = new FileChooser();
        fc.setTitle("open Maze File");
        fc.setInitialDirectory(new File("C:\\Users\\idanr\\Desktop\\src\\resources"));
        File chosen = fc.showOpenDialog(null);
        if(chosen!=null)
            try {
                viewModel.loadMaze(chosen);

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
                 System.out.println(chosen.getAbsoluteFile());
                 out.writeObject(viewModel.getMaze().toByteArray());
                 out.flush();

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
        viewModel.stopservers();
        System.exit(0);

    }
    public void properties(ActionEvent event) throws Exception{

        stage = new Stage();
        Parent root;
        if(event.getSource()==prop){
        FXMLLoader fxmlLoader = new FXMLLoader();
        root = fxmlLoader.load(getClass().getResource("../View/Properties.fxml").openStream());
        p=fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Properties");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        p.stage=stage;
        stage.showAndWait();
        }

    }




}
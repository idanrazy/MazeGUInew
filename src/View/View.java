package View;


import ViewModel.MyViewModel;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
     public Button solve_but;
     public MenuItem prop;


    public MyViewModel viewModel;
    public Properties p;
    Stage stage;


    //not happen just when initilaize, also we can call to initilize when we need
    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel && arg.equals("gen")) {
            mazeDisplay.setMazeData(viewModel.getMaze());
            mazeDisplay.setCharacterPosition(viewModel.getCharacterPositionRow(), viewModel.getCharacterPositionColumn());
            int characterPositionRow = viewModel.getCharacterPositionRow();
            int characterPositionColumn = viewModel.getCharacterPositionColumn();
            CharacterRow.set(characterPositionRow + "");
            CharacterColumn.set(characterPositionColumn + "");
            mazeDisplayed();
        }
        if(o==viewModel && arg.equals("solution")){
            Solution s = viewModel.getSlution();
            ArrayList<AState> arr = s.getSolutionPath();
            ArrayList<int[]> solcord = new ArrayList<int[]>();
            for(AState a : arr){
               solcord.add(parsingSoution(a));
            }
            mazeDisplay.setSolution(solcord);
            mazeDisplay.drawSolution();



        }
        if(o==viewModel && arg.equals("keymove")){
            mazeDisplay.setCharacterPosition(viewModel.getCharacterPositionRow(), viewModel.getCharacterPositionColumn());
            int characterPositionRow = viewModel.getCharacterPositionRow();
            int characterPositionColumn = viewModel.getCharacterPositionColumn();
            mazeDisplay.drawChar();
            if(viewModel.getSlution()!=null)
                mazeDisplay.drawSolution();
            CharacterRow.set(characterPositionRow + "");
            CharacterColumn.set(characterPositionColumn + "");

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
        solve_but.setDisable(false);


    }

    public void mazeDisplayed(){
        mazeDisplay.requestFocus();
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

    public String getCharacterRow() {
        return CharacterRow.get();
    }

    public StringProperty characterRowProperty() {
        return CharacterRow;
    }

    public void setCharacterRow(String characterRow) {
        this.CharacterRow.set(characterRow);
    }

    public String getCharacterColumn() {
        return CharacterColumn.get();
    }

    public StringProperty characterColumnProperty() {
        return CharacterColumn;
    }

    public void setCharacterColumn(String characterColumn) {
        this.CharacterColumn.set(characterColumn);
    }

    public void stop(){
        mazeDisplay.clear();
        start_but.setDisable(false);
        solve_but.setDisable(true);
        viewModel.resetSolution();

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
    public void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("this Application purpose is to generate Maze and let the User option to solve it.\n" +
                "the App create 2 kinds of Mazes : Simple maze based on randomized algorithm" +
                        " or My_maze based on Prim algorithm."+'\n'+
        "after generate stage the Apps can solve any generated mazes with 3 kinds off searching algorithm : DFS,BFS or Best."+'\n'+
                "the User only have to choose the size of the maze that he want to solve and than try his best.\n\n  This Apps Created by idan and linoy.");
        alert.showAndWait();
}
    public void help(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("instruction manual");
        alert.setContentText("In the main window ,choose the size of the maze by enter row and column number and then click Start.\nafter the Maze appears on the window you can try and solve it with the keyboard arrow." +
                "\nin any time you can choose the solve" +
                " button to get the solution of the maze, GOOD LUCK\n\n\n" +
                "You can change the maze generator algorithm and solver algorithm in Option Tab and notice the different");
        alert.showAndWait();

    }
    public void Solve(){
        viewModel.solveMaze();
    }


    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    //region String Property for Binding
    public StringProperty CharacterRow = new SimpleStringProperty();

    public StringProperty CharacterColumn = new SimpleStringProperty();


    public int[] parsingSoution(AState state){
        String t= state.toString();
        t=t.substring(1,t.length()-1);
        String[] mysol =t.split(",");
        int []a = new int[2];
        a[0]=Integer.parseInt( mysol[0]);
        a[1]= Integer.parseInt(mysol[1]);
        return a;
    }





}
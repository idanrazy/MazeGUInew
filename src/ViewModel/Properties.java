package ViewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by idanr on 17/06/2017.
 */
public class Properties implements EventHandler<Event> {

    @FXML
    public javafx.scene.control.TextField text_col;
    @FXML
    public javafx.scene.control.TextField text_row;
    @FXML
    public Button save;
    public Button cancel;
    public ChoiceBox solv;
    public ChoiceBox gen;
    ObservableList<String> solvlist = FXCollections.observableArrayList("DFS","BFS","BEST");
    ObservableList<String > genList = FXCollections.observableArrayList("Simple Maze","My maze");
    public Properties(){
    }
    @FXML
    public void initialize()  {

        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../View/Properties.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Properties");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    solv.setItems(solvlist);
    gen.setItems(genList);
     }

    @Override
    public void handle(Event event) {
    if(event.getSource()==save)
    {

    }
    if(event.getSource()==cancel){

    }
    }


}

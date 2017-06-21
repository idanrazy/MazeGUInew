package View;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;

/**
 * Created by idanr on 17/06/2017.
 */
public class Properties extends Observable {

    @FXML
    public javafx.scene.control.TextField text_thread;


    @FXML
    public javafx.scene.control.ChoiceBox gen_alg;
    public javafx.scene.control.ChoiceBox solv_alg;
    public Button save;
    public Button cancel;

    public int row ;
    public int col ;

    public Stage stage;


    public Properties(){
    }
    @FXML
    public void initialize()  {

     }


    public void handle(Event event) throws Exception {
        if(event.getSource()==save)
        {
        java.util.Properties prop = new java.util.Properties();
        InputStream in = new FileInputStream("config.properties");
        prop.load(in);
        if(gen_alg.getValue()==null||solv_alg.getValue()==null||text_thread.getText()==null){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("illegal argument");
        alert.setContentText("you didn't choose any arguments, pls choose again");
        alert.showAndWait();
        }
        else {
            if (gen_alg.getValue() != null)
                prop.setProperty("GenerateAlg", gen_alg.getValue().toString());
            if (solv_alg.getValue() != null)
                prop.setProperty("SearchAlg", solv_alg.getValue().toString());
            if (text_thread.getText() != null)
                prop.setProperty("MaxThread", text_thread.getText());
            OutputStream output = new FileOutputStream("config.properties");
            prop.store(output, null);
            stage.close();
            }
        }
        if(event.getSource()==cancel){
            stage.close();
        }
    }



}

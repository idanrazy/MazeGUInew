package View;

import javafx.event.Event;
import javafx.fxml.FXML;
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
    public javafx.scene.control.TextField text_col;
    @FXML
    public javafx.scene.control.TextField text_row;
    @FXML
    public javafx.scene.control.ChoiceBox gen_alg;
    public javafx.scene.control.ChoiceBox solv_alg;
    public Button save;
    public Button cancel;

    public int row;
    public int col;

    public Stage stage;


    public Properties(){
    }
    @FXML
    public void initialize()  {

     }


    public void handle(Event event) throws Exception {
    if(event.getSource()==save)
    {
        if(text_row.getText()!="")
            row = Integer.parseInt(text_row.getText());
        if(text_col.getText()!="")
            col = Integer.parseInt(text_col.getText());
        System.out.println(row);
        System.out.println(col);
        java.util.Properties prop = new java.util.Properties();
        InputStream in = new FileInputStream("config.properties");
        prop.load(in);
        if(gen_alg.getValue().toString()!="")
            prop.setProperty("GenerateAlg", gen_alg.getValue().toString());
        if(solv_alg.getValue().toString()!="")
            prop.setProperty("SearchAlg", solv_alg.getValue().toString());
        OutputStream output = new FileOutputStream("config.properties");
        prop.store(output,null);
        stage.close();
        setChanged();
        notifyObservers();


    }
    if(event.getSource()==cancel){
        stage.close();
    }
    }



}

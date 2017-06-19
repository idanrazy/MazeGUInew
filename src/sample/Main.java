package sample;

import Model.MyModel;
import View.Properties;
import View.View;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    MyModel model;

    @Override
    public void stop() throws Exception {
        model.stopServers();
        super.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        model = new MyModel();
        model.startServers();
        Properties p = new Properties();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);

        //--------------
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../View/MyView.fxml").openStream());
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        //---------------

        //--------------
        View view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        view.setProperties(p);
        p.addObserver(view);
        viewModel.addObserver(view);
        //--------------
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}

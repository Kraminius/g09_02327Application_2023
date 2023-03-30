package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.GUI.ViewHandler;
import org.example.filereading.InsertFile;

import java.sql.SQLException;

public class Main extends Application {


    public static void main(String[] args) throws SQLException {
        InsertFile file = new InsertFile();
        file.getData();
        DataHandler dataHandler = new DataHandler();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewHandler viewHandler = ViewHandler.get();
        viewHandler.setStage(stage);
        viewHandler.start();
    }
}
package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.GUI.ViewHandler;

public class Main extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewHandler viewHandler = ViewHandler.get();
        viewHandler.setStage(stage);
        viewHandler.start();
    }
}
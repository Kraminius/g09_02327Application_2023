package org.example.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.DataHandler;
import org.example.Main;

import java.util.ArrayList;

public class ViewHandler {
    private static ViewHandler viewHandler;
    private ViewController controller;
    private Stage stage;
    private DataHandler dataHandler;

    public static ViewHandler get(){
        if(viewHandler == null) viewHandler = new ViewHandler();
        return  viewHandler;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void start(){
        createWindow();
    }

    private void createWindow(){
        try{
            FXMLLoader fxmlLoader;
            fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource("view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1200, 800);
            stage.setScene(scene);
            stage.show();
            controller = fxmlLoader.getController();
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> keyPressed(key.getCode()));
        }catch(Exception e){
            System.out.println("Error");
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }

    }
    public void sendData(ArrayList<ArrayList<String>> toSend){
        //Send data to be transformed into SQL table.

    }
    public void changeAt(int x, int y, String changeTo, String table){//Should change at x and y into changeTo in table Table

    }
    public void sendError(String errorMessage){
        controller.setError(errorMessage);
    }
    public void showTable(ArrayList<ArrayList<String>> table, boolean[] isNullAble){
        controller.setNewTable(table, isNullAble);
    }
    public String[] getTableNames(){
        //Should return the different tables existing in the database
        return dataHandler.getTableNames();
    }
    public String[] getKeys(String name){
        //Should return the primary keys of a given table
        return dataHandler.getKeys(name);
    }

    public ArrayList<ArrayList<String>> getTable(String table){
        //Should return the table with the parameter of a table name.

        return null;
    }
    public void keyPressed(KeyCode key){
        if(key == KeyCode.ENTER){
            System.out.println("Enter Pressed");
            return;
        }
        if(key == KeyCode.ESCAPE){
            System.out.println("Escape Pressed");
            return;
        }
    }
    public void setDataHandler(DataHandler dh){
        dataHandler = dh;
    }
    public void execCommand(String command){
        dataHandler.execCommand(command);
    }

}

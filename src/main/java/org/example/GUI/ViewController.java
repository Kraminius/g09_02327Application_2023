package org.example.GUI;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.mariadb.jdbc.internal.com.read.resultset.SelectResultSet;

import javax.swing.text.View;
import java.util.ArrayList;

public class ViewController {
    @FXML
    Button searchButton;
    @FXML
    Tab tabs;
    @FXML
    Button executeButton;
    @FXML
    VBox content;
    @FXML
    HBox headers;
    @FXML
    AnchorPane anchor;
    @FXML
    ScrollPane scroll;
    @FXML
    TextField keyInput;
    @FXML
    ComboBox keyBox;
    @FXML
    ComboBox databaseBox;
    @FXML
    Label databaseName;
    @FXML
    CheckBox editableCheckBox;
    @FXML
    Button addButton;
    @FXML
    TextArea command;
    @FXML
    Label errorLabel;

    boolean editable = false;
    String currentTable = "";
    ArrayList<ArrayList<String>> current;
    ArrayList<ArrayList<String>> edited;
    ArrayList<ArrayList<TextArea>> fields = new ArrayList<>();
    boolean[] nullable;
    String[] tableNames;
    String[] keys;
    ArrayList<String> primaryKeys;

    @FXML
    void initialize(){
        editableCheckBox.setOnAction(e -> setEditable());
        addButton.setOnAction(e -> addRow());
        executeButton.setOnAction(e -> sendCommand());
        tabs.setOnSelectionChanged(e -> switchToSearch());
        databaseBox.setOnAction(e -> setDatabaseKeysBox());
        searchButton.setOnAction(e -> initSearch());
    }
    void switchToSearch(){
        setDatabaseNamesBox();
    }
    void initSearch(){

        String name, key, where;
        if(databaseBox.getValue() != null) name = databaseBox.getValue().toString();
        else name = "none";
        if(keyBox.getValue() != null) key = keyBox.getValue().toString();
        else key = "none";
        where = keyInput.getText();

        String command = "";
        if(!name.equals("none")){
            command+="SELECT ";
            if(key.equals("none")){
                command += "* FROM " + name;
                System.out.println(command);
                ViewHandler.get().execCommand(command);

            }
            else{
                if(where.equals("")){
                    command += key + " FROM " + name;
                    System.out.println(command);
                    ViewHandler.get().execCommand(command);

                }
                else{
                    command += "* FROM " + name + " WHERE " + key + " = " + "'" + where + "'";
                    System.out.println(command);
                    ViewHandler.get().execCommand(command);

                }
            }
        }
    }

    private void sendCommand(){
        ViewHandler.get().execCommand(command.getText());
    }
    private void setEditable(){
        editable = editableCheckBox.selectedProperty().get();
        fill();
    }
    public void setError(String text){
        errorLabel.setText(text);
    }
    private void test(){
        ArrayList<ArrayList<String>> testTable = new ArrayList<>();
        ArrayList<String> line = new ArrayList<>();
        line.add("Employee Number");
        line.add("Name              ");
        line.add("Other         ");
        line.add("Extra Thing    ");
        ArrayList<String> line2 = new ArrayList<>();
        line2.add("00000");
        line2.add("Miki Brøns Sejersten Løngaard Hansen");
        line2.add("IDK");
        line2.add("Can jump higher than a snail");
        testTable.add(line);
        testTable.add(line2);
        testTable.add(line2);
        testTable.add(line);
        boolean[] nullTest = new boolean[4];
        nullTest[0] = false;
        nullTest[1] = true;
        nullTest[2] = true;
        nullTest[3] = true;
        setNewTable(testTable, nullTest);
        fill();
    }
    void setDatabaseNamesBox(){ //Adds the values to the comboboxes of databses and keys
        tableNames = ViewHandler.get().getTableNames();
        if(tableNames != null){
            databaseBox.getItems().clear();

            for(int i = 0; i < tableNames.length; i++){
                databaseBox.getItems().add(tableNames[i]);
            }
            databaseBox.setValue(databaseBox.getItems().get(0));
        }
    }
    void setDatabaseKeysBox(){
        if(tableNames != null && databaseBox.getValue() != null){
            databaseName.setText(databaseBox.getValue().toString());
            keys = ViewHandler.get().getKeys(databaseName.getText());
            if(keys != null){
                keyBox.getItems().clear();
                keyBox.getItems().add("none");
                for(int i = 0; i < keys.length; i++){
                    keyBox.getItems().add(keys[i]);
                }
                keyBox.setValue(keyBox.getItems().get(0));
            }
        }
    }
    void addRow(){
        ArrayList<String> newLine = new ArrayList<>();
        for(int i = 0; i < edited.get(0).size(); i++){
            if(nullable[i]) newLine.add("null");
            else newLine.add("");

        }
        edited.add(newLine);
        editChecks();
        current = edited;
        fill();
    }
    ArrayList<ArrayList<String>> copyArrayArray(ArrayList<ArrayList<String>> a){
        //Copies the values from a into b
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        for(int i = 0; i < a.size(); i++){
            ArrayList<String> line = new ArrayList<>();
            for(int j = 0; j < a.get(i).size(); j++){
                line.add(a.get(i).get(j));
            }
            b.add(line);
        }
        return b;
    }
    public void setNewTable(ArrayList<ArrayList<String>> table, boolean[] canBeNull){
        current = table;
        nullable = canBeNull;
        fill();
    }
    public void fill(){
        ArrayList<ArrayList<String>> table = current;
        edited = copyArrayArray(table);

        headers.getChildren().clear();
        content.getChildren().clear();
        ArrayList<String> firstLine = table.get(0);
        int totalWidth = 0;
        int[] widths = new int[firstLine.size()];
        //Creates the boxes and text for the first line, along with getting the size of the different columns.
        for(int i = 0; i < firstLine.size(); i++){
            String text = firstLine.get(i);
            VBox element = new VBox();
            element.setPrefHeight(30);
            //int width = text.length()*10;
            int width = 120;
            element.setPrefWidth(width);
            totalWidth += width;
            widths[i] = width;
            Label label = new Label(" " + text);
            label.setStyle("-fx-text-fill: #eeeeee");
            element.setStyle("-fx-border-color: #cccccc; -fx-background-color: #1f9fce");
            element.setAlignment(Pos.CENTER_LEFT);
            element.getChildren().add(label);
            headers.getChildren().add(element);
        }

        fields.clear();
        int totalHeight = 30;
        for(int i = 1; i < table.size(); i++){
            HBox row = new HBox();
            int rowHeight = 0;
            ArrayList<TextArea> fieldLine = new ArrayList<>();
            for(int n = 0; n < table.get(i).size(); n++){
                String text = table.get(i).get(n);
                VBox element = new VBox();
                element.setPrefWidth(widths[n]);
                TextArea field = new TextArea(text);
                int x = n, y = i;
                field.setOnKeyTyped(e -> edit(x, y));
                fieldLine.add(field);
                field.setWrapText(true);
                field.setEditable(editable);
                if(i%2 == 0) field.setStyle("-fx-text-fill: #404040; -fx-background-color: #94efff");
                else field.setStyle("-fx-text-fill: #404040; -fx-background-color: #eeeeee");
                element.setStyle("-fx-border-color: #cccccc");
                element.setAlignment(Pos.CENTER_LEFT);
                element.getChildren().add(field);
                element.setPadding(new Insets(2));
                row.getChildren().add(element);
                int height = 30;
                int length = text.length()*10;
                element.setPrefHeight(height);
                height *= 1.5;

                if(height > rowHeight) rowHeight = height;
            }
            fields.add(fieldLine);
            row.setPrefHeight(rowHeight);
            totalHeight+=rowHeight+10;
            content.getChildren().add(row);
        }
        //Resizes most things, so they fit the size of the scrollbar.
        scroll.setPrefWidth(totalWidth+15);
        scroll.setTranslateX(2);//It centers and is now slightly off center, so moves it a bit to the right
        if(scroll.getHeight() < totalHeight*15) scroll.setPrefHeight(totalHeight);
        headers.setPrefWidth(totalWidth);
        content.setPrefWidth(totalWidth);
        content.setPrefHeight(totalHeight);
        anchor.setPrefWidth(totalWidth);
        anchor.setPrefHeight(totalHeight);
    }
    public void edit(int x, int y){
        //It should do a check here, to see if there will be an error when parsing. We must therefore save the datatypes of each column.
        edited.get(y).set(x, fields.get(y-1).get(x).getText()); //plus ones to the first one, because there are the headers.
        ViewHandler.get().changeAt(x, y-1, fields.get(y-1).get(x).getText(), currentTable);
        String table = currentTable;
        String key = edited.get(0).get(x); //First line at x position

        String primaryKey = "";
        String where = ""; //Primary key = where id of that line
        String command = "UPDATE " + table + " SET " + key + " WHERE " + primaryKey + "=" + "'" + where + "'";
        System.out.println(command);
        ViewHandler.get().execCommand(command);
        editChecks();
        current = edited;
    }
    void editChecks(){
        checkForNull();
    }
    void checkForNull(){
        for(int y = 0; y < edited.size(); y++){
            for(int x = 0; x < edited.get(y).size(); x++){
                if(edited.get(y).get(x).equals("") || edited.get(y).get(x).equals("CANNOT_BE_NULL")) edited.get(y).set(x, "null");
                if(edited.get(y).get(x).equals("null")) {
                    if(!nullable[x]) edited.get(y).set(x, "CANNOT_BE_NULL");
                }
            }
        }
    }

    private void sendData(){
        ViewHandler.get().sendData(current);
        //send current
    }
}

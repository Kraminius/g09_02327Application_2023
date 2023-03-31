package org.example.GUI;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.JDBCType;
import java.util.ArrayList;

public class ViewController {
    @FXML
    Button searchButton;
    @FXML
    Tab tabs;
    @FXML
    Tab commandTab;
    @FXML
    Tab queriesTab;
    @FXML
    Label qCommand;
    @FXML
    Button executeButton;
    @FXML
    Button qExec;
    @FXML
    ComboBox qComboBox;
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
    int currentTab = 0;

    boolean editable = false;
    String currentTable = "";
    ArrayList<ArrayList<String>> current;
    ArrayList<ArrayList<String>> edited;
    ArrayList<ArrayList<TextArea>> fields = new ArrayList<>();
    boolean[] nullable;
    String[] tableNames;
    String[] keys;
    String primaryKey;
    ArrayList<ArrayList<String>> primaryKeys;
    String lastSearchCommand;
    ArrayList<String> qCommands;
    ArrayList<String> qNames;


    @FXML
    void initialize(){
        editableCheckBox.setOnAction(e -> setEditable());
        addButton.setOnAction(e -> addRow());
        executeButton.setOnAction(e -> sendCommand());
        tabs.setOnSelectionChanged(e -> switchToSearch());
        commandTab.setOnSelectionChanged(e -> switchCommand());
        queriesTab.setOnSelectionChanged(e -> switchToQueries());
        databaseBox.setOnAction(e -> setDatabaseKeysBox());
        searchButton.setOnAction(e -> initSearch());
        qExec.setOnAction(e -> queryCommand(qComboBox.getValue().toString()));
        initQueryCommands();
        switchCommand();
    }
    void switchCommand(){
        editableCheckBox.setDisable(true);
        addButton.setDisable(true);
        editableCheckBox.setSelected(false);
        currentTab = 0;
    }
    void switchToSearch(){
        editableCheckBox.setDisable(false);
        addButton.setDisable(false);
        setDatabaseNamesBox();
        getPrimaryKeys();
        currentTab = 1;
    }
    void switchToQueries(){
        editableCheckBox.setDisable(true);
        addButton.setDisable(true);
        editableCheckBox.setSelected(false);
        currentTab = 2;
    }
    void getPrimaryKeys(){
        primaryKeys = ViewHandler.get().getPrimaryKeys();
    }
    void initQueryCommands(){
        qCommands = new ArrayList<>();
        qNames = new ArrayList<>();

        //Query for making a contact-list with first name, last name, telephone and mail.
        qNames.add("Contact List");
        qCommands.add( "SELECT First_Name, Last_Name, Telephone, Mail FROM Journalist LEFT JOIN EMAIL ON Journalist.EmployeeNumber = EMAIL.EmployeeNumber LEFT JOIN Telephone ON Journalist.EmployeeNumber = Telephone.EmployeeNumber WHERE Mail LIKE '%@tv3.dk'");

        //Show reporters whose footages were never broadcasted in the morning (before 12:00).
        qNames.add("No Broadcast < 1200");
        qCommands.add("SELECT DISTINCT j.First_Name, j.Last_Name, j.EmployeeNumber FROM Journalist j WHERE NOT EXISTS (SELECT 1 FROM Edition e WHERE j.EmployeeNumber = e.EmployeeNumber AND e.BroadcastTime < 1200)");

        //Show the top 10 hosts of editions that, overall, attracted the maximum number of views.
        qNames.add("Max Views Edition");
        qCommands.add("SELECT j.EmployeeNumber, j.First_Name, j.Last_Name, SUM(i.ViewCount) AS TotalViews FROM Journalist j JOIN Item i ON j.EmployeeNumber = i.EmployeeNumber GROUP BY j.EmployeeNumber ORDER BY TotalViews DESC LIMIT 10");

        //Identifying which topics, overall, attracted less views than the average:
        qNames.add("Underperforming Topics");
        qCommands.add("SELECT t.Title, SUM(i.ViewCount) AS TotalViews FROM Topic t JOIN Item i ON t.Title = i.TopicTitle GROUP BY t.Title HAVING TotalViews < (SELECT AVG(ViewSum) FROM (SELECT SUM(i.ViewCount) AS ViewSum FROM Item i GROUP BY i.TopicTitle ORDER BY i.ViewCount) AS AvgTable)");

        // Identifying journalists who were both curators and reporters, having shot at least a footage that was used for a news item on a topic they curated.
        qNames.add("Manager1");
        qCommands.add("SELECT DISTINCT j.First_Name, j.Last_Name FROM Journalist j JOIN Curator c ON j.EmployeeNumber = c.EmployeeNumber JOIN Footage f ON j.EmployeeNumber = f.EmployeeNumber JOIN FootageHandler fh ON f.Title = fh.Title AND f.PublishingDate = fh.PublishingDate JOIN Item i ON fh.ItemID = i.ItemID AND c.TopicTitle = i.TopicTitle");

        //Find the total length of footages for each journalist. (also those who didn't record any)
        qNames.add("Footage To Length");
        qCommands.add("SELECT j.First_Name, j.Last_Name, COALESCE(SUM(f.Length), 0) AS Total_Footage_Length FROM Journalist j LEFT JOIN Footage f ON j.EmployeeNumber = f.EmployeeNumber GROUP BY j.EmployeeNumber ORDER BY Total_Footage_Length DESC");

        //Show the top 5 journalists with the highest average number of views per item.
        qNames.add("Average Journalist ViewCount");
        qCommands.add("SELECT j.First_Name, j.Last_Name, AVG(i.ViewCount) AS Average_Views FROM Journalist j JOIN Item i ON j.EmployeeNumber = i.EmployeeNumber GROUP BY j.EmployeeNumber ORDER BY Average_Views;");

        //List all journalists who have not created any footages. (manager one hahah)
        qNames.add("null Footage Journalists");
        qCommands.add("SELECT j.First_Name, j.Last_Name FROM Journalist j LEFT JOIN Footage f ON j.EmployeeNumber = f.EmployeeNumber WHERE f.EmployeeNumber IS NULL;");

        //
        qNames.add("");
        qCommands.add("");

        //
        qNames.add("");
        qCommands.add("");

        //
        qNames.add("");
        qCommands.add("");

        //
        qNames.add("");
        qCommands.add("");

        //
        qNames.add("");
        qCommands.add("");

        qComboBox.getItems().add("none");
        for(int i = 0; i < qNames.size(); i++){
            qComboBox.getItems().add(qNames.get(i));
        }
        qComboBox.setValue(qComboBox.getItems().get(0));
    }
    void queryCommand(String query){
        if(query.equals("none")) {
            qCommand.setText("");
            return;
        }
        String command = "";
        for(int i = 0; i < qNames.size(); i++){
            if(qNames.get(i).equals(query)){
                command = qCommands.get(i);
                break;
            }
        }
        qCommand.setText(command);
        ViewHandler.get().execCommand(command);
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
        lastSearchCommand = command;
    }

    private void sendCommand(){
        ViewHandler.get().execCommand(command.getText());
    }
    private void setEditable(){
        editable = editableCheckBox.selectedProperty().get();
        try{
            fill();
        }catch(Exception e){
            System.out.println("No Table Selected");
        }

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
    void addRow() {
        int rowLenght = current.get(0).size();
        String emptyRow = "";
        String table = databaseBox.getValue().toString();
        ArrayList<String> tableType = ViewHandler.get().getColumnTypes(table);

        String emptyRowCommand = "";
        for (int i = 0; i < tableType.size(); i++) {
            if (i > 0) {
                emptyRow += ", ";
            }
            if (tableType.get(i).equals("VARCHAR")) {
                emptyRow += "'p'";
            }
            if (tableType.get(i).equals("DECIMAL")) {
                emptyRow += "0";
            }
        }

        emptyRowCommand = "INSERT INTO " + table + " VALUES (" + emptyRow + ")";
        System.out.println(emptyRowCommand);

        ViewHandler.get().execCommand(emptyRowCommand);


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
        primaryKey = table.get(0).get(0);
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
            label.setStyle("-fx-text-fill: #eeeeee; -fx-opacity: 0.8");
            if(currentTab == 1){
                for(int j = 0; j < primaryKeys.size(); j++){
                    if(primaryKeys.get(j).get(0).equals(text)){
                        label.setStyle(label.getStyle() + "; -fx-opacity: 1");
                    }
                }
            }
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
        getPrimaryKeys();
        String tempPrimaryKey = current.get(current.size()-1).get(0);
        String originalValue = current.get(y).get(x);

        //It should do a check here, to see if there will be an error when parsing. We must therefore save the datatypes of each column.
        edited.get(y).set(x, fields.get(y-1).get(x).getText()); //plus ones to the first one, because there are the headers.
        ViewHandler.get().changeAt(x, y-1, fields.get(y-1).get(x).getText(), currentTable);
        String table = databaseBox.getValue().toString();
        String key = current.get(0).get(x); //First line at x position
        String newText = fields.get(y-1).get(x).getText();

        ArrayList<String> primaryKeysAndValues = new ArrayList<>(); //Makes an array of the primary keys and their current values
        for(int i = 0; i < primaryKeys.size(); i++){
            if(primaryKeys.get(i).get(1).equals(table)){ //Only check the primary keys of the current table
                String primary = primaryKeys.get(i).get(0); //make a string of that primary key
                primaryKeysAndValues.add(primary); //add the primary key to the arrayList
                for(int j = 0; j < current.get(0).size(); j++){
                    if(current.get(0).get(j).equals(primary)){ //Search for the position of the primary key in the shown list.
                        String primaryValue = fields.get(y-1).get(j).getText(); //Get the current input at the line
                        if (newText.equalsIgnoreCase("") && x == j || newText.equalsIgnoreCase("null") && x==j) {
                            if(ViewHandler.get().getColumnTypes(table).equals("VARCHAR")) {
                                primaryValue = "change";
                            }
                            else if(ViewHandler.get().getColumnTypes(table).equals("DECIMAL")){
                                primaryValue = "0";
                            }
                            newText = primaryValue; // Update newText based on primary key check
                        }
                        primaryKeysAndValues.add(primaryValue); //Add that value
                        break;
                    }
                }
            }
        }
        table = convert(table);
        key = convert(key);
        newText = convert(newText);
        String command;
        if(newText.equalsIgnoreCase("null") || newText.equalsIgnoreCase("")){
            command = "UPDATE " + table + " SET " + key + " = " + "null" + " WHERE ";
        }
        else{
            command = "UPDATE " + table + " SET " + key + " = '" + newText + "' WHERE ";
        }

        for(int i = 0; i < primaryKeysAndValues.size(); ){
            if (i > 0) command += " AND ";
            command += convert(primaryKeysAndValues.get(i));
            command += " = '";
            if (primaryKeysAndValues.get(i).equals(key)) {
                command += convert(originalValue); // Use the original value in the WHERE clause
            } else {
                if (i+1 < primaryKeysAndValues.size()) {
                    command += convert(primaryKeysAndValues.get(i+1));
                } else {
                    command += "null";
                }
            }
            command += "'";
            i += 2;
        }

        // Check if newText is empty and set it to a default value based on the column type
        if (newText.isEmpty()) {
            if (ViewHandler.get().getColumnTypes(table).equals("VARCHAR")) {
                newText = "change";
            } else if (ViewHandler.get().getColumnTypes(table).equals("DECIMAL")) {
                newText = "0";
            }
        }

        command = command.replace(" = null", " = '" + newText + "'"); // Replace null with the default value
        command += ";";
        System.out.println(command);
        ViewHandler.get().execManipulation(command);
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
    String convert(String text){
        String[] textArr = text.split("'");
        String converted = "";
        for(int i = 0; i < textArr.length-1; i++){
            converted += textArr[i] + "\\'";
        }
        converted += textArr[textArr.length-1];
        return converted;
    }
    private void sendData(){
        ViewHandler.get().sendData(current);
        //send current
    }
}

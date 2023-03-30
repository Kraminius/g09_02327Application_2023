package org.example;

import org.example.GUI.ViewHandler;
import org.example.SQL.OnlineDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataHandler {
    OnlineDatabase database;
    String getKeysCommand = "SELECT COLUMN_NAME, TABLE_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE CONSTRAINT_SCHEMA = 'mikkelju_tv3database' AND CONSTRAINT_NAME = 'PRIMARY';";



    public DataHandler(){
        database = new OnlineDatabase();
        ViewHandler.get().setDataHandler(this);
    }
    public void execCommand(String command){
        ArrayList<ArrayList<String>> output = database.getTableWithCommand(command);
        if(output == null){
            ViewHandler.get().sendError("Command Failed");
        }
        else{
            ViewHandler.get().sendError("");
            boolean[] bools = new boolean[output.get(0).size()];
            ViewHandler.get().showTable(output, bools);
        }
    }
    public void manipulate(String command){
        database.manipulate(command);
    }
    public ArrayList<ArrayList<String>> getPrimaryKeys(){
        return database.getTableWithCommand(getKeysCommand);
    }
    public String[] getTableNames(){
        ArrayList<ArrayList<String>> names = database.getTableWithCommand("Show Tables");
        String[] nameArray = new String[names.size()-1];
        for(int i = 1; i < names.size()-1; i++){
            nameArray[i-1] = names.get(i).get(0);
        }
        return nameArray;
    }
    public String[] getKeys(String tableName){
        ArrayList<ArrayList<String>> keys = database.getTableWithCommand("SELECT * FROM " + tableName);
        String[] keyArray = new String[keys.get(0).size()];
        for(int i = 0; i < keyArray.length; i++){
            keyArray[i] = keys.get(0).get(i);
        }
        return keyArray;
    }
    public ArrayList<String> getColumnTypes(String tableName) {return database.tableType(tableName); }
}

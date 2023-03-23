package org.example;

import org.example.GUI.ViewHandler;
import org.example.SQL.OnlineDatabase;

import java.util.ArrayList;

public class DataHandler {
    OnlineDatabase database;



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
}

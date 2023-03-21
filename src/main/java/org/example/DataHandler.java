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

}

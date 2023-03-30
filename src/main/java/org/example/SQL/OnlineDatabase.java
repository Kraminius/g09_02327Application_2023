package org.example.SQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OnlineDatabase {

    /*
    For at forbinde:
        Hostname: mikkeljurs.dk
        User: mikkelju_ethicalorganharvesters
        Password: liversAreBest1

        Database: mikkelju_tv3database
        Port: 3306
     */

    String host = "mikkeljurs.dk";
    String port = "3306";
    String database = "mikkelju_tv3database";
    String cp = "utf8";
    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;
    String username = "mikkelju_ethicalorganharvesters";
    String password = "liversAreBest1";
    Connection dbConnection;
    Scanner scanner;


    public OnlineDatabase() {
        dbConnection = getConnection(url, username, password);
        scanner = new Scanner(System.in, "CP850");
    }
    public ArrayList<ArrayList<String>> getTableWithCommand(String command){
        try{
            ArrayList<ArrayList<String>> toReturn = new ArrayList<>();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            ArrayList<String> firstLine = new ArrayList<>();
            for(int i = 1; i <= columnCount; i++){
                firstLine.add(resultSetMetaData.getColumnName(i));
            }
            toReturn.add(firstLine);

            resultSet.beforeFirst();
            while (resultSet.next()){
                ArrayList<String> tableLines = new ArrayList<>();
                for(int i = 1; i <= columnCount; i++){
                    if(resultSet.getString(i) == null) tableLines.add("null");
                    else tableLines.add(resultSet.getString(i));
                }
                toReturn.add(tableLines);
            }
            return toReturn;
        }catch (Exception e){
            System.out.println("Command Failed");
            e.getCause();
            ArrayList<ArrayList<String>> failed = new ArrayList<>();
            ArrayList<String> firstLine = new ArrayList<>();
            ArrayList<String> secondLine = new ArrayList<>();
            ArrayList<String> thirdLine = new ArrayList<>();
            firstLine.add("Command Failed");
            secondLine.add(e.getMessage());
            thirdLine.add(e.getCause().toString());
            failed.add(firstLine);
            failed.add(secondLine);
            failed.add(thirdLine);
            return failed;
        }
    }

    public ArrayList<String> tableType(String table) {
        ArrayList<String> coloumnTypes = null;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData metaData = resultSet.getMetaData();
            coloumnTypes = new ArrayList<>();
            int columnLenght = metaData.getColumnCount();

            for (int i = 1; i <= columnLenght; i++) {
                String coloumnType = metaData.getColumnTypeName(i);
                coloumnTypes.add(coloumnType);
            }
        } catch (SQLException e) {
            System.out.println("Command Failed");
            e.getCause();
            ArrayList<String> failed = new ArrayList<>();
            failed.add("Command Failed");
            return failed;
        }
        return coloumnTypes;
    }
    public void manipulate(String command){
        try{
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
        }catch (Exception e){
            System.out.println("Command Failed");
            e.getCause();
        }
    }
    public Connection getConnection(String url, String username, String password){
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }catch (Exception e){
            System.out.println("Connection failed");
        }
        return null;
    }
}

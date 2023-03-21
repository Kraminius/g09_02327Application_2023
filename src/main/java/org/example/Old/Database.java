package org.example.Old;

import java.sql.*;
import java.util.Scanner;

public class Database {
    String host = "localhost";
    String port = "3306";
    String database = "UniversityDB";
    String cp = "utf8";
    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=" + cp;
    String username = "root";
    String password = "mypassword";
    Connection dbConnection;

    public Database(){
        dbConnection = getConnection(url, username, password);
    }
    public void manipulate(){
        try{
            String command = getCommand();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            for(int i = 1; i <= columnCount; i++){
                System.out.print(resultSetMetaData.getColumnName(i) + ";");
            }
            System.out.println();
            System.out.println("------");

            resultSet.beforeFirst();
            while (resultSet.next()){
                for(int i = 1; i <= columnCount; i++){
                    if(resultSet.getString(i) == null){
                        System.out.println("null");
                    }
                    else{
                        System.out.println(resultSet.getString(i));
                    }
                }
            }


        }catch (Exception e){
            System.out.println("Command Failed");
            e.getCause();
        }
    }
    public String getCommand(){
        Scanner scanner = new Scanner(System.in, "CP850");
        String command = scanner.nextLine();
        scanner.close();
        return command;
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


package org.example.filereading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Reader {
    public ArrayList<String[]> data;
    public Reader() {
        data = readCSV("src/main/Ressources/30032023updatetv3.csv", ";");
    }
    public ArrayList<String[]> readCSV(String filepath, String delimiter) {
        String[] data;
        String currentLine;
        ArrayList<String[]> colData = new ArrayList<>();

        try
        {
            FileReader read = new FileReader(filepath);
            BufferedReader bread = new BufferedReader(read);
            while((currentLine = bread.readLine()) != null)
            {
                data = currentLine.split(delimiter);
                colData.add(data);
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return colData;
    }
}

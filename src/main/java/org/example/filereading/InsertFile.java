package org.example.filereading;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class InsertFile {

    Reader reader;
    ArrayList<FootageAndReporter> footageAndReporters = new ArrayList<>();
    //FootageID;Title;PublishingDate;EmployeeNumber;Lenght;CPR;First_Name;Last_Name;Street_Name;Civic_Number;City;ZIP;Country
    public void getData() {
        reader = new Reader();
        footageAndReporters.clear();
        for(int i = 1; i < reader.data.size(); i++){
            String[] data = reader.data.get(i);
            for(int j = 0; j < data.length; j++){
                if(data[j].equals(" ")) data[j] = null;
            }
            try{
                if(data.length > 5){
                    FootageAndReporter fr = new FootageAndReporter(parseInt(data[0]) ,data[1], new SimpleDateFormat("yyyyMMdd").parse(data[2]), data[3] ,parseInt(data[4]), data[5], data[6], data[7], data[8], parseInt(data[9]), data[10], parseInt(data[11]), data[12]);
                    footageAndReporters.add(fr);
                }
                else {
                    FootageAndReporter fr = new FootageAndReporter(parseInt(data[0]), data[1], new SimpleDateFormat("yyyyMMdd").parse(data[2]), parseInt(data[4]));
                    footageAndReporters.add(fr);
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}

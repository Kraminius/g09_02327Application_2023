package org.example.filereading;

import org.example.DataHandler;
import org.example.GUI.ViewController;
import org.example.GUI.ViewHandler;

import java.util.ArrayList;

public class UPDATE {



    private ArrayList<FootageAndReporter> footageAndReporters;

    public UPDATE(ArrayList<FootageAndReporter> footageAndReporters){
        this.footageAndReporters = footageAndReporters;
    }
    public void updateDB(){
        for(int i = 0; i < footageAndReporters.size(); i++){
            updateFootageAndReporter(footageAndReporters.get(i));
        }
    }

    private void updateFootageAndReporter(FootageAndReporter fr){
        Journalist j = fr.getJournalist();
        Footage f = fr.getFootage();






        if(j != null){
            String checkCommand = "SELECT * FROM Journalist WHERE EmployeeNumber='" + j.getEmployeeNumber()+"')";

            //If this input exists, then:
            ArrayList<String> commands = new ArrayList<>();
            commands.add("UPDATE Journalist SET CPR=" + j.getCPR() + " WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET First_Name='" + j.getFirstName() + "' WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET Last_Name='" + j.getLastName() + "' WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET Street_Name='" + j.getStreetName() + "' WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET Civic_Number=" + j.getCivicNumber() + " WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET City='" + j.getCity() + "' WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET ZIP=" + j.getZIPCode() + " WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            commands.add("UPDATE Journalist SET Country='" + j.getCountry() + "' WHERE EmployeeNumber='" + j.getEmployeeNumber()+"'");
            for(int i = 0; i < commands.size(); i++){
                //System.out.println(commands.get(i));
                ViewHandler.get().execManipulation(commands.get(i));
            }
            String command = "INSERT INTO Journalist VALUES ('" + j.getEmployeeNumber() + "', " + j.getCPR() + ", '" + j.getFirstName() + "', '" + j.getLastName() + "', '" + j.getStreetName() + "', " + j.getCivicNumber() + ", '" + j.getCity() + "', " + j.getZIPCode()+ ", '" + j.getCountry() +"')";
            //System.out.println(command);
            ViewHandler.get().execManipulation(command);
        }
        if(f != null){
            //Footage
            String checkCommand = "SELECT * FROM Footage WHERE Title='" + f.getTitle() + "' AND PublishingDate=" + f.getDate() + " AND EmployeeNumber='" + f.getEmployeeNumber()+"'";
            //If this input exists, then:
            String command = "UPDATE Footage SET Length=" + f.getDuration() + " WHERE Title='" + f.getTitle() + "' AND PublishingDate=" + f.getDate() + " AND EmployeeNumber='" + f.getEmployeeNumber()+"'";
            //System.out.println(command);
            ViewHandler.get().execManipulation(command);

            String insertCommand = "INSERT INTO Footage VALUES ('" + f.getTitle() + "', " + f.getDate() + ", '" + f.getEmployeeNumber() + "', " + f.getDuration()+")";
            //System.out.println(insertCommand);
            ViewHandler.get().execManipulation(insertCommand);
        }

    }

    /*
    Journalist              Footage
    <EmployeeNumber          <Title
    CPR                     <PublishingDate
    First_Name              <EmployeeNumber
    Last_Name               Length
    Street_Name
    Civic_Number
    City
    ZIP



     */
}

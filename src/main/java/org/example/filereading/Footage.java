package org.example.filereading;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Footage {
    private final String title;
    private final Integer date;
    private final Integer duration;
    private final String employeeNumber;

    public Footage(String title, Integer date, Integer duration, String employeeNumber) {
        this.employeeNumber = employeeNumber;
        this.title = title;
        this.date = date;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }
    public Integer getDate() { return date; }
    public Integer getDuration() {
        return duration;
    }
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public String toString() {
        final String D = ";";
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        return getTitle() +D + date +D + getDuration();
    }

}
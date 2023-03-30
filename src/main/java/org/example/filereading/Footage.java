package org.example.filereading;

import java.text.SimpleDateFormat;
import java.util.Date;
public class Footage {
    private final String title;
    private final Date date;
    private final Integer duration;
    private final Integer footageID;

    public Footage(Integer footageID, String title, Date date, Integer duration) {
        this.footageID = footageID;
        this.title = title;
        this.date = date;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }
    public Date getDate() { return date; }
    public Integer getDuration() {
        return duration;
    }
    public Integer getFootageID() {
        return footageID;
    }

    @Override
    public String toString() {
        final String D = ";";
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        return getTitle() +D + dateFormatter.format(getDate()) +D + getDuration();
    }

}
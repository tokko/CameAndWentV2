package com.tokko.cameandwentv2.dbmodels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.joda.time.DateTime;

/**
 * Created by Andreas on 27/07/2016.
 */
@Table(name = "LogEntries")
public class LogEntry extends Model {
    @Column(name = "Date")
    public long date;
    @Column(name = "DateTime")
    public long dateTime;
    @Column(name = "location")
    public Location location;
    @Column(name = "Entered")
    public boolean entered;

    public LogEntry(DateTime dateTime) {
        date = dateTime.withMillisOfDay(0).getMillis();
        this.dateTime = dateTime.getMillis();
    }

    public LogEntry() {

    }

    public LogEntry(DateTime dt, boolean entered, Location location) {
        date = dt.withMillisOfDay(0).getMillis();
        this.dateTime = dt.getMillis();
        this.entered = entered;
        this.location = location;
    }
}

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
    @Column(name = "Location")
    public Location Location;
    @Column(name = "Entered")
    public boolean entered;

    public LogEntry(DateTime dateTime) {
        date = dateTime.withMillisOfDay(0).getMillis();
        this.dateTime = dateTime.getMillis();
    }

    public LogEntry() {

    }
}

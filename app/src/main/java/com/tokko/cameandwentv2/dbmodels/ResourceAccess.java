package com.tokko.cameandwentv2.dbmodels;

import com.activeandroid.query.Select;
import com.tokko.cameandwentv2.events.EventLogEntryCommited;
import com.tokko.cameandwentv2.events.OttoBus;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Andreas on 28/07/2016.
 */
@EBean
public class ResourceAccess {

    @Bean
    OttoBus bus;

    public List<LogEntry> getLogEntries() {
        return new Select().from(LogEntry.class)
                //.where("Date = ?", date)
                .orderBy("DateTime DESC").execute();
    }

    public void CommitLogEntry(DateTime dt, boolean entered, String locationName) {
        new LogEntry(dt, entered, (Location) new Select().from(Location.class).where("Name = ?", locationName).executeSingle()).save();
        bus.post(new EventLogEntryCommited(dt, entered));
    }
}

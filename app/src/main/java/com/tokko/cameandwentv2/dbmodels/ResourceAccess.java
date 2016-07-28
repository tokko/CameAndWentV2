package com.tokko.cameandwentv2.dbmodels;

import com.activeandroid.query.Select;
import com.tokko.cameandwentv2.events.EventLogEntryCommited;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.OttoBus;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Andreas on 28/07/2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ResourceAccess {

    @Bean
    OttoBus bus;

    public List<LogEntry> getLogEntries(DateTime date) {
        return new Select().from(LogEntry.class)
                //.where("Date = ?", date)
                .orderBy("DateTime ASC").execute();
    }

    public void CommitLogEntry(DateTime dt, boolean entered, String locationName) {
        new LogEntry(dt, entered, (Location) new Select().from(Location.class).where("Name = ?", locationName).executeSingle()).save();
        bus.post(new EventLogEntryCommited(dt, entered));
    }

    public LogEntry readLatestLogEntry() {
        return new Select().from(LogEntry.class).orderBy("date DESC").executeSingle();
    }

    public void deleteLogEntry(LogEntry logEntry) {
        logEntry.delete();
        bus.post(new EventLogEntryDeleted(logEntry.dateTime));
    }
}

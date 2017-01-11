package com.tokko.cameandwentv2.events;

import com.tokko.cameandwentv2.log.LogEntry;

/**
 * Created by andre on 10/01/2017.
 */

public class EventLogEntryDeleted {

    private LogEntry entry;

    public EventLogEntryDeleted(LogEntry entry) {
        this.entry = entry;
    }

    public LogEntry getEntry() {
        return entry;
    }

    public void setEntry(LogEntry entry) {
        this.entry = entry;
    }
}

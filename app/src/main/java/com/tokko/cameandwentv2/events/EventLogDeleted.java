package com.tokko.cameandwentv2.events;

import com.tokko.cameandwentv2.log.LogEntry;

/**
 * Created by andre on 10/01/2017.
 */

public class EventLogDeleted {

    private LogEntry entry;

    public EventLogDeleted(LogEntry entry) {
        this.entry = entry;
    }

    public LogEntry getEntry() {
        return entry;
    }

    public void setEntry(LogEntry entry) {
        this.entry = entry;
    }
}

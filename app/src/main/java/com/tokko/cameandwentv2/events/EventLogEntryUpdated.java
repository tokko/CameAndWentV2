package com.tokko.cameandwentv2.events;

import com.tokko.cameandwentv2.log.LogEntry;

public class EventLogEntryUpdated {

    private LogEntry entry;

    public EventLogEntryUpdated(LogEntry entry) {
        this.entry = entry;
    }

    public LogEntry getEntry() {
        return entry;
    }
}

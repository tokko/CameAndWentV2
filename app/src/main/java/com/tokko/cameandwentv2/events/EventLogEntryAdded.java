package com.tokko.cameandwentv2.events;

import com.tokko.cameandwentv2.log.LogEntry;

public class EventLogEntryAdded {
    LogEntry entry;

    public EventLogEntryAdded(LogEntry entry) {
        this.entry = entry;
    }

    public LogEntry getEntry() {
        return entry;
    }
}

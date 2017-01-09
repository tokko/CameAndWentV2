package com.tokko.cameandwentv2.log;

import java.util.ArrayList;
import java.util.List;

public class DurationEntry {

    private final long duration;
    private final long date;
    private final List<LogEntry> logEntries;

    public DurationEntry(long duration, long date, List<LogEntry> logEntries) {
        this.duration = duration;
        this.date = date;
        this.logEntries = logEntries;
    }

    public long getDuration() {
        return duration;
    }

    public long getDate() {
        return date;
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }
}

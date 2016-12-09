package com.tokko.cameandwentv2.log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "LogEntries")
public class LogEntry extends Model {
    @Column(name = "Time")
    public long time;
    @Column(name = "Entered")
    public boolean entered;

    public LogEntry() {
        super();
    }

    public LogEntry(long time, boolean entered) {
        this.time = time;
        this.entered = entered;
    }

    public long getTime() {
        return time;
    }

    public boolean isEntered() {
        return entered;
    }
}

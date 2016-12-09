package com.tokko.cameandwentv2.log;

public class LogEntry {
    private long time;
    private boolean entered;

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

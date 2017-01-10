package com.tokko.cameandwentv2.log;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DurationEntry {

    private final List<LogEntry> logEntries;

    public DurationEntry(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public long getDuration() {
        return sumDurations(logEntries);
    }

    public long getDate() {
        return logEntries.get(0).getDate();
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void addEntries(List<LogEntry> entries){
        logEntries.addAll(entries);
    }
    public void addEntry(LogEntry entry){
        logEntries.add(entry);
    }

    public long sumDurations(List<LogEntry> entries){
        return abs(entries.stream().map(x -> x.getTime() * (x.entered ? -1 : 1)).collect(Collectors.summingLong(Long::valueOf)));
    }

    private long abs(long l){
        return l < 0 ? -l : l;
    }
    public List<LogEntry> purgeDoubleToggles(List<LogEntry> entries){
        List<LogEntry> result = new ArrayList<>();
        List<LogEntry> sortedEntries = entries.stream().sorted((a, b) -> (int) (a.getTime() - b.getTime())).collect(Collectors.toList());
        if(sortedEntries.isEmpty()) return result;
        boolean state = sortedEntries.get(0).entered;
        result.add(sortedEntries.get(0));
        for (int i = 1; i < sortedEntries.size(); i++){
            LogEntry currentEntry = sortedEntries.get(i);
            boolean currentState = currentEntry.entered;
            if(state == currentState) continue;
            state = currentState;
            result.add(currentEntry);
        }
        return result;
    }
}

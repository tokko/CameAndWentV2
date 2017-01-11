package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.util.Log;

import com.tokko.cameandwentv2.dagger.DaggerDurationEntryComponent;
import com.tokko.cameandwentv2.dagger.DurationEntryComponent;
import com.tokko.cameandwentv2.utils.TimeUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;


public class DurationEntry {

    private  List<LogEntry> logEntries;

    @Inject
    public TimeUtils timeUtils;

    public DurationEntry(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
        DaggerDurationEntryComponent.builder().build().inject(this);
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

    public void addEntry(LogEntry entry){
        logEntries.add(entry);
    }

    public long sumDurations(List<LogEntry> entries){
        if(entries.size() == 1){
            return timeUtils.getCurrentTime() - entries.get(0).getTime();
        }
        long sum = 0;
        Optional<Long> maxTime = entries.stream().map(LogEntry::getTime).max((a, b) -> (int)(a - b));
        Optional<LogEntry> last = entries.stream().filter(x -> x.getTime() == maxTime.get()).findFirst();
        if(last.isPresent() && last.get().entered) {
            sum = timeUtils.getCurrentTime() - last.get().getTime();
            entries = entries.stream().limit(entries.size()-1).collect(Collectors.toList());
        }
        sum += abs(entries.stream().map(x -> x.getTime() * (x.entered ? 1 : -1)).collect(Collectors.summingLong(Long::valueOf)));
        return sum;
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

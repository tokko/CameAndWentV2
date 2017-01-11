package com.tokko.cameandwentv2.log;

import com.tokko.cameandwentv2.dagger.DaggerDurationEntryComponent;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.OttoBus;
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

    @Inject
    public OttoBus bus;

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

}

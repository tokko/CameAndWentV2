package com.tokko.cameandwentv2.log;

import android.util.Log;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

@EBean
public class DurationEntryBuilder {

    public List<DurationEntry> calculateDurations(List<LogEntry> entries){
        Map<Long, List<LogEntry>> groupByPriceMap =
                entries.stream().collect(Collectors.groupingBy(LogEntry::getDate));
    //Stream.of does not have groupingby collector?

        List<DurationEntry> durations = groupByPriceMap.keySet().stream().map(x -> {
            List<LogEntry> purgedIds = purgeDoubleToggles(groupByPriceMap.get(x));
            long sum = sumDurations(purgedIds);
            return new DurationEntry(sum, x, groupByPriceMap.get(x));
        }).collect(Collectors.toList());
        return durations;
    }

    public long sumDurations(List<LogEntry> entries){
        return abs(entries.stream().map(x -> x.getTime() * (x.entered ? -1 : 1)).collect(Collectors.summingLong(Long::valueOf)));
    }

    private long abs(long l){
        return l < 0 ? -l : l;
    }
    public List<LogEntry> purgeDoubleToggles(List<LogEntry> entries){
        List<LogEntry> result = new ArrayList<>();
        List<LogEntry> sortedEntries = entries.stream().sorted((a, b) -> (int) (b.getTime() - a.getTime())).collect(Collectors.toList());
        if(sortedEntries.isEmpty()) return result;
        boolean state = sortedEntries.get(0).entered;
        result.add(sortedEntries.get(0));
        for (int i = 1; i < sortedEntries.size(); i++){
            if(state == sortedEntries.get(i).entered) continue;
            state = sortedEntries.get(i).entered;
            result.add(sortedEntries.get(i));
        }
        return result;
    }
}

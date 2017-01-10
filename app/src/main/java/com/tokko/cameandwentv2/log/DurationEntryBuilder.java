package com.tokko.cameandwentv2.log;


import org.androidannotations.annotations.EBean;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EBean
public class DurationEntryBuilder {

    public List<DurationEntry> calculateDurations(List<LogEntry> entries){
        Map<Long, List<LogEntry>> groupByPriceMap =
                entries.stream().collect(Collectors.groupingBy(LogEntry::getDate));

        List<DurationEntry> durations = groupByPriceMap.keySet().stream().map(x -> new DurationEntry(groupByPriceMap.get(x))).collect(Collectors.toList());
        return durations;
    }
}

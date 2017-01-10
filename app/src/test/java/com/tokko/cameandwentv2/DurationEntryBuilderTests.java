package com.tokko.cameandwentv2;

import com.activeandroid.ActiveAndroid;
import com.tokko.cameandwentv2.log.DurationEntry;
import com.tokko.cameandwentv2.log.DurationEntryBuilder;
import com.tokko.cameandwentv2.log.LogEntry;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DurationEntryBuilderTests {


    @Test
    public void sumDurations_EnterExit_Correct(){
        List<LogEntry> list = Arrays.asList(new LogEntry(1, true), new LogEntry(2, false));

        long result = new DurationEntry(list).getDuration();
        Assert.assertEquals(1, result);
    }
     @Test
    public void sumDurations_EnterExitEnterExit_Correct(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true),
                new LogEntry(2, false),
                new LogEntry(3, true),
                new LogEntry(4, false)
        );

        long result = new DurationEntry(list).getDuration();
        Assert.assertEquals(2, result);
    }

    @Test
    public void purgeDoubleToggles_PurgesToggles(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true),
                new LogEntry(2, true)
        );

        List<LogEntry> result = new DurationEntry(null).purgeDoubleToggles(list);
        Assert.assertEquals("There can be only one!", 1, result.size());
        Assert.assertEquals(1, result.get(0).getTime());
    }

    @Test
    public void purgeDoubleToggles_PurgesToggles_ManyEntries(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true),
                new LogEntry(2, true),
                new LogEntry(3, false),
                new LogEntry(4, false),
                new LogEntry(5, true),
                new LogEntry(6, false)
        );

        List<LogEntry> result = new DurationEntry(null).purgeDoubleToggles(list);
        Assert.assertEquals("There can be exactly 4", 4, result.size());
        Assert.assertEquals(1, result.get(0).getTime());
        Assert.assertEquals(3, result.get(1).getTime());
        Assert.assertEquals(5, result.get(2).getTime());
        Assert.assertEquals(6, result.get(3).getTime());
    }

    @Test
    public void calculateDurations_CalculatesCorrectly(){
        DateTime dt = new DateTime(2016, 2, 5, 13, 15);
        DateTime tomorrow = dt.plusDays(1);
        List<LogEntry> list = Arrays.asList(
                new LogEntry(dt.getMillis(), true),
                new LogEntry(dt.plusMinutes(5).getMillis(), false),
                new LogEntry(dt.plusMinutes(10).getMillis(), true),
                new LogEntry(dt.plusMinutes(15).getMillis(), false),
                new LogEntry(tomorrow.getMillis(), true),
                new LogEntry(tomorrow.plusMinutes(5).getMillis(), false)
        );

        List<DurationEntry> result = new DurationEntryBuilder().calculateDurations(list);
        result = result.stream().sorted((a, b) -> (int)(a.getDate() - b.getDate())).collect(Collectors.toList());
        Assert.assertEquals(2, result.size());
        DurationEntry entry = result.get(0);
        Assert.assertEquals(10*60*1000, entry.getDuration());
        Assert.assertEquals(4, entry.getLogEntries().size());
    }
}

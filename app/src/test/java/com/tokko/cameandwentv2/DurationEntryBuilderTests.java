package com.tokko.cameandwentv2;

import com.activeandroid.ActiveAndroid;
import com.tokko.cameandwentv2.log.DurationEntryBuilder;
import com.tokko.cameandwentv2.log.LogEntry;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DurationEntryBuilderTests {


    @Test
    public void sumDurations_EnterExit_Correct(){
        List<LogEntry> list = Arrays.asList(new LogEntry(1, true), new LogEntry(2, false));

        long result = new DurationEntryBuilder().sumDurations(list);
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

        long result = new DurationEntryBuilder().sumDurations(list);
        Assert.assertEquals(2, result);
    }

    @Test
    public void purgeDoubleToggles_PurgesToggles(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true),
                new LogEntry(2, true)
        );

        List<LogEntry> result = new DurationEntryBuilder().purgeDoubleToggles(list);
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

        List<LogEntry> result = new DurationEntryBuilder().purgeDoubleToggles(list);
        Assert.assertEquals("There can be exactly 4", 4, result.size());
        Assert.assertEquals(1, result.get(0).getTime());
        Assert.assertEquals(3, result.get(1).getTime());
        Assert.assertEquals(5, result.get(2).getTime());
        Assert.assertEquals(6, result.get(3).getTime());
    }
}

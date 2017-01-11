package com.tokko.cameandwentv2;

import com.tokko.cameandwentv2.log.DurationEntry;
import com.tokko.cameandwentv2.log.LogEntry;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andre on 11/01/2017.
 */

public class LogEntryRepositoryTests {
    @Test
    public void purgeDoubleToggles_PurgesToggles(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true),
                new LogEntry(2, true)
        );

        List<LogEntry> result = new LogEntryRepository(null).purgeDoubleToggles(list);
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

        List<LogEntry> result = new LogEntryRepository(null).purgeDoubleToggles(list);
        Assert.assertEquals("There can be exactly 4", 4, result.size());
        Assert.assertEquals(1, result.get(0).getTime());
        Assert.assertEquals(3, result.get(1).getTime());
        Assert.assertEquals(5, result.get(2).getTime());
        Assert.assertEquals(6, result.get(3).getTime());
    }
}

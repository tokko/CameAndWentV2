package com.tokko.cameandwentv2;

import com.tokko.cameandwentv2.log.LogEntry;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.fail;

public class LogEntryRepositoryTests {
    @Spy
    LogEntryRepository repo;

    @Test
    public void purgeDoubleToggles_PurgesToggles(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true, 0L),
                new LogEntry(2, true, 0L)
        );

        List<LogEntry> result = new LogEntryRepository(null).getDoubles(list);
        Assert.assertEquals("There can be only one!", 1, result.size());
        Assert.assertEquals(2, result.get(0).getTime());
    }

    @Test
    public void purgeDoubleToggles_PurgesToggles_ManyEntries(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true, 0L),
                new LogEntry(2, true, 0L),
                new LogEntry(3, false, 0L),
                new LogEntry(4, false, 0L),
                new LogEntry(5, true, 0L),
                new LogEntry(6, false, 0L)
        );

        List<LogEntry> result = new LogEntryRepository(null).getDoubles(list);
        Assert.assertEquals("There can be exactly 4", 2, result.size());
        Assert.assertEquals(2, result.get(0).getTime());
        Assert.assertEquals(4, result.get(1).getTime());
    }

    @Test
    @Ignore
    public void adjustOverlabps_OverlappingArrivals_Removes_SecondOne() {
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true, 0L),
                new LogEntry(2, true, 0L)
                //TODO:finish this shiet
        );
        fail();
    }
}

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
                new LogEntry(2, true),
                new LogEntry(3, true),
                new LogEntry(4, false)
        );

        long result = new DurationEntryBuilder().sumDurations(list);
        Assert.assertEquals(2, result);
    }
}

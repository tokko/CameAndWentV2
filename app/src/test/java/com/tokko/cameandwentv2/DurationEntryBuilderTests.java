package com.tokko.cameandwentv2;

import com.activeandroid.ActiveAndroid;
import com.tokko.cameandwentv2.log.DurationEntry;
import com.tokko.cameandwentv2.log.DurationEntryBuilder;
import com.tokko.cameandwentv2.log.LogEntry;
import com.tokko.cameandwentv2.utils.TimeUtils;

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
    public void clockedIn_OnlyOneLogEntryForToday_ShowsCurrentDuration(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true)
        );
        TimeUtils.currentTime = 2;

        long result = new DurationEntry(null).sumDurations(list);
        Assert.assertEquals(1, result);
    }

    @Test
    public void clockedIn_MoreClockEntriesForToday_ShowsCurrentDuration(){
        List<LogEntry> list = Arrays.asList(
                new LogEntry(1, true),
                new LogEntry(2, false),
                new LogEntry(3, true)
        );
        TimeUtils.currentTime = 4;

        long result = new DurationEntry(null).sumDurations(list);
        Assert.assertEquals(2, result);
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

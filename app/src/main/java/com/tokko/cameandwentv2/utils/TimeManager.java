package com.tokko.cameandwentv2.utils;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

/**
 * Created by Andreas on 28/07/2016.
 */
@EBean
public class TimeManager {

    public DateTime getCurrentTime() {
        return new DateTime();
    }

    public String millisToHourMinuteFormat(long millis) {
        long hours = millis / DateTimeConstants.MILLIS_PER_HOUR;
        long minuteMillis = millis % DateTimeConstants.MILLIS_PER_HOUR;
        long minutes = minuteMillis / DateTimeConstants.MILLIS_PER_MINUTE;
        return hours + " " + minutes;
    }
}

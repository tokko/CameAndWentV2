package com.tokko.cameandwentv2.events;

import org.joda.time.DateTime;

/**
 * Created by Andreas on 28/07/2016.
 */

public class EventLogEntryDeleted {
    public DateTime dateTime;

    public EventLogEntryDeleted(long dateTime) {
        this.dateTime = new DateTime(dateTime);
    }
}

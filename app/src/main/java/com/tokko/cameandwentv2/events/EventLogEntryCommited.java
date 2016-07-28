package com.tokko.cameandwentv2.events;

import org.joda.time.DateTime;

/**
 * Created by Andreas on 28/07/2016.
 */

public class EventLogEntryCommited {
    private final DateTime dt;
    public DateTime date;
    public boolean entered;

    public EventLogEntryCommited(DateTime dt, boolean entered) {
        this.dt = dt;
        this.entered = entered;
    }
}

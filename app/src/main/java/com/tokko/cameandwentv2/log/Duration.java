package com.tokko.cameandwentv2.log;

import org.joda.time.DateTime;

/**
 * Created by Andreas on 28/07/2016.
 */

public class Duration {
    DateTime date;
    long duration;

    public Duration(DateTime date, long duration) {
        this.date = date;
        this.duration = duration;
    }

    public Duration() {

    }
}

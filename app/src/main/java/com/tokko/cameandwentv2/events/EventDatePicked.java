package com.tokko.cameandwentv2.events;

import org.joda.time.DateTime;

/**
 * Created by andre on 19/01/2017.
 */

public class EventDatePicked {

    private final int year;
    private final int month;
    private final int day;

    public EventDatePicked(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}

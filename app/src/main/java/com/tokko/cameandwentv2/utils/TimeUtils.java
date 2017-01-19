package com.tokko.cameandwentv2.utils;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

@EBean
public class TimeUtils {
    public static long currentTime = -1;

    public DateTime getCurrentTimeDateTime(){
        return new DateTime(getCurrentTimeMillis());
    }

    public long getCurrentTimeMillis(){
        if(currentTime > 0) return currentTime;
        return System.currentTimeMillis();
    }
}

package com.tokko.cameandwentv2.utils;

import org.androidannotations.annotations.EBean;

@EBean
public class TimeUtils {
    public static long currentTime = -1;

    public long getCurrentTime(){
        if(currentTime > 0) return currentTime;
        return System.currentTimeMillis();
    }
}

package com.tokko.cameandwentv2.utils;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

/**
 * Created by Andreas on 28/07/2016.
 */
@EBean
public class TimeManager {

    public DateTime getCurrentTime() {
        return new DateTime();
    }
}

package com.tokko.cameandwentv2.dagger;

import com.tokko.cameandwentv2.utils.TimeUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TimeUtilModule {

    private TimeUtils timeUtils = new TimeUtils();

    @Provides
    @Singleton
    TimeUtils providesTimeUtils(){
        return timeUtils;
    }
}

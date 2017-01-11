package com.tokko.cameandwentv2.dagger;

import android.content.Context;

import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LogEntryRepositoryModule {

    @Singleton
    @Provides
    LogEntryRepository providesLogEntryRepository(Context context){
        return new LogEntryRepository(context);
    }
}

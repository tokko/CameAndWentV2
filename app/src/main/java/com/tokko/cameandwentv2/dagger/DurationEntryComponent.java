package com.tokko.cameandwentv2.dagger;

import com.tokko.cameandwentv2.log.DurationEntry;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TimeUtilModule.class, LogEntryRepositoryModule.class})
public interface DurationEntryComponent {
    void inject(DurationEntry entry);
}

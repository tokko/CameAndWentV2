package com.tokko.cameandwentv2.dagger;

import com.tokko.cameandwentv2.log.DurationEntry;
import com.tokko.cameandwentv2.log.LogFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andre on 11/01/2017.
 */

@Singleton
@Component(modules = {TimeUtilModule.class, OttoBusModule.class})
public interface LogFragmentComponent {
    void inject(LogFragment logFragment);
}

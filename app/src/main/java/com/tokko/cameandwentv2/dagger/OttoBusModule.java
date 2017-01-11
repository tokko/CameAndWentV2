package com.tokko.cameandwentv2.dagger;

import android.support.design.widget.TabLayout;

import com.tokko.cameandwentv2.events.OttoBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andre on 11/01/2017.
 */
@Module
public class OttoBusModule {

    private OttoBus ottoBus = new OttoBus();

    @Singleton
    @Provides
    public OttoBus provideOttoBus(){
        return ottoBus;
    }
}

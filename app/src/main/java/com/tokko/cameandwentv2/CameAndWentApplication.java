package com.tokko.cameandwentv2;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.tokko.cameandwentv2.dbmodels.Location;
import com.tokko.cameandwentv2.dbmodels.LogEntry;

import org.androidannotations.annotations.EApplication;

@EApplication
public class CameAndWentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClasses(LogEntry.class, Location.class);
        ActiveAndroid.initialize(config.create());
    }
}

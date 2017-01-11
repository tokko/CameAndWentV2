package com.tokko.cameandwentv2;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.util.SQLiteUtils;
import com.tokko.cameandwentv2.dagger.DaggerDurationEntryComponent;
import com.tokko.cameandwentv2.dagger.DurationEntryComponent;
import com.tokko.cameandwentv2.dagger.TimeUtilModule;
import com.tokko.cameandwentv2.log.LogEntry;

import org.androidannotations.annotations.EApplication;

@EApplication
public class CameAndWentApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

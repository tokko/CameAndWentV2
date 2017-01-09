package com.tokko.cameandwentv2;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.util.SQLiteUtils;
import com.tokko.cameandwentv2.log.LogEntry;

import org.androidannotations.annotations.EApplication;

@EApplication
public class CameAndWentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*
        Configuration.Builder config = new Configuration.Builder(this);
        config.addModelClass(LogEntry.class);
        config.setDatabaseVersion(6);
        config.setDatabaseName("CameAndWentV2");
        ActiveAndroid.initialize(config.create());
        */
    }
}

package com.tokko.cameandwentv2;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import org.androidannotations.annotations.EApplication;

@EApplication
public class CameAndWentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Configuration.Builder config = new Configuration.Builder(this);
        //ActiveAndroid.initialize(config.create());
    }
}

package com.tokko.cameandwentv2.resourceaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.log.DaoMaster;
import com.tokko.cameandwentv2.log.DaoSession;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

@EBean
public abstract class BaseRepository {
    @Bean
    public OttoBus bus;
    protected  DaoSession daoSession;

    public BaseRepository(Context context) {
        if(context == null) return;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cameandwentv2-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}

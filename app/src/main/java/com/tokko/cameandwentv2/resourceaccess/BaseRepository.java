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
    private Context context;

    public BaseRepository(Context context) {
        this.context = context;
        if(context == null) return;

    }

    protected DaoSession getSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cameandwentv2-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}

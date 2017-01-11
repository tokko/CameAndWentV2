package com.tokko.cameandwentv2.resourceaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tokko.cameandwentv2.events.EventLogEntryAdded;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.log.DaoMaster;
import com.tokko.cameandwentv2.log.DaoSession;
import com.tokko.cameandwentv2.log.LogEntry;
import com.tokko.cameandwentv2.log.LogEntryDao;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class LogEntryRepository {

    private final DaoSession daoSession;
    private final LogEntryDao logEntryDao;

    @Bean
    public OttoBus bus;

    public LogEntryRepository(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "cameandwentv2-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        logEntryDao = daoSession.getLogEntryDao();
    }

    public void insertLogEntry(LogEntry entry){
        logEntryDao.insert(entry);
        bus.post(new EventLogEntryAdded(entry));
    }

    public void deleteLogEntry(LogEntry entry){
        logEntryDao.delete(entry);
        bus.post(new EventLogEntryDeleted(entry));
    }

    public List<LogEntry> readAll() {
        return logEntryDao.loadAll();
    }

    public void deleteAll() {
        logEntryDao.deleteAll();
    }
}

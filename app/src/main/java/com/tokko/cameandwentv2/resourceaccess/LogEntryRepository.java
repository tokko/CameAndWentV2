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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@EBean
public class LogEntryRepository {

    private DaoSession daoSession;
    private LogEntryDao logEntryDao;

    @Bean
    public OttoBus bus;

    public LogEntryRepository(Context context) {
        if(context == null) return;
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
        return purgeDoubleToggles(logEntryDao.loadAll());
    }

    public void deleteAll() {
        logEntryDao.deleteAll();
    }

    public List<LogEntry> purgeDoubleToggles(List<LogEntry> entries){
        List<LogEntry> result = new ArrayList<>();
        List<LogEntry> sortedEntries = entries.stream().sorted((a, b) -> (int) (a.getTime() - b.getTime())).collect(Collectors.toList());
        if(sortedEntries.isEmpty()) return result;
        boolean state = sortedEntries.get(0).entered;
        result.add(sortedEntries.get(0));
        for (int i = 1; i < sortedEntries.size(); i++){
            LogEntry currentEntry = sortedEntries.get(i);
            boolean currentState = currentEntry.entered;
            if(state == currentState){
                if(bus != null) bus.post(new EventLogEntryDeleted(currentEntry));
                continue;
            }
            state = currentState;
            result.add(currentEntry);
        }
        return result;
    }
}

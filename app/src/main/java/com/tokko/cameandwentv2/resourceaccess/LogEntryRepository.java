package com.tokko.cameandwentv2.resourceaccess;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tokko.cameandwentv2.events.EventLogEntryAdded;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.EventLogEntryUpdated;
import com.tokko.cameandwentv2.log.LogEntry;
import com.tokko.cameandwentv2.log.LogEntryDao;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@EBean
public class LogEntryRepository extends BaseRepository {

    private LogEntryDao logEntryDao;

    @Inject
    public LogEntryRepository(Context context) {
        super(context);
        if(daoSession != null)
            logEntryDao = daoSession.getLogEntryDao();
    }

    public void Commit(LogEntry entry){
        if(entry.getId() == null) {
            logEntryDao.insert(entry);
            bus.post(new EventLogEntryAdded(entry));
        }
        else{
            logEntryDao.update(entry);
            bus.post(new EventLogEntryUpdated(entry));
        }
    }

    public void deleteLogEntry(LogEntry entry){
        logEntryDao.delete(entry);
        new Handler(Looper.getMainLooper()).post(() -> bus.post(new EventLogEntryDeleted(entry)));
    }

    public List<LogEntry> readAll() {
        return logEntryDao.loadAll();
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
                deleteLogEntry(currentEntry);
                continue;
            }
            state = currentState;
            result.add(currentEntry);
        }
        return result;
    }

    public LogEntry getLatestLogEntry() {
        List<LogEntry> entries = logEntryDao.queryBuilder().orderDesc(LogEntryDao.Properties.Time).limit(1).build().list();
        if(entries.isEmpty()) return null;
        return entries.get(0);
    }

    public LogEntry getLogEntry(Long id) {
        return logEntryDao.load(id);
    }

}

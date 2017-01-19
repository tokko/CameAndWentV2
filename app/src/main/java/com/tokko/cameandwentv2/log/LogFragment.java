package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ToggleButton;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.dagger.DaggerLogFragmentComponent;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.EventLogEntryAdded;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.project.ProjectListFragment;
import com.tokko.cameandwentv2.project.ProjectListFragment_;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;
import com.tokko.cameandwentv2.utils.TimeUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Collection;

import javax.inject.Inject;

@EFragment(R.layout.logfragment)
public class LogFragment extends ListFragment implements ProjectListFragment.OnProjectChosenListener {

    @ViewById
    ExpandableListView list;
    @ViewById
    ToggleButton clockButton;
    @Bean
    LogEntryAdapter adapter;

    @Bean
    OttoBus bus;
    @Bean
    LogEntryRepository logEntryRepo;

    @Inject
    TimeUtils timeUtils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DaggerLogFragmentComponent.builder().build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @AfterInject
    public void initBus(){
        bus.register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        list.setAdapter(adapter);
        new EntryLoader().execute();
        setStatusOfClockButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    public void setStatusOfClockButton(){
        LogEntry latestLogEntry = logEntryRepo.getLatestLogEntry();
        if(latestLogEntry != null)
            clockButton.setChecked(latestLogEntry.entered);
        else
            clockButton.setChecked(false);
    }


    //@OptionsItem(R.id.action_clear)
    public void purgeDb(){
        logEntryRepo.deleteAll();
        adapter.clear();
    }

    @Subscribe
    public void deleteLogEntry(EventLogEntryDeleted entry){
        adapter.delete(entry.getEntry());
        setStatusOfClockButton();
    }

    @Subscribe
    public void addLogEntry(EventLogEntryAdded entryAdded){
        adapter.add(entryAdded.getEntry());
        setStatusOfClockButton();
    }

    @Click(R.id.clockButton)
    public void clockButtonClick(){
        if(clockButton.isChecked()){
            ProjectListFragment_ projectChooser = new ProjectListFragment_();
            projectChooser.setChooserMode(this);
            projectChooser.show(getFragmentManager(), "projectChooser");
            return;
        }
        LogEntry latestEntry = logEntryRepo.getLatestLogEntry();
        LogEntry entry = new LogEntry(timeUtils.getCurrentTimeMillis(), clockButton.isChecked(), latestEntry.getProjectId());
        logEntryRepo.insertLogEntry(entry);
    }

    @Override
    public void onProjectChosen(Long projectId) {
        LogEntry entry = new LogEntry(timeUtils.getCurrentTimeMillis(), clockButton.isChecked(), projectId);
        logEntryRepo.insertLogEntry(entry);
    }

    private class EntryLoader extends AsyncTask<Void, Void, Collection<LogEntry>> {

        @Override
        protected Collection<LogEntry> doInBackground(Void... voids) {
            return logEntryRepo.readAll();
        }

        @Override
        protected void onPostExecute(Collection<LogEntry> logEntries) {
            if(adapter != null)
                adapter.addAll(logEntries);
        }
    }
}

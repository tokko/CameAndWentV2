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
import com.tokko.cameandwentv2.events.EventMinuteTicked;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;
import com.tokko.cameandwentv2.utils.TimeUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

@EFragment(R.layout.logfragment)
public class LogFragment extends ListFragment{

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
    Timer timer;

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
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> bus.post(new EventMinuteTicked()));
            }
        }, 0, 1000);
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
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
        List<LogEntry> entries = logEntryRepo.readAll();
        Optional<Long> maxTime = entries.stream().map(LogEntry::getTime).max((a, b) -> (int)(a - b));
        if(maxTime.isPresent()){
            Optional<LogEntry> maxEntry = entries.stream().filter(x -> x.getTime() == maxTime.get()).findFirst();
            clockButton.setChecked(maxEntry.get().entered);
        }
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
        LogEntry entry = new LogEntry(timeUtils.getCurrentTime(), clockButton.isChecked());
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

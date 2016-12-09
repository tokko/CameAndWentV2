package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.activeandroid.query.Select;
import com.annimon.stream.Stream;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.events.EventLogEntryAdded;
import com.tokko.cameandwentv2.events.OttoBus;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.Collection;

@EFragment(R.layout.logfragment)
@OptionsMenu(R.menu.menu_main)
public class LogFragment extends ListFragment{

    @ViewById
    ListView list;
    @ViewById
    ToggleButton clockButton;
    @Bean
    LogEntryAdapter adapter;

    @Bean
    OttoBus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    public void setAdapter(){
        list.setAdapter(adapter);
        new EntryLoader().execute();
    }

    @OptionsItem(R.id.action_clear)
    public void purgeDb(){
        Collection<LogEntry> entries = new Select().from(LogEntry.class).execute();
        Stream.of(entries).forEach(LogEntry::delete);
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    @Click(R.id.clockButton)
    public void clockButtonClick(){
        LogEntry entry = new LogEntry(System.currentTimeMillis(), clockButton.isChecked());
        entry.save();
        bus.post(new EventLogEntryAdded(entry));
        adapter.add(entry);
    }

    private class EntryLoader extends AsyncTask<Void, Void, Collection<LogEntry>> {

        @Override
        protected Collection<LogEntry> doInBackground(Void... voids) {
            return new Select().from(LogEntry.class).execute();
        }

        @Override
        protected void onPostExecute(Collection<LogEntry> logEntries) {
            if(adapter != null)
                adapter.addAll(logEntries);
        }
    }
}

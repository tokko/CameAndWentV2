package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ToggleButton;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.MainActivity;
import com.tokko.cameandwentv2.MainActivity_;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.dagger.DaggerLogFragmentComponent;
import com.tokko.cameandwentv2.events.EventLogEntryAdded;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.EventLogEntryUpdated;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.project.ProjectListFragment;
import com.tokko.cameandwentv2.project.ProjectListFragment_;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;
import com.tokko.cameandwentv2.utils.TimeUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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
        bus.register(this);
        list.setAdapter(adapter);
        reload();
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    public void setStatusOfClockButton(LogEntry latestLogEntry) {
        if(clockButton == null) return;
        if (latestLogEntry != null)
            clockButton.setChecked(latestLogEntry.entered);
        else
            clockButton.setChecked(false);
    }

    @AfterViews
    public void initViews() {
        list.setStackFromBottom(true);
    }

    @OptionsItem(R.id.add_logentry)
    public void onAddLogEntryClicked() {
        Intent i = new Intent(getActivity(), MainActivity_.class);
        i.setAction(MainActivity.ACTION_EDIT_LOG);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getActivity().startActivity(i);
    }

    @OptionsItem(R.id.action_clear)
    public void purgeDb() {
        logEntryRepo.deleteAll();
        adapter.clear();
        reload();
    }

    @Subscribe
    public void deleteLogEntry(EventLogEntryDeleted entry) {
       reload();
    }

    @Subscribe
    public void addLogEntry(EventLogEntryAdded entryAdded) {
        reload();
    }

    @Subscribe
    public void updateEntry(EventLogEntryUpdated entryUpdated) {
        reload();
    }
    @OptionsItem(R.id.reload)
    public void reload(){
        List<Long> expandIds = new ArrayList<>();
        if (list != null)
            for (int i = 0; i < adapter.getGroupCount(); i++)
                if (list.isGroupExpanded(i))
                    expandIds.add(adapter.getGroupId(i));
        if (adapter != null) {
            List<LogEntry> logEntries = logEntryRepo.readAll();
            adapter.clear();
            adapter.addAll(logEntries);
            adapter.notifyDataSetChanged();
            if (!logEntries.isEmpty())
                setStatusOfClockButton(logEntries.get(logEntries.size() - 1));
        }
        if (!adapter.isEmpty())
            list.expandGroup(adapter.getGroupCount() - 1);
        if (list != null)
            for (int i = 0; i < adapter.getGroupCount(); i++)
                if (expandIds.contains(adapter.getGroupId(i)))
                    list.expandGroup(i);
    }

    @Click(R.id.clockButton)
    public void clockButtonClick() {
        if (clockButton.isChecked()) {
            ProjectListFragment_ projectChooser = new ProjectListFragment_();
            projectChooser.setChooserMode(this);
            projectChooser.show(getFragmentManager(), "projectChooser");
            return;
        }
        LogEntry latestEntry = logEntryRepo.getLatestLogEntry();
        LogEntry entry = new LogEntry(timeUtils.getCurrentTimeMillis(), clockButton.isChecked(), latestEntry.getProjectId());
        logEntryRepo.commit(entry);
    }

    @Override
    public void onProjectChosen(Long projectId) {
        LogEntry entry = new LogEntry(timeUtils.getCurrentTimeMillis(), clockButton.isChecked(), projectId);
        logEntryRepo.commit(entry);
    }
}

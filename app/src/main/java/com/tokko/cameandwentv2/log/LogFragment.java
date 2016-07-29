package com.tokko.cameandwentv2.log;

import android.app.Fragment;
import android.widget.ExpandableListView;
import android.widget.ToggleButton;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.dbmodels.LogEntry;
import com.tokko.cameandwentv2.dbmodels.ResourceAccess;
import com.tokko.cameandwentv2.utils.TimeManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Andreas on 27/07/2016.
 */
@EFragment(R.layout.logfragment)
public class LogFragment extends Fragment {
    @ViewById
    public ToggleButton clockButton;
    @ViewById
    public ExpandableListView list;

    @Bean
    DurationAdapter durationAdapter;

    @Bean
    ResourceAccess resourceAccess;

    @Bean
    TimeManager timeManager;

    @AfterViews
    public void init() {
        LogEntry le = resourceAccess.readLatestLogEntry();
        if (le != null)
            clockButton.setChecked(le.entered);
    }

    @AfterViews
    public void bindAdapters() {
        list.setAdapter(durationAdapter);
    }

    @Click(R.id.clockButton)
    public void onClockButtonClick() {
        resourceAccess.CommitLogEntry(timeManager.getCurrentTime(), clockButton.isChecked(), "Leffe");
        durationAdapter.refreshData();
    }

    @LongClick(R.id.clockButton)
    public void onLongClockButtonClick() {

    }
}

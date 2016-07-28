package com.tokko.cameandwentv2.log;

import android.app.Fragment;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.activeandroid.query.Select;
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
import org.joda.time.DateTime;


/**
 * Created by Andreas on 27/07/2016.
 */
@EFragment(R.layout.logfragment)
public class LogFragment extends Fragment {
    @ViewById
    public ToggleButton clockButton;
    @ViewById
    public ListView list;

    @Bean
    LogEntryAdapter logEntryAdapter;

    @Bean
    ResourceAccess resourceAccess;

    @Bean
    TimeManager timeManager;

    @AfterViews
    public void init() {
        LogEntry le = new Select().from(LogEntry.class).orderBy("date DESC").executeSingle();
        if (le != null)
            clockButton.setChecked(le.entered);
        //setListShown(true);
        logEntryAdapter.init(new DateTime().getMillis());
    }

    @AfterViews
    public void bindAdapters() {
        list.setAdapter(logEntryAdapter);
    }

    @Click(R.id.clockButton)
    public void onClockButtonClick() {
        resourceAccess.CommitLogEntry(timeManager.getCurrentTime(), clockButton.isChecked(), "Leffe");
        logEntryAdapter.notifyDataSetChanged();
    }

    @LongClick(R.id.clockButton)
    public void onLongClockButtonClick() {

    }
}

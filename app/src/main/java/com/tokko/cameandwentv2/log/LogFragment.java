package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.activeandroid.query.Select;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.dbmodels.LogEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Andreas on 27/07/2016.
 */
@EFragment(R.layout.logfragment)
public class LogFragment extends ListFragment {
    @ViewById
    public ToggleButton clockButton;
    @ViewById
    public ListView list;

    @AfterViews
    public void init() {
        LogEntry model = new Select().from(LogEntry.class).orderBy("date DESC").executeSingle();
        clockButton.setChecked(model.entered);
    }

    @Click(R.id.clockButton)
    public void onClockButtonClick() {

    }
}

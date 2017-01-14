package com.tokko.cameandwentv2.log;

import android.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.utils.TimeUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

@EFragment(R.layout.logentryedit)
public class LogEntryEditFragment extends DialogFragment {

    @ViewById
    TimePicker timePicker;
    @ViewById
    DatePicker datePicker;
    @ViewById
    TextView comment;
    @Bean
    TimeUtils timeUtils;

    private LogEntry entry;

    public void setEntry(LogEntry entry){

        this.entry = entry;
    }

    @AfterViews
    public void initForm(){
        DateTime dt = new DateTime(entry.getDate());
        datePicker.updateDate(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
        timePicker.setHour(new DateTime(entry.getTime()).getHourOfDay());
        timePicker.setMinute(new DateTime(entry.getTime()).getMinuteOfDay());
        comment.setText(entry.getComment());
    }
}

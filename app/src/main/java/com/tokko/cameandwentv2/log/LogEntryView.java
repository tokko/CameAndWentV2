package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.dbmodels.LogEntry;
import com.tokko.cameandwentv2.dbmodels.ResourceAccess;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * Created by Andreas on 27/07/2016.
 */
@EViewGroup(R.layout.logentry)
public class LogEntryView extends LinearLayout {
    @ViewById
    TextView action;
    @ViewById
    TextView time;
    @ViewById
    TextView location;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    @Bean
    ResourceAccess ra;
    private LogEntry entry;

    public LogEntryView(Context context) {
        super(context);
    }

    public void bind(LogEntry entry) {
        this.entry = entry;
        action.setText(entry.entered ? "Entered" : "Exited");
        time.setText(sdf.format(entry.dateTime));
        if (entry.location != null)
            location.setText(entry.location.name);
    }

    @Click(R.id.delete)
    public void onDeleteClick() {
        ra.deleteLogEntry(entry);
    }
}

package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;

@EViewGroup(R.layout.logentry)
public class LogEntryView extends LinearLayout{
    @ViewById
    TextView time;
    @ViewById
    TextView entered;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
    public LogEntryView(Context context) {
        super(context);
    }

    public void bind(LogEntry entry){
        time.setText(timeFormat.format(new Date(entry.getTime())));
        entered.setText(entry.isEntered() ? "Arrived" : "Departed");
    }
}

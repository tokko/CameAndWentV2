package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
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
    @Bean
    OttoBus bus;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private LogEntry entry;
    @Bean
    LogEntryRepository logEntryRepository;
    public LogEntryView(Context context) {
        super(context);
    }

    public void bind(LogEntry entry){
        this.entry = entry;
        time.setText(timeFormat.format(new Date(entry.getTime())));
        entered.setText(entry.isEntered() ? "Arrived" : "Departed");

    }
    @Click(R.id.delete)
    public void delete(){
       // setVisibility(View.GONE);
       //
        logEntryRepository.deleteLogEntry(entry);
    }
}

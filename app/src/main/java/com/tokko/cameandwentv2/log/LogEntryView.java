package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.MainActivity;
import com.tokko.cameandwentv2.MainActivity_;
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
    @ViewById
    TextView project;
    @ViewById
    TextView comment;

    @Bean
    OttoBus bus;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private LogEntry entry;
    @Bean
    LogEntryRepository logEntryRepository;
    private Context context;

    public LogEntryView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind(LogEntry entry){
        this.entry = entry;
        time.setText(timeFormat.format(new Date(entry.getTime())));
        entered.setText(entry.isEntered() ? "Arrived" : "Departed");
        project.setText(entry.getProject().getTitle());
        comment.setVisibility(entry.getComment() != null && !entry.comment.equals("") ? VISIBLE : GONE);
        comment.setText(entry.getComment());
    }

    @Click(R.id.edit)
    public void edit(){
        Intent i = new Intent(context, MainActivity_.class);
        i.setAction(MainActivity.ACTION_EDIT_LOG);
        i.putExtra(MainActivity.EXTRA_LOGENTRYID, entry.getId());
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }

    @Click(R.id.delete)
    public void delete(){
        logEntryRepository.deleteLogEntry(entry);
    }
}

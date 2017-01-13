package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.events.EventMinuteTicked;
import com.tokko.cameandwentv2.events.OttoBus;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@EViewGroup(android.R.layout.simple_expandable_list_item_2)
public class DurationView extends LinearLayout {
    @ViewById(android.R.id.text1)
    public TextView date;
    @ViewById(android.R.id.text2)
    public TextView duration;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DurationEntry entry;
    private Timer timer;

    public DurationView(Context context) {
        super(context);
    }

    public void bind(DurationEntry entry){
        if(entry.getLogEntries().isEmpty()) return;
        this.entry = entry;
        long duration = entry.getDuration();
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration - TimeUnit.HOURS.toMillis(hours));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
        String durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        date.setText(dateFormat.format(new Date(entry.getDate())));
        this.duration.setText(durationString);
        if(timer != null ){
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> bind(entry));
            }
        }, 0, 1000);
    }
}

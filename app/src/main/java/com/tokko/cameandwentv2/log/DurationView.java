package com.tokko.cameandwentv2.log;

import android.content.Context;
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
import java.util.concurrent.TimeUnit;

@EViewGroup(android.R.layout.simple_expandable_list_item_2)
public class DurationView extends LinearLayout {
    @ViewById(android.R.id.text1)
    public TextView date;
    @ViewById(android.R.id.text2)
    public TextView duration;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DurationEntry entry;
    @Bean
    OttoBus bus;

    public DurationView(Context context) {
        super(context);
    }

    @AfterInject
    public void initBus(){
        bus.register(this);
    }

    public void bind(DurationEntry entry){
        this.entry = entry;
        long duration = entry.getDuration();
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration - TimeUnit.HOURS.toMillis(hours));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
        String durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        date.setText(dateFormat.format(new Date(entry.getDate())));
        this.duration.setText(durationString);
    }
    @Subscribe
    public void rebind(EventMinuteTicked event){
        bind(entry);
    }
}

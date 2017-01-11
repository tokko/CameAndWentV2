package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public DurationView(Context context) {
        super(context);
    }

    public void bind(DurationEntry entry){
        long hours = TimeUnit.MILLISECONDS.toHours(entry.getDuration());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(entry.getDuration() - TimeUnit.HOURS.toMillis(hours));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(entry.getDuration() - TimeUnit.HOURS.toMillis(hours) - TimeUnit.MINUTES.toMillis(minutes));
        String durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        date.setText(dateFormat.format(new Date(entry.getDate())));
        duration.setText(durationString);
    }
}

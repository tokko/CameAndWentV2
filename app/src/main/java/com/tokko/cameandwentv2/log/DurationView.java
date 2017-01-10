package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;

@EViewGroup(android.R.layout.simple_expandable_list_item_2)
public class DurationView extends LinearLayout {
    @ViewById(android.R.id.text1)
    public TextView date;
    @ViewById(android.R.id.text2)
    public TextView duration;
    private final SimpleDateFormat durationFormatter = new SimpleDateFormat("hh:MM");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    public DurationView(Context context) {
        super(context);
    }

    public void bind(DurationEntry entry){
        date.setText(dateFormat.format(new Date(entry.getDate())));
        duration.setText(durationFormatter.format(new Date(entry.getDuration())));
    }
}

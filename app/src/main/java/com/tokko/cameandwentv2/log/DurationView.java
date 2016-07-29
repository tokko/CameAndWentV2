package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.utils.TimeManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

/**
 * Created by Andreas on 28/07/2016.
 */
@EViewGroup(R.layout.expandablelist)
public class DurationView extends LinearLayout {
    @ViewById
    TextView text1;
    @ViewById
    TextView text2;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/mm, ddd");

    @Bean
    TimeManager tm;

    public DurationView(Context context) {
        super(context);
    }

    public void bind(Duration group) {
        text1.setText(sdf.format(group.date.getMillis()));
        text2.setText(tm.millisToHourMinuteFormat(group.duration));
    }
}

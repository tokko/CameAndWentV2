package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.dbmodels.LogEntry;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by Andreas on 27/07/2016.
 */
@EViewGroup(R.layout.logentry)
public class LogEntryView extends LinearLayout {
    public LogEntryView(Context context) {
        super(context);
    }

    public void bind(LogEntry entry) {

    }
}

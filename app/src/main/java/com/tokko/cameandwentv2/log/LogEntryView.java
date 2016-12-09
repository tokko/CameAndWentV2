package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokko.cameandwentv2.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by andre on 9/12/2016.
 */
@EViewGroup(R.layout.logentry)
public class LogEntryView extends LinearLayout{
    @ViewById
    TextView time;
    @ViewById
    TextView entered;

    public LogEntryView(Context context) {
        super(context);
    }

    public void bind(LogEntry entry){
        time.setText(entry.getTime()+"");
        entered.setText(entry.isEntered()+"");
    }
}

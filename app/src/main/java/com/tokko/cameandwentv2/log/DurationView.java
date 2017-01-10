package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(android.R.layout.simple_expandable_list_item_2)
public class DurationView extends LinearLayout {
    @ViewById(android.R.id.text1)
    public TextView text1;
    @ViewById(android.R.id.text2)
    public TextView text2;

    public DurationView(Context context) {
        super(context);
    }

    public void bind(DurationEntry entry){
        text1.setText(entry.getDate()+"");
        text2.setText(entry.getDuration()+"");
    }
}

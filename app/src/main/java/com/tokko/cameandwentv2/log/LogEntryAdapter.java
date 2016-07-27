package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.activeandroid.query.Select;
import com.tokko.cameandwentv2.dbmodels.LogEntry;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas on 27/07/2016.
 */

@EBean
public class LogEntryAdapter extends BaseAdapter {
    @RootContext
    Context context;
    private List<LogEntry> list = new ArrayList<>();

    public void init(long date) {
        list = new Select().from(LogEntry.class)
                //.where("Date = ?", date)
                .orderBy("DateTime DESC").execute();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        init(new DateTime().getMillis());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LogEntry getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).dateTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogEntryView lev;
        if (convertView == null)
            lev = LogEntryView_.build(context);
        else
            lev = (LogEntryView) convertView;
        lev.bind(getItem(position));
        return lev;
    }
}

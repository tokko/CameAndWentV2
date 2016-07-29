package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.dbmodels.LogEntry;
import com.tokko.cameandwentv2.dbmodels.ResourceAccess;
import com.tokko.cameandwentv2.events.EventLogEntryCommited;
import com.tokko.cameandwentv2.events.EventLogEntryDeleted;
import com.tokko.cameandwentv2.events.OttoBus;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas on 27/07/2016.
 */

@EBean
public class LogEntryAdapter extends BaseAdapter {
    @RootContext
    Context context;

    @Bean
    ResourceAccess ra;

    @Bean
    OttoBus bus;

    private List<LogEntry> list = new ArrayList<>();

    public void init() {
        list = ra.getLogEntries();
    }

    @AfterInject
    public void bindBus() {
        bus.register(this);
    }

    @Subscribe
    public void onDataSetChanged(EventLogEntryCommited event) {
        notifyDataSetChanged();
    }

    @Subscribe
    public void onDataSetChanged(EventLogEntryDeleted eventLogEntryDeleted) {
        notifyDataSetChanged();
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        init();
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

package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collection;

@EBean
public class LogEntryAdapter extends BaseAdapter {
    private ArrayList<LogEntry> data = new ArrayList<>();

    @RootContext
    Context context;

    public void addAll(Collection<LogEntry> entries){
        data.addAll(entries);
        notifyDataSetChanged();
    }

    public void add(LogEntry entry){
        data.add(entry);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public LogEntry getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LogEntryView entryView;
        if(view == null) entryView = LogEntryView_.build(context);
        else entryView = (LogEntryView) view;
        entryView.bind(getItem(i));
        return entryView;
    }
}

package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@EBean
public class LogEntryAdapter extends BaseExpandableListAdapter {
    private ArrayList<DurationEntry> data = new ArrayList<>();
    private ArrayList<DataSetObserver> observers = new ArrayList<>();

    @RootContext
    Context context;

    public void addAll(Collection<LogEntry> entries){
        entries.forEach(x -> {
                    Optional<DurationEntry> existing = data.stream().filter(y -> y.getDate() == x.getDate()).findFirst();
                    if (existing.isPresent())
                        existing.get().addEntry(x);
                    else
                        data.add(new DurationEntry(new ArrayList<LogEntry>() {{
                            add(x);
                        }}));
                }
        );
        observers.forEach(DataSetObserver::onChanged);
    }

    public void add(LogEntry entry){
        addAll(new ArrayList<LogEntry>(){{add(entry);}});
        observers.forEach(DataSetObserver::onChanged);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        observers.forEach(DataSetObserver::onChanged);
        notifyDataSetChanged();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        observers.add(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        observers.remove(dataSetObserver);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getLogEntries().size();
    }

    @Override
    public DurationEntry getGroup(int i) {
        return data.get(i);
    }

    @Override
    public LogEntry getChild(int i, int i1) {
        return getGroup(i).getLogEntries().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return getGroup(i).getDate();
    }

    @Override
    public long getChildId(int i, int i1) {
        return getGroup(i).getLogEntries().get(i1).getTime();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        DurationView durationView;
        if(view == null) durationView = DurationView_.build(context);
        else durationView = (DurationView) view;
        durationView.bind(getGroup(i));
        return durationView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LogEntryView entryView;
        if(view == null) entryView = LogEntryView_.build(context);
        else entryView = (LogEntryView) view;
        entryView.bind(getChild(i, i1));
        return entryView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return l^l1;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return l;
    }
}

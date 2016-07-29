package com.tokko.cameandwentv2.log;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

import com.annimon.stream.Stream;
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
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Andreas on 28/07/2016.
 */
@EBean
public class DurationAdapter implements ExpandableListAdapter {

    @RootContext
    Context context;

    HashMap<Duration, List<LogEntry>> data = new HashMap<>();

    @Bean
    ResourceAccess ra;

    @Bean
    OttoBus bus;

    @AfterInject
    public void init() {
        bus.register(this);
        refreshData();
    }

    @Subscribe
    public void onDataChanged(EventLogEntryCommited event) {
        refreshData();
    }

    @Subscribe
    public void onDataChanged(EventLogEntryDeleted eventLogEntryDeleted) {
        refreshData();
    }

    public void refreshData() {
        calculateDurations(ra.getLogEntries());
    }

    public void calculateDurations(List<LogEntry> entries) {
        Stream.of(entries).groupBy(x -> x.date).forEach(x -> data.put(
                new Duration(new DateTime(x.getKey().longValue()),
                        Stream.of(x.getValue()).map(y -> y.entered ? y.dateTime : -y.dateTime).reduce((a, b) -> a + b).get()),
                x.getValue()));
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return data.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.size();
    }

    @Override
    public Duration getGroup(int groupPosition) {
        return (Duration) data.keySet().toArray()[groupPosition];
    }

    @Override
    public LogEntry getChild(int groupPosition, int childPosition) {
        return (LogEntry) data.get(getGroup(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition).dateTime;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        DurationView dv;
        if (convertView == null)
            dv = DurationView_.build(context);
        else
            dv = (DurationView) convertView;
        dv.bind(getGroup(groupPosition));
        return dv;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LogEntryView lev;
        if (convertView == null)
            lev = LogEntryView_.build(context);
        else
            lev = (LogEntryView) convertView;
        lev.bind(getChild(groupPosition, childPosition));
        return lev;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
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
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}

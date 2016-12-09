package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class LogFragment extends ListFragment{

    @ViewById
    ListView list;
    ArrayList<LogEntry> data = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data.add(new LogEntry(System.currentTimeMillis()));
        data.add(new LogEntry(System.currentTimeMillis()));
        data.add(new LogEntry(System.currentTimeMillis()));
        data.add(new LogEntry(System.currentTimeMillis()));
        List<String> collect = Stream.of(data).map(x -> x.getTime() + "").collect(Collectors.toList());
        adapter  = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, collect);
    }

    @AfterViews
    public void setAdapter(){
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setListShown(true);
    }
}

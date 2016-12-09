package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.tokko.cameandwentv2.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.logfragment)
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
        adapter  = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, collect);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //
        return null;
    }
    @AfterViews
    public void setAdapter(){
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Click(R.id.clockbutton)
    public void clockButtonClick(){
        data.add(new LogEntry(System.currentTimeMillis()));
        refreshData();
    }

    private void refreshData() {
        List<String> collect = Stream.of(data).map(x -> x.getTime() + "").collect(Collectors.toList());
        adapter.clear();
        adapter.addAll(collect);
        adapter.notifyDataSetChanged();
    }
}

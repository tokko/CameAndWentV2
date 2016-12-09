package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.tokko.cameandwentv2.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.logfragment)
public class LogFragment extends ListFragment{

    @ViewById
    ListView list;
    @ViewById
    ToggleButton clockButton;
    @Bean
    LogEntryAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.add(new LogEntry(System.currentTimeMillis(), false));
        adapter.add(new LogEntry(System.currentTimeMillis(), false));
        adapter.add(new LogEntry(System.currentTimeMillis(), false));
        adapter.add(new LogEntry(System.currentTimeMillis(), false));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    public void setAdapter(){
        list.setAdapter(adapter);
    }

    @Click(R.id.clockButton)
    public void clockButtonClick(){
        adapter.add(new LogEntry(System.currentTimeMillis(), clockButton.isChecked()));

    }
}

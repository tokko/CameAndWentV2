package com.tokko.cameandwentv2.log;

import android.app.ListFragment;
import android.widget.ListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment
public class LogFragment extends ListFragment{
    @ViewById()
    ListView list;
    ArrayList<LogEntry> data = new ArrayList<>();
}

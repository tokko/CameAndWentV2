package com.tokko.cameandwentv2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.tokko.cameandwentv2.log.LogFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.mainactivity)
public class MainActivity extends Activity {
    @ViewById
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.mainlayout, new LogFragment_()).commit();
    }

    @AfterViews
    public void initToolbar(){
        setActionBar(toolbar);
        getActionBar().show();
    }
}

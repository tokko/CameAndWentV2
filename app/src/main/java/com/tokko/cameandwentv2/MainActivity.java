package com.tokko.cameandwentv2;

import android.app.Activity;
import android.os.Bundle;

import com.tokko.cameandwentv2.log.LogFragment_;

import org.androidannotations.annotations.EActivity;

@EActivity
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new LogFragment_()).commit();
    }
}

package com.tokko.cameandwentv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.tokko.cameandwentv2.log.LogEntryEditFragment;
import com.tokko.cameandwentv2.log.LogEntryEditFragment_;
import com.tokko.cameandwentv2.log.LogFragment_;
import com.tokko.cameandwentv2.project.ProjectListFragment_;
import com.tokko.cameandwentv2.resourceaccess.LogEntryRepository;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.mainactivity)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends Activity implements LogEntryEditFragment.LogEditCallbacks {
    public static final String ACTION_EDIT_LOG = "ACTION_EDIT_LOG";
    public static final String EXTRA_LOGENTRYID = "EXTRA_LOGENTRYID";

    @ViewById
    Toolbar toolbar;

    @Bean
    ProjectRepository projectRepository;
    @Bean
    LogEntryRepository logEntryRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLogFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProjectFragmentIfThereAreNoProjects();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getAction().equals(ACTION_EDIT_LOG)) {
            LogEntryEditFragment_ fragment = LogEntryEditFragment_.Create(intent.getLongExtra(EXTRA_LOGENTRYID, -1L));
            getFragmentManager().beginTransaction().addToBackStack("edit").replace(R.id.mainlayout, fragment).commit();
        }
    }

    public void showProjectFragmentIfThereAreNoProjects(){
        if(projectRepository.readAll().isEmpty())
            showProjectsFragment();
    }

    @AfterViews
    public void initToolbar(){
        setActionBar(toolbar);
        getActionBar().show();
    }

    @OptionsItem(android.R.id.home)
    public void onHomeClicked(){
        getFragmentManager().popBackStack();
        setHomeButtonState(false);
    }
    @OptionsItem(R.id.manageProjects)
    public void showProjectsFragment(){
        setHomeButtonState(true);
        getFragmentManager().beginTransaction().replace(R.id.mainlayout, new ProjectListFragment_()).addToBackStack("sometag").commit();
    }

    private void setHomeButtonState(boolean b) {
        getActionBar().setDisplayHomeAsUpEnabled(b);
        getActionBar().setDisplayShowHomeEnabled(b);
    }

    public void showLogFragment(){
        getFragmentManager().beginTransaction().replace(R.id.mainlayout, new LogFragment_()).commit();
    }

    @Override
    public void OnFinished() {
        getFragmentManager().popBackStack();
    }
}

package com.tokko.cameandwentv2;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.tokko.cameandwentv2.log.LogFragment_;
import com.tokko.cameandwentv2.project.ProjectListFragment_;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.mainactivity)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends Activity {
    @ViewById
    Toolbar toolbar;

    @Bean
    ProjectRepository projectRepository;

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
}

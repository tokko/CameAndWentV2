package com.tokko.cameandwentv2.project;


import android.app.ListFragment;
import android.media.projection.MediaProjection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.events.EventProjectAdded;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.UUID;

@EFragment
@OptionsMenu(R.menu.menu_project)
public class ProjectFragment extends ListFragment{

    @ViewById
    ListView list;

    ArrayAdapter<Project> adapter;
    @Bean
    ProjectRepository projectRepository;

    @Bean
    OttoBus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @AfterInject
    public void initBus(){
        bus.register(this);
    }

    @Subscribe
    public void projectAdded(EventProjectAdded event){
        Project project = event.getProject();
        adapter.add(project);
        adapter.notifyDataSetChanged();
    }

    @OptionsItem(R.id.addProject)
    public void addProject(){
        projectRepository.insert(new Project(UUID.randomUUID().toString(), 0, 0));
    }
    @Override
    public void onResume() {
        super.onResume();
        new ProjectLoader().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    public class ProjectLoader extends AsyncTask<Void, Void, List<Project>> {

        @Override
        protected List<Project> doInBackground(Void... voids) {
            return projectRepository.readAll();
        }

        @Override
        protected void onPostExecute(List<Project> projects) {
            super.onPostExecute(projects);
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, projects);
            setListAdapter(adapter);
        }
    }
}

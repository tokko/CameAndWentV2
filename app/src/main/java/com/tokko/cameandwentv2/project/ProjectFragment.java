package com.tokko.cameandwentv2.project;


import android.app.ListFragment;
import android.media.projection.MediaProjection;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment
public class ProjectFragment extends ListFragment{

    @ViewById
    ListView list;

    ArrayAdapter<Project> adapter;
    @Bean
    ProjectRepository projectRepository;

    @Override
    public void onResume() {
        super.onResume();
        new ProjectLoader().execute();
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

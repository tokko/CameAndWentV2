package com.tokko.cameandwentv2.project;


import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.media.projection.MediaProjection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.events.EventProjectAdded;
import com.tokko.cameandwentv2.events.EventProjectDeleted;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment
@OptionsMenu(R.menu.menu_project)
public class ProjectListFragment extends ListFragment{

    @ViewById
    ListView list;

    ProjectListAdapter adapter;
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

    @Subscribe
    public void projectDeleted(EventProjectDeleted event){
        adapter.remove(event.getProject());
        adapter.notifyDataSetChanged();
    }

    @OptionsItem(R.id.addProject)
    public void addProject(){
        new ProjectDialogFragment_().show(getFragmentManager(), "sometag");
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
            adapter = new ProjectListAdapter(getActivity(), projects);
            setListAdapter(adapter);
            if(adapter.isEmpty())
                new ProjectDialogFragment_().show(getFragmentManager(), "sometag");
        }
    }

    public class ProjectListAdapter extends ArrayAdapter<Project>{

        private Context context;

        public ProjectListAdapter(@NonNull Context context, List<Project> projects) {
            super(context, 0, projects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ProjectView projectView;
            if(convertView == null) projectView = ProjectView_.build(context);
            else projectView = (ProjectView) convertView;
            projectView.bind(getItem(position));
            return projectView;
        }
    }
}

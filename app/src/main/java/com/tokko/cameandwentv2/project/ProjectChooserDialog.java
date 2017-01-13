package com.tokko.cameandwentv2.project;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by andre on 13/01/2017.
 */

@EFragment(R.layout.projectchooserdialog)
public class ProjectChooserDialog extends DialogFragment implements AdapterView.OnItemClickListener {

    @ViewById
    ListView list;

    @Bean
    ProjectRepository projectRepository;

    ArrayAdapter<Project> adapter;
    private ProjectClickedListener projectClickListener;

    @AfterInject
    public void setListItemClickListener(){
        list.setOnItemClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        projectClickListener = (ProjectClickedListener) getActivity();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1);
        new ProjectLoader().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        projectClickListener.projectClickedListener(adapter.getItem(i).getId());
    }

    public class ProjectLoader extends AsyncTask<Void, Void, List<Project>>{

        @Override
        protected List<Project> doInBackground(Void... voids) {
            return projectRepository.readAll();
        }

        @Override
        protected void onPostExecute(List<Project> projects) {
            super.onPostExecute(projects);
            adapter.addAll(projects);
            adapter.notifyDataSetChanged();
        }
    }

    public interface ProjectClickedListener{
        void projectClickedListener(Long id);
    }
}

package com.tokko.cameandwentv2.resourceaccess;

import android.content.Context;

import com.tokko.cameandwentv2.events.EventProjectAdded;
import com.tokko.cameandwentv2.events.EventProjectDeleted;
import com.tokko.cameandwentv2.project.Project;
import com.tokko.cameandwentv2.project.ProjectDao;

import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class ProjectRepository extends BaseRepository {


    public ProjectRepository(Context context) {
        super(context);

    }

    public ProjectDao getDao(){
        return daoSession.getProjectDao();
    }
    public List<Project> readAll() {
        return getDao().loadAll();
    }

    public void insert(Project project) {
        getDao().insert(project);
        bus.post(new EventProjectAdded(project));
    }

    public void remove(Project project) {
        getDao().delete(project);
        bus.post(new EventProjectDeleted(project));
    }
}

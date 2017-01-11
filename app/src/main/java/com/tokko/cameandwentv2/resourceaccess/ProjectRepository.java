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

    private final ProjectDao projectDao;

    public ProjectRepository(Context context) {
        super(context);
        projectDao = daoSession.getProjectDao();

    }

    public List<Project> readAll() {
        return projectDao.loadAll();
    }

    public void insert(Project project) {
        projectDao.insert(project);
        bus.post(new EventProjectAdded(project));
    }

    public void remove(Project project) {
        projectDao.delete(project);
        bus.post(new EventProjectDeleted(project));
    }
}

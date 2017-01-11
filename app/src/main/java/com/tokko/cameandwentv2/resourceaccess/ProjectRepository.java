package com.tokko.cameandwentv2.resourceaccess;

import android.content.Context;

import com.tokko.cameandwentv2.events.EventProjectAdded;
import com.tokko.cameandwentv2.events.OttoBus;
import com.tokko.cameandwentv2.project.Project;
import com.tokko.cameandwentv2.project.ProjectDao;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
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
}

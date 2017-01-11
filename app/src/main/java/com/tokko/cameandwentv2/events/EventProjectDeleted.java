package com.tokko.cameandwentv2.events;

import com.tokko.cameandwentv2.project.Project;

/**
 * Created by andre on 11/01/2017.
 */

public class EventProjectDeleted {
    private Project project;

    public EventProjectDeleted(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}

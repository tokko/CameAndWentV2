package com.tokko.cameandwentv2.events;

import com.tokko.cameandwentv2.project.Project;

public class EventProjectAdded {

    private Project project;

    public EventProjectAdded(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}

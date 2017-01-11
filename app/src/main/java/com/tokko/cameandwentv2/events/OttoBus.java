package com.tokko.cameandwentv2.events;

import com.squareup.otto.Bus;
import com.tokko.cameandwentv2.project.Project;

import org.androidannotations.annotations.EBean;

/**
 * Created by Andreas on 28/07/2016.
 */

// Declare the bus as an enhanced bean
@EBean(scope = EBean.Scope.Singleton)
public class OttoBus extends Bus {

    /**
     * Created by andre on 11/01/2017.
     */

    static class EventProjectAdded {
        private Project project;

        public EventProjectAdded(Project project) {
            this.project = project;
        }

        public Project getProject() {
            return project;
        }
    }
}
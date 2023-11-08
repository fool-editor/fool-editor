package com.ooqn.core.event;

import com.ooqn.core.project.Project;

public class OpenProjectEvent extends Event{
    public final Project project;

    public OpenProjectEvent(Project project) {
        super(null);
        this.project = project;
    }
}

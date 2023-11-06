package com.ooqn.core.event;

import com.ooqn.core.project.Project;

public class OpenProjectEvent {
    public final Project project;

    public OpenProjectEvent(Project project) {
        this.project = project;
    }
}

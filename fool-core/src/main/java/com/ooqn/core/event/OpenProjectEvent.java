package com.ooqn.core.event;

import com.ooqn.core.project.Project;

/**
 * 打开一个项目
 */
public class OpenProjectEvent extends Event{
    public final Project project;

    public OpenProjectEvent(Project project) {
        super(null);
        this.project = project;
    }
}

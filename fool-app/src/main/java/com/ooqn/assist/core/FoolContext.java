package com.ooqn.assist.core;


import com.ooqn.core.project.Project;
import com.ooqn.modules.SimpleJfxApplication;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import lombok.Getter;

public class FoolContext {

    @Getter
    private static Project project;
    @Getter
    private static FoolContextWindow foolContextWindow;

    public static void init(Project project,FoolContextWindow foolContextWindow){
        FoolContext.project=project;
        FoolContext.foolContextWindow=foolContextWindow;
    }


}

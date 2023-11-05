package com.ooqn.assist.core;


import com.ooqn.core.project.Project;
import javafx.scene.control.TabPane;
import lombok.Getter;

public class FoolContext {

    @Getter
    private static Project project;
    private static FoolContextInitializable foolContextInitializable;



    public static void init(Project project,FoolContextInitializable foolContextInitializable){
        FoolContext.project=project;
        FoolContext.foolContextInitializable=foolContextInitializable;
    }

    public static TabPane getBodyTop() {
       return foolContextInitializable.getEditor();
    }


}

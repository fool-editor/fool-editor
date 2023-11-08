package com.ooqn.assist.core;


import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.project.Project;
import com.ooqn.modules.SimpleJfxApplication;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import lombok.Getter;

public class FoolContext {

    @Getter
    private static Project project;
    @Getter
    private static FoolContextWindow foolContextWindow;
    @Getter
    private static EditorJmeApplication editorJmeApplication;

    public static void init(Project project,FoolContextWindow foolContextWindow){
        FoolContext.project=project;
        FoolContext.foolContextWindow=foolContextWindow;
        FoolContext.editorJmeApplication= foolContextWindow.getEditorJmeApplication();
    }
    public static void runJmeThread(Runnable runnable){
        Thread thread = Thread.currentThread();
        Thread jmeThread = foolContextWindow.getEditorJmeApplication().getThread();
        if(jmeThread==thread){
            runnable.run();
        }else {
            foolContextWindow.getEditorJmeApplication().enqueue(runnable);
        }
    }
    public static void runFxThread(Runnable runnable){
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        }else {
            Platform.runLater(runnable);
        }
    }
}

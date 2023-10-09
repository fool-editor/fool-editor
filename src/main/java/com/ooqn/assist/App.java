package com.ooqn.assist;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.plugin.ConsolePlugin;
import com.ooqn.assist.plugin.ExplorerPlugin;
import com.ooqn.assist.plugin.ToolPlugin;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FoolContext.stage = stage;
        stage.setMaximized(true);
        stage.setTitle("fool-editor");
        stage.setScene(FoolContext.scene);
        initEditor();
        initPlugin();
        stage.show();
    }

    private void initEditor() {
        FoolContext.stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            FoolContext.main.setMinHeight(newValue.doubleValue() - 80);
        });
    }

    /**
     * 初始化所有插件
     */
    public static void initPlugin(){
        FoolContext.getPluginManage().addPlugin(new ExplorerPlugin());
        FoolContext.getPluginManage().addPlugin(new ToolPlugin());
        FoolContext.getPluginManage().addPlugin(new ConsolePlugin());
    }

    public static void run(String[] args) {
        launch(args);
    }
}

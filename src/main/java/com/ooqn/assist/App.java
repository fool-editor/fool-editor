package com.ooqn.assist;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.plugin.ConsolePlugin;
import com.ooqn.assist.plugin.ExplorerPlugin;
import com.ooqn.assist.plugin.FileMenuPlugin;
import com.ooqn.assist.plugin.ToolPlugin;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FoolContext.setStage(stage);
        stage.setTitle("fool-editor");
        stage.setScene(FoolContext.getScene());
        initEditor();
        initPlugin();
        // 设置窗口最大化
        stage.setMaximized(true);
        stage.show();
    }

    private void initEditor() {
        FoolContext.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
            FoolContext.getMain().setMinHeight(newValue.doubleValue() - 80);
        });
    }

    /**
     * 初始化所有插件
     */
    public static void initPlugin(){
        FoolContext.getPluginManage().addPlugin(new ExplorerPlugin());
        FoolContext.getPluginManage().addPlugin(new ToolPlugin());
        FoolContext.getPluginManage().addPlugin(new ConsolePlugin());
        FoolContext.getPluginManage().addPlugin(new FileMenuPlugin());
    }

    public static void run(String[] args) {
        launch(args);
    }
}

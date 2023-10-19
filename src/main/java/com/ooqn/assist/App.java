package com.ooqn.assist;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.module.ConsoleModule;
import com.ooqn.assist.module.ExplorerModule;
import com.ooqn.assist.module.FileMenuModule;
import com.ooqn.assist.module.ToolModule;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FoolContext.setStage(stage);
        stage.setTitle("fool-editor");
        stage.setScene(FoolContext.getScene());
        initEditor();
        initModule();
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
    public static void initModule(){
        FoolContext.getModuleManage().addModule(new ToolModule());
        FoolContext.getModuleManage().addModule(new ConsoleModule());
        FoolContext.getModuleManage().addModule(new FileMenuModule());
    }

    public static void run(String[] args) {
        launch(args);
    }
}

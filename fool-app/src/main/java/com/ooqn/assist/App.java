package com.ooqn.assist;

//import com.ooqn.assist.component.ConsoleComponent;
//import com.ooqn.assist.component.FileMenuComponent;
//import com.ooqn.assist.component.JmeComponent;
//import com.ooqn.assist.component.ToolComponent;
//import com.ooqn.assist.core.FoolContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("fool-editor");
//        stage.setScene(FoolContext.getScene());
        initEditor();
        initModule();
        // Max Window
        stage.setMaximized(true);
        stage.show();
    }

    private void initEditor() {
//        FoolContext.getStage().heightProperty().addListener((observable, oldValue, newValue) -> {
//            FoolContext.getMain().setMinHeight(newValue.doubleValue() - 80);
//        });
    }

    /**
     * Init Module
     */
    public static void initModule(){
//        FoolContext.getComponentManage().addAssembly(new ToolComponent());
//        FoolContext.getComponentManage().addAssembly(new ConsoleComponent());
//        FoolContext.getComponentManage().addAssembly(new JmeComponent());
//        FoolContext.getComponentManage().addAssembly(new FileMenuComponent());
    }

    public static void run(String[] args) {
        launch(args);
    }
}

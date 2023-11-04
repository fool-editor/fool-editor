package com.ooqn.assist;

import com.ooqn.assist.component.JmeComponent;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FoolContext.setStage(stage);

        // 加载FXML文件

        FXMLLoader fxmlLoader = new FXMLLoader(App2.class.getClassLoader().getResource("mainView.fxml"));
        Parent root = fxmlLoader.load();
        MainViewController controller = fxmlLoader.getController();
        FoolContext.init(controller);
        Scene scene = new Scene(root);
        FoolContext.setScene(scene);

        stage.setTitle("fool-editor");
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(720);
        stage.show();
//        initEditor();
        initModule();
        // Max Window
//        stage.setMaximized(true);
    }

    private void initEditor() {

    }

    /**
     * Init Module
     */
    public static void initModule(){
//        FoolContext.getComponentManage().addAssembly(new ToolComponent());
//        FoolContext.getComponentManage().addAssembly(new ConsoleComponent());
//        FoolContext.getComponentManage().addAssembly(new JmeComponent());
//        FoolContext.getComponentManage().addAssembly(new FileMenuComponent());

        JmeComponent jmeComponent = new JmeComponent();
        jmeComponent.init();
    }

    public static void run(String[] args) {
        launch(args);
    }
}

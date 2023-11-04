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

        // 加载FXML文件
        FXMLLoader fxmlLoader = new FXMLLoader(App2.class.getClassLoader().getResource("projectManagerView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("fool-editor");
        stage.setScene(scene);
        stage.show();
        initModule();
    }

    private void initEditor() {

    }

    /**
     * Init Module
     */
    public static void initModule(){

    }

    public static void run(String[] args) {
        launch(args);
    }
}

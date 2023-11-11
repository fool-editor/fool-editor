package com.ooqn.assist;

import com.ooqn.core.plugin.PluginManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 加载FXML文件
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource("projectManagerView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("fool-editor");
        stage.setScene(scene);
        stage.show();
        initPlugin();
    }

    private void initPlugin() throws IOException {
        PluginManager.init();
    }

    public static void run(String[] args) {
        launch(args);
    }
}

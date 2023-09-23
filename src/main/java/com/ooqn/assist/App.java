package com.ooqn.assist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TransformController.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("fxml窗体");
        stage.setScene(scene);
        stage.show();
    }
}

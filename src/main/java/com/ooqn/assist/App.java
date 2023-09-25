package com.ooqn.assist;

import com.ooqn.assist.core.MainContext;
import com.ooqn.assist.core.NewTag;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;
        stage.setWidth(0);
        stage.setHeight(0);
        stage.setMaximized(true);
        

        VBox vBox = new VBox();

        createFloot();
        createMainBody();
        createMenuBar();

        vBox.getChildren().add(0, MainContext.menuBar);
        vBox.getChildren().add(1, MainContext.createMainBody);
        vBox.getChildren().add(2, MainContext.createFloot);

        Scene scene = new Scene(vBox);
        stage.setTitle("TestDemo");
        stage.setScene(scene);

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            
        });
        stage.show();
    }

    public static void createMenuBar() {
        MainContext.menuBar = new MenuBar();
        MainContext.menuBar.getMenus().add(0, new Menu("File"));
        MainContext.menuBar.getMenus().add(0, new Menu("Edit"));
        MainContext.menuBar.getMenus().add(0, new Menu("Help"));
    }

    public static void createMainBody() {

        // 左边
        Accordion leftTop = new Accordion();
        Accordion leftDown = new Accordion();
        leftTop.setMinHeight(28);
        leftDown.setMinHeight(28);
        
        SplitPane leftSplitPane = new SplitPane();
        leftSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        leftSplitPane.getItems().add(0, leftTop);
        leftSplitPane.getItems().add(1, leftDown);
        leftSplitPane.setMaxWidth(200);
        leftSplitPane.setMinWidth(200);

        // 右边
        Accordion rightTop = new Accordion();
        Accordion rightDown = new Accordion();
        rightTop.setMinHeight(28);
        rightDown.setMinHeight(28);

        SplitPane rightSplitPane = new SplitPane();
        rightSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        rightSplitPane.getItems().add(0, rightTop);
        rightSplitPane.getItems().add(1, rightDown);
        rightSplitPane.setMaxWidth(200);
        rightSplitPane.setMinWidth(200);
        
        // 中间部分
        SplitPane mainSplitPane = new SplitPane();
        mainSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        
        TabPane tabTop = new TabPane(new NewTag("Hello.java"),new NewTag("Box.j3o"));
        TabPane tabDown = new TabPane(new NewTag("Console"),new NewTag("Debug"));

        tabTop.setMinHeight(28);
        tabDown.setMinHeight(28);
        mainSplitPane.getItems().addAll(tabTop,tabDown);

        // 注入
        MainContext.createMainBody = new SplitPane();
        MainContext.createMainBody.getItems().add(0, leftSplitPane);
        MainContext.createMainBody.getItems().add(1, mainSplitPane);
        MainContext.createMainBody.getItems().add(2, rightSplitPane);


        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            MainContext.createMainBody.setMinHeight(newValue.doubleValue() - 80);
        });

    }

    public static void createFloot() {
        MainContext.createFloot= new HBox(new Label("hello"));
        MainContext.createFloot.setPrefHeight(50);
    }

    public static void run(String[] args) {
        launch(args);
    }
}

package com.ooqn.assist;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.assist.plugin.ConsolePlugin;
import com.ooqn.assist.plugin.ExplorerPlugin;
import com.ooqn.assist.plugin.ToolPlugin;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;
        stage.setMaximized(true);
        

        VBox vBox = new VBox();

        createFloot();
        createMainBody();
        createMenuBar();

        vBox.getChildren().add(0, FoolContext.MainLayout.menu);
        vBox.getChildren().add(1, FoolContext.MainLayout.createMainBody);
        vBox.getChildren().add(2, FoolContext.MainLayout.createFloot);

        Scene scene = new Scene(vBox);
        stage.setTitle("TestDemo");
        stage.setScene(scene);

        stage.show();
    }

    public static void createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(0, new Menu("File"));
        menuBar.getMenus().add(0, new Menu("Edit"));
        menuBar.getMenus().add(0, new Menu("Help"));
        menuBar.minWidth(500);

        FoolContext.MainLayout.menu = menuBar;
    }

    public static void createMainBody() {

        // 左边
        Accordion leftTop = new Accordion();
        Accordion leftDown = new Accordion();
        leftTop.setMinHeight(28);
        leftDown.setMinHeight(28);
        FoolContext.MainLayout.leftTop = leftTop; 
        FoolContext.MainLayout.leftDown = leftDown;
        
        
        SplitPane leftSplitPane = new SplitPane();
        leftSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        leftSplitPane.getItems().add(0, FoolContext.MainLayout.leftTop);
        leftSplitPane.getItems().add(1, FoolContext.MainLayout.leftDown);
        leftSplitPane.setMaxWidth(200);
        leftSplitPane.setMinWidth(200);
        FoolContext.MainLayout.leftSplitPane = leftSplitPane;

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
        
        TabPane tabTop = new TabPane(new FoolTab("Hello.java"),new FoolTab("Box.j3o"));
        TabPane tabDown = new TabPane();

        tabTop.setMinHeight(28);
        tabDown.setMinHeight(28);
        mainSplitPane.getItems().addAll(tabTop,tabDown);
        FoolContext.MainLayout.tabDown = tabDown;

       
        // 注入
        SplitPane createMainBody = new SplitPane();
        createMainBody.getItems().add(0, leftSplitPane);
        createMainBody.getItems().add(1, mainSplitPane);
        createMainBody.getItems().add(2, rightSplitPane);
        FoolContext.MainLayout.createMainBody = createMainBody;


        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            createMainBody.setMinHeight(newValue.doubleValue() - 80);
        });

        ExplorerPlugin explorerPlugin = new ExplorerPlugin();
        explorerPlugin.init();

        ToolPlugin toolPlugin = new ToolPlugin();
        toolPlugin.init();

        ConsolePlugin consolePlugin = new ConsolePlugin();
        consolePlugin.init();
    }

    public static void createFloot() {
        FoolContext.MainLayout.createFloot= new HBox(new Label("hello"));
        FoolContext.MainLayout.createFloot.setPrefHeight(50);
    }

    public static void run(String[] args) {
        launch(args);
    }
}

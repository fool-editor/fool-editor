package com.ooqn.assist;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.assist.plugin.ConsolePlugin;
import com.ooqn.assist.plugin.ExplorerPlugin;
import com.ooqn.assist.plugin.ToolPlugin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        FoolContext.stage = stage;
        stage.setMaximized(true);
        stage.setTitle("TestDemo");
        stage.setScene(loadScene());
        initPlugin();
        stage.show();
    }

    /**
     * 加载Scene
     * @return
     */
    public static Scene loadScene(){
        VBox vBox = new VBox();
        createFloot();
        createMainBody();
        vBox.getChildren().add(0, FoolContext.Layout.menu);
        vBox.getChildren().add(1, FoolContext.Layout.mainBody);
        vBox.getChildren().add(2, FoolContext.Layout.createFloot);
        return new Scene(vBox);
    }

    /**
     * 初始化所有插件
     */
    public static void initPlugin(){
        FoolContext.getPluginManage().addPlugin(new ExplorerPlugin());
        FoolContext.getPluginManage().addPlugin(new ToolPlugin());
        FoolContext.getPluginManage().addPlugin(new ConsolePlugin());
    }


    public static void createMainBody() {

        // 左边
        Accordion left = new Accordion();
        left.setMinWidth(200);
        left.setMaxWidth(200);

        FoolContext.Layout.leftBody.getItems().add(left);
        FoolContext.Layout.leftBody.setOrientation(javafx.geometry.Orientation.VERTICAL);
        FoolContext.Layout.rightBody.setOrientation(javafx.geometry.Orientation.VERTICAL);

        FoolContext.Layout.leftBody.setMaxWidth(200);

        
        // 右边
        Accordion right = new Accordion();
        right.setMaxWidth(200);
        right.setMinWidth(200);

        // 中间部分
        SplitPane mainSplitPane = new SplitPane();
        mainSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        
        TabPane tabTop = new TabPane(new FoolTab("Hello.java"),new FoolTab("Box.j3o"));
        TabPane tabDown = new TabPane();

        tabTop.setMinHeight(28);
        tabDown.setMinHeight(28);
        mainSplitPane.getItems().addAll(tabTop,tabDown);
        FoolContext.Layout.tabDown = tabDown;


        

    }

    public static void createFloot() {
        FoolContext.Layout.createFloot= new HBox(new Label("hello"));
        FoolContext.Layout.createFloot.setPrefHeight(50);
    }

    public static void run(String[] args) {
        launch(args);
    }
}

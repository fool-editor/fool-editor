package com.ooqn.assist.core;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FoolContext {

    public static Stage stage;

    public static Scene scene;

    public static Map<String, Map<String, Object>> pluginData = new HashMap<>();

    private static PluginManage pluginManage = new PluginManage();

    public static PluginManage getPluginManage() {
        return pluginManage;
    }

    public static MenuBar menu = new MenuBar();

    public static SplitPane main = new SplitPane();

    public static SplitPane body = new SplitPane();

    public static SplitPane left = new SplitPane();

    public static SplitPane right = new SplitPane();

    public static HBox createFloot;

    public static TabPane tabDown;

    static {

        createFloot = new HBox(new Label("hello"));
        createFloot.setPrefHeight(50);

        left.setOrientation(javafx.geometry.Orientation.VERTICAL); // 设置为垂直分割
        body.setOrientation(javafx.geometry.Orientation.VERTICAL); // 设置为垂直分割
        right.setOrientation(javafx.geometry.Orientation.VERTICAL); // 设置为垂直分割


        main = new SplitPane();
        main.getItems().add(0, left);
        main.getItems().add(1, body);
        main.getItems().add(2, right);
        main.setPrefHeight(500);

        menu.getMenus().add(new Menu("File"));
        
        scene = new Scene(new VBox(menu, main, createFloot));

    }

}

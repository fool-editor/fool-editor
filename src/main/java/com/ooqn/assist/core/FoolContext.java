package com.ooqn.assist.core;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FoolContext {

    public static Stage stage;

    // 当前工作空间
    public static String projectPath = "C:\\Users\\bobcbui\\Desktop\\jmonkeyengine-master";

    public static Map<String,Map<String,Object>> pluginData = new HashMap<>();

    private static PluginManage pluginManage = new PluginManage();

    public static PluginManage getPluginManage(){
        return pluginManage;
    }

    public class Layout{

        public static MenuBar menu = new MenuBar();

        public static SplitPane mainBody = new SplitPane();

        public static SplitPane leftBody = new SplitPane();

        public static SplitPane rightBody = new SplitPane();

        public static HBox createFloot;

        public static TabPane tabDown;

        static{
            
            Layout.createFloot= new HBox(new Label("hello"));
            Layout.createFloot.setPrefHeight(50);

            SplitPane createMainBody = new SplitPane();
            createMainBody.getItems().add(0, FoolContext.Layout.leftBody);
            createMainBody.getItems().add(1, mainBody);
            createMainBody.getItems().add(2, rightBody);
            
            stage.heightProperty().addListener((observable, oldValue, newValue) -> {
                createMainBody.setMinHeight(newValue.doubleValue() - 80);
            });
    
        }
    }
    
}

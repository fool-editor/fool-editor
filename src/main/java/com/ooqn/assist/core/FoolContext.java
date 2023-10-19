package com.ooqn.assist.core;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FoolContext {

    private static String workPath;

    private static Stage stage;

    private static Scene scene;

    private static Map<String, Map<String, Object>> moduleData = new HashMap<>();

    private static ModuleManage moduleManage = new ModuleManage();

    private static MenuBar menu = new MenuBar();

    private static SplitPane main = new SplitPane();

    private static SplitPane body = new SplitPane();

    private static TabPane bodyTop = new TabPane();

    private static TabPane bodyDown = new TabPane();

    private static SplitPane left = new SplitPane();

    private static SplitPane right = new SplitPane();

    private static HBox createFloot;

    private static TabPane tabDown;

    private static boolean b = false;

    public static ModuleManage getModuleManage() {
        return moduleManage;
    }

    static {

        createFloot = new HBox(new Label("hello"));
        createFloot.setPrefHeight(50);

        left.setOrientation(javafx.geometry.Orientation.VERTICAL); // 设置为垂直分割
        right.setOrientation(javafx.geometry.Orientation.VERTICAL); // 设置为垂直分割
        body.setOrientation(javafx.geometry.Orientation.VERTICAL); // 设置为垂直分割

        body.getItems().addAll(bodyTop, bodyDown);

     
        //left.setPrefWidth(200);
        //left.setMinWidth(200);

        //right.setMaxWidth(200);
        //right.setMinWidth(200);

        main = new SplitPane();
        main.getItems().add(0, left);
        main.getItems().add(1, body);
        main.getItems().add(2, right);

        left.setMaxWidth(200);
        left.setMinWidth(200);

        
        scene = new Scene(new VBox(menu, main, createFloot));

        
        main.getDividers().get(0).positionProperty().addListener((obs, oldValue, newValue) -> {
            // 在这里根据新的分隔条位置来调整子面板的宽度
            if(b){
                left.setMaxWidth(9999);
                left.setMinWidth(100);
                main.getDividers().get(0).setPosition(newValue.doubleValue());
            }else{
                b = true;
            }
        });

    }

    

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        FoolContext.stage = stage;
    }

    public static Scene getScene() {
        return scene;
    }

    public static void setScene(Scene scene) {
        FoolContext.scene = scene;
    }

    public static Map<String, Map<String, Object>> getModuleData() {
        return moduleData;
    }

    public static void setModuleData(Map<String, Map<String, Object>> moduleData) {
        FoolContext.moduleData = moduleData;
    }

    public static void setModuleManage(ModuleManage moduleManage) {
        FoolContext.moduleManage = moduleManage;
    }

    public static MenuBar getMenu() {
        return menu;
    }

    public static void setMenu(MenuBar menu) {
        FoolContext.menu = menu;
    }

    public static SplitPane getMain() {
        return main;
    }

    public static void setMain(SplitPane main) {
        FoolContext.main = main;
    }

    public static SplitPane getLeft() {
        return left;
    }

    public static void setLeft(SplitPane left) {
        FoolContext.left = left;
    }

    public static SplitPane getRight() {
        return right;
    }

    public static void setRight(SplitPane right) {
        FoolContext.right = right;
    }

    public static HBox getCreateFloot() {
        return createFloot;
    }

    public static void setCreateFloot(HBox createFloot) {
        FoolContext.createFloot = createFloot;
    }

    public static TabPane getTabDown() {
        return tabDown;
    }

    public static void setTabDown(TabPane tabDown) {
        FoolContext.tabDown = tabDown;
    }

    public static String getWorkPath() {
        return workPath;
    }

    public static void setWorkPath(String workPath) {
        FoolContext.workPath = workPath;
    }

    public static boolean isB() {
        return b;
    }

    public static void setB(boolean b) {
        FoolContext.b = b;
    }

    public static TabPane getBodyTop() {
        return bodyTop;
    }

    public static void setBodyTop(TabPane bodyTop) {
        FoolContext.bodyTop = bodyTop;
    }

    public static TabPane getBodyDown() {
        return bodyDown;
    }

    public static void setBodyDown(TabPane bodyDown) {
        FoolContext.bodyDown = bodyDown;
    }

    

    

}

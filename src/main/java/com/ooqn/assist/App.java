package com.ooqn.assist;

import com.ooqn.assist.tab.TextTab;
import com.ooqn.assist.tab.WebTab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;
        stage.setMaximized(true);
        stage.setWidth(1500);
        stage.setHeight(800);

        VBox vBox = new VBox();

        vBox.getChildren().add(0, createMenuBar());
        vBox.getChildren().add(1, createMainBody());
        vBox.getChildren().add(2, createFloot());

        Scene scene = new Scene(vBox);
        stage.setTitle("TestDemo");
        stage.setScene(scene);

        
        stage.show();
    }

    public static MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(0, new Menu("File"));
        menuBar.getMenus().add(0, new Menu("Edit"));
        menuBar.getMenus().add(0, new Menu("Help"));
        return menuBar;
    }

    public static SplitPane createMainBody() {

        TabPane tabBody = createTabBody();

        SplitPane leftSplitPane = new SplitPane();

        Accordion leftDown = new Accordion();

        // top
        TitledPane sx = createTitledPane("目录", leftDown, leftSplitPane);
        Accordion leftTop = new Accordion(sx);
        leftTop.setExpandedPane(sx);

        // down
        TitledPane ml = createTitledPane("目录", leftDown, leftSplitPane);
        TitledPane gn = createTitledPane("功能", leftDown, leftSplitPane);
        leftDown.getPanes().add(0, ml);
        leftDown.getPanes().add(1, gn);
        leftDown.setExpandedPane(ml);

        leftSplitPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            leftDown.setPrefWidth(newValue.doubleValue());
        });

        leftSplitPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            leftDown.setPrefHeight(newValue.doubleValue());
        });

        leftSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        leftSplitPane.getItems().add(0, leftTop);
        leftSplitPane.getItems().add(1, leftDown);

        leftSplitPane.setMaxWidth(200);
        leftSplitPane.setMinWidth(200);

        SplitPane splitPane = new SplitPane();
        splitPane.setMinHeight(stage.getHeight() - 80);

        AnchorPane rightPane = new AnchorPane();
        rightPane.setMaxWidth(200);
        rightPane.setMinWidth(200);

        splitPane.getItems().add(0, leftSplitPane);
        splitPane.getItems().add(1, tabBody);
        splitPane.getItems().add(2, rightPane);


        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setMinHeight(newValue.doubleValue() - 80);
        });

        return splitPane;
    }

    public static TabPane createTabBody() {

        // 在 TabPane 上放 Tab
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color:#fffff0");

        tabPane.getTabs().addAll(new TextTab("tab1"), new TextTab("tab1"), new TextTab("tab1"), new TextTab("tab1"),
                new TextTab("tab1"), new TextTab("tab1"), new TextTab("tab1"), new TextTab("tab1"), new TextTab("tab1"),
                new TextTab("tab1"), new WebTab("webTable"), new TextTab("为什么你.java"));

        return tabPane;
    }

    public static HBox createFloot() {
        HBox hBox = new HBox(new Label("hello"));
        hBox.setPrefHeight(50);
        return hBox;
    }

    public static void run(String[] args) {
        launch(args);
    }

    private static TitledPane createTitledPane(String title, Accordion accordion, SplitPane p) {
        TitledPane pane = new TitledPane();
        pane.setText(title);

        int size = accordion.getPanes().size();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(size * 25);

        p.heightProperty().addListener((observable, oldValue, newValue) -> {
            anchorPane.setPrefHeight(newValue.doubleValue() - size * 25);
        });

        pane.setContent(anchorPane);

        return pane;
    }
}

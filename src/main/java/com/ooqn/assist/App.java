package com.ooqn.assist;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(0, new Menu("sdjfklsd"));

        stage.setWidth(800);
        stage.setHeight(600);

        VBox vBox = new VBox();

        SplitPane splitPane = new SplitPane();
        splitPane.setMinHeight(400 - 80);

        WebView createWebView = createWebView();

        // 在 TabPane 上放 Tab
        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color:#fffff0");

        Tab tab1 = new Tab("tab1");// tab无法设置宽高
        tab1.setStyle("-fx-min-width: 200px !important; -fx-max-width: 200px !important;");
        Tab tab2 = new Tab("tab2");
        Tab tab3 = new Tab("tab3");

        tabPane.getTabs().addAll(tab1, tab2, tab3);

        tab1.setContent(createWebView());

        ScrollPane scrollPane = new ScrollPane(tabPane);

        tabPane.setMinHeight(scrollPane.getHeight());
        tabPane.setMinWidth(scrollPane.getWidth());

        AnchorPane leftPane = new AnchorPane();
        leftPane.setMinWidth(200);
        leftPane.setMaxWidth(200);


        Accordion accordion = new Accordion();

        
        TitledPane titledPane1 = createTitledPane("asdfasdf",500);
        TitledPane titledPane2 = createTitledPane("asdfasdf",500);

        


        accordion.getPanes().addAll(titledPane1, titledPane2);

        accordion.getHeight();
        

        leftPane.widthProperty().addListener((observable, oldValue, newValue) ->{
            accordion.setMinWidth(newValue.doubleValue());
        });
       
        

        leftPane.getChildren().add(0, accordion);

        AnchorPane rightPane = new AnchorPane();
        rightPane.setMinWidth(200);
        rightPane.setMaxWidth(200);

        splitPane.getItems().add(0,leftPane );
        splitPane.getItems().add(1, scrollPane);
        splitPane.getItems().add(2, rightPane);

        scrollPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            createWebView.setMinHeight(scrollPane.getHeight());
            tabPane.setMinHeight(scrollPane.getHeight());
        });

        scrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            createWebView.setMinWidth(scrollPane.getWidth());
            tabPane.setMinWidth(scrollPane.getWidth());
        });

        HBox hBox = new HBox(new Label("hello"));
        hBox.setPrefHeight(50);

        vBox.getChildren().add(0, menuBar);
        vBox.getChildren().add(1, splitPane);
        vBox.getChildren().add(2, hBox);

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setMinHeight(newValue.doubleValue() - 80);
        });

        Scene scene = new Scene(vBox);
        stage.setTitle("fxml窗体");
        stage.setScene(scene);
        stage.show();
    }

    private static Accordion createHe(Accordion pane){
        Accordion accordion = new Accordion();
        pane.heightProperty().addListener((observable, oldValue, newValue) -> {
            accordion.setMinHeight(newValue.doubleValue());
        });
        return accordion;
    }

    public static void run(String[] args) {
        launch(args);
    }

    private static WebView createWebView() {
        WebView webView1 = new WebView();
        WebEngine webEngine1 = webView1.getEngine();
        webEngine1.load("https://baidu.com");
        return webView1;
    }

    private TitledPane createTitledPane(String title, double maxHeight) {
        TitledPane pane = new TitledPane();
        pane.setText(title);

        VBox content = new VBox();
        Rectangle rectangle = new Rectangle(200, maxHeight);
        rectangle.setFill(Color.LIGHTGRAY);
        content.getChildren().add(rectangle);

        pane.setContent(content);

        // 设置内容的最大高度
        content.setMaxHeight(maxHeight);

        return pane;
    }
}

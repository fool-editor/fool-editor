package com.ooqn.assist.tab;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebTab extends Tab {

    public WebTab(String tabName) {
        super(tabName, createWebView());
        Platform.runLater(() -> {
            double textWidth = new Text(tabName).getBoundsInLocal().getWidth();
            setStyle("-fx-pref-width: " + (textWidth + 30) + "px;");
        });
    }

    private static WebView createWebView() {
        WebView webView1 = new WebView();
        WebEngine webEngine1 = webView1.getEngine();
        webEngine1.load("https://baidu.com");
        return webView1;
    }
}

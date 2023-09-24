package com.ooqn.assist.tab;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;

public class HtmlTab extends Tab{

    public HtmlTab(String tabName){
        setContent(new HTMLEditor());
        setText(tabName);
        Platform.runLater(() -> {
            double textWidth = new Text(tabName).getBoundsInLocal().getWidth();
            setStyle("-fx-pref-width: " + (textWidth + 30) + "px;");
        });
    }
    
}

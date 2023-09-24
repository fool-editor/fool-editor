package com.ooqn.assist.tab;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class TextTab extends Tab{

    public TextTab(String tabName){
        setContent(new TextArea());
        setText(tabName);
        Platform.runLater(() -> {
            double textWidth = new Text(tabName).getBoundsInLocal().getWidth();
            setStyle("-fx-pref-width: " + (textWidth + 30) + "px;");
        });
    }
}

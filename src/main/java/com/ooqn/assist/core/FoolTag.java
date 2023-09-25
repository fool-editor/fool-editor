package com.ooqn.assist.core;

import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class FoolTag extends Tab{
    
    public FoolTag(String tabName){
        setText(tabName);
        Platform.runLater(() -> {
            double textWidth = new Text(tabName).getBoundsInLocal().getWidth();
            setStyle("-fx-pref-width: " + (textWidth + 30) + "px;");
        });
    }
}

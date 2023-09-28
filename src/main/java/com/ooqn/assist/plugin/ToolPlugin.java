package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class ToolPlugin implements Plugin {

    TitledPane titledPane;

    @Override
    public void init() {
        TitledPane titledPane = new TitledPane("工具", new Label("ksldjfksjdf"));
        FoolContext.MainLayout.leftDown.getPanes().add(titledPane);
    }

    @Override
    public void delete() {
        FoolContext.MainLayout.leftDown.getPanes().remove(titledPane);        
    }
}

package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class ToolPlugin implements Plugin {

    @Override
    public void init() {
        FoolContext.MainLayout.leftDown.getPanes().add(new TitledPane("工具", new Label("ksldjfksjdf")));
    }
}

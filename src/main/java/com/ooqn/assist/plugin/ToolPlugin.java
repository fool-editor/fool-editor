package com.ooqn.assist.plugin;

import com.ooqn.assist.core.MainContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class ToolPlugin implements Plugin {

    @Override
    public void init() {
        MainContext.leftDown.getPanes().add(new TitledPane("工具", new Label("ksldjfksjdf")));
    }

}

package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TitledPane;

public class ToolPlugin implements Plugin {

    TitledPane titledPane;

    @Override
    public void init() {
        TitledPane titledPane = new TitledPane("工具", new Label("ksldjfksjdf"));
        //FoolContext.Layout.leftBody.getItems().getPanes().add(titledPane);

        // Menu 
        Menu tool = new Menu("Tool");
        FoolContext.Layout.menu.getMenus().add(tool);
    }

    @Override
    public void delete() {
        //FoolContext.Layout.left.getPanes().remove(titledPane);        
    }
}

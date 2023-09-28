package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class ExplorerPlugin implements Plugin {

    @Override
    public void init() {
        TitledPane titledPane = new TitledPane("目录", new Label("ksldjfksjdf"));
        FoolContext.MainLayout.leftTop.getPanes().add(titledPane);
        
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        
    }
}

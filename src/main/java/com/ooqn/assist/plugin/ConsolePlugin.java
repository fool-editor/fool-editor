package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolOpenTab;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ConsolePlugin implements Plugin {

    @Override
    public void init() {
        FoolContext.MainLayout.tabDown.getTabs().add(new FoolTab("控制台"));

        FoolContext.openTab.put("txt", new FoolOpenTab() {

            @Override
            public Pane openTab(String name) {
                return new Pane(new Label("ksdjfkajsdf"));
            }
        });
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        
    }
}

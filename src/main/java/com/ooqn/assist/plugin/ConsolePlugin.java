package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.assist.core.Plugin;

public class ConsolePlugin implements Plugin {

    @Override
    public void init() {
        FoolContext.MainLayout.tabDown.getTabs().add(new FoolTab("控制台"));
    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub
        
    }
}

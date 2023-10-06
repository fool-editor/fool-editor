package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.assist.core.Plugin;

public class ConsolePlugin implements Plugin {

    @Override
    public void init() {
        FoolContext.Layout.tabDown.getTabs().add(new FoolTab("控制台"));
    }

    @Override
    public void delete() {
        
    }
}

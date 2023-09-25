package com.ooqn.assist.plugin;

import com.ooqn.assist.core.MainContext;
import com.ooqn.assist.core.NewTag;
import com.ooqn.assist.core.Plugin;

public class ConsolePlugin implements Plugin {

    @Override
    public void init() {
        MainContext.tabDown.getTabs().add(new NewTag("Console"));
    }

    
}

package com.ooqn.assist.plugin;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTag;
import com.ooqn.assist.core.Plugin;

public class ConsolePlugin implements Plugin {

    @Override
    public void init() {
        FoolContext.tabDown.getTabs().add(new FoolTag("Console"));
    }

    
}

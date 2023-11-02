package com.ooqn.assist.component;

import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.core.Component;

public class ConsoleComponent implements Component {

    @Override
    public void init() {
        FoolTab foolTab = new FoolTab("Console");
        FoolContext.getBodyDown().getTabs().add(foolTab);
    }

    @Override
    public void destroy() {

    }

    @Override
    public Map<String, Object> getData() {
        throw new UnsupportedOperationException("Unimplemented method 'getData'");
    }
}

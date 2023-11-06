package com.ooqn.core.plugin;

import com.ooqn.core.event.EditorEventBus;

public interface Plugin {

    void init(EditorEventBus eventBus);

    void destroy();
}

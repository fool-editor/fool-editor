package com.ooqn.core.plugin;

import com.google.common.eventbus.EventBus;
import com.ooqn.core.event.EditorEventBus;

public interface Plugin {

    void init(EventBus eventBus);

    void destroy();
}

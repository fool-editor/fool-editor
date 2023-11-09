package com.ooqn.core.event;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class EditorEventBus {
    public static final EventBus eventBus=new EventBus();

    public static void post(Object event) {
        log.info("post event:{}",event.getClass().getName());
        eventBus.post(event);
    }
    public static void register(Object event) {
        eventBus.register(event);
    }
}

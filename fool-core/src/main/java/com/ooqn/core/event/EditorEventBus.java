package com.ooqn.core.event;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class EditorEventBus extends EventBus {
    public static final EditorEventBus editorEventBus=new EditorEventBus();

    @Override
    public void post(Object event) {
        log.info("post event:{}",event.getClass().getName());
        super.post(event);
    }
}

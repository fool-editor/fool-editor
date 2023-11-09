package com.ooqn.core.event;

import com.ooqn.core.EditorJmeApplication;

/**
 * jme 启动完成
 */
public class JmeStartCompleteEvent extends Event {
    public JmeStartCompleteEvent(EditorJmeApplication editorJmeApplication) {
        super(editorJmeApplication);
    }
}

package com.ooqn.core.event;

import javafx.stage.Stage;

/**
 * 编辑器关闭事件
 *
 */
public class EditorCloseEvent extends Event{
    public EditorCloseEvent(Stage source) {
        super(source);
    }
}

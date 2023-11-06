package com.ooqn.assist.core;

import com.ooqn.core.EditorJmeApplication;
import com.ooqn.modules.SimpleJfxApplication;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

/**
 * 主窗口
 *
 * ------------------------------------------
 * |  TabPane1 |    TabPane3     |  TabPane5|
 * |           |                 |          |
 * |           |                 |          |
 * |           |                 |          |
 * |           |                 |          |
 * |           |                 |          |
 * |           |                 |          |
 * |           |                 |          |
 * |-----------|-----------------|          |
 * | TabPane2  |   TabPane4      |          |
 * |           |                 |          |
 * |           |                 |          |
 * |           |                 |          |
 * ------------------------------------------
 * | footerLabel                            |
 * ------------------------------------------


 */
public interface FoolContextWindow {

    TabPane getTabPane1();
    TabPane getTabPane2();
    TabPane getTabPane3();
    TabPane getTabPane4();
    TabPane getTabPane5();
    Label getFooterLabel();

    EditorJmeApplication getEditorJmeApplication();
}

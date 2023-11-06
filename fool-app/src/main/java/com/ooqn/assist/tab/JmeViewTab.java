package com.ooqn.assist.tab;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.modules.SimpleJfxApplication;
import com.ooqn.modules.jme.EditorFxImageView;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

/**
 * jme 主窗口显示视图
 */
public class JmeViewTab extends Tab {
    public JmeViewTab() {
        setText("JME");
        EditorFxImageView imageView = ((SimpleJfxApplication)FoolContext.getFoolContextWindow().getEditorJmeApplication()).getImageView();
        BorderPane pane = new BorderPane(imageView);
        this.setContent(pane);
        // 监听宽度发送时修改JME的宽度
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
            imageView.resize(newValue.doubleValue(), pane.getWidth());
        });
        // 监听高度发送时修改JME的高度
        pane.heightProperty().addListener((observable, oldValue, newValue) -> imageView.resize(pane.getHeight(), newValue.doubleValue()));
    }
}

package com.ooqn.assist.tab;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.util.SvgUtil;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.fx.Svg;
import com.ooqn.modules.FxJmeApplication;
import com.ooqn.modules.SimpleJfxApplication;
import com.ooqn.modules.jme.EditorFxImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;

/**
 * jme 主窗口显示视图
 */
@Slf4j
public class JmeViewTab extends Tab {
    private Node renderCanvas;

    public JmeViewTab() {
        setText("JME");
        EditorJmeApplication editorJmeApplication = FoolContext.getFoolContextWindow().getEditorJmeApplication();
        FxJmeApplication<Canvas> fxJmeApplication = (FxJmeApplication) editorJmeApplication;
        renderCanvas = fxJmeApplication.getImageView();
//        EditorFxImageView imageView = ((SimpleJfxApplication)FoolContext.getFoolContextWindow().getEditorJmeApplication()).getImageView();
        tabPaneProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.widthProperty().removeListener(this::reSize);
                oldValue.heightProperty().removeListener(this::reSize);
            }
            if (newValue != null) {
                newValue.widthProperty().addListener(this::reSize);
                newValue.heightProperty().addListener(this::reSize);
            }
            reSize();
        });
        this.setContent(renderCanvas);
    }

    private void reSize() {
        TabPane tabPane = getTabPane();
        if (renderCanvas instanceof Canvas) {
            ((Canvas) renderCanvas).setWidth(tabPane.getWidth());
            ((Canvas) renderCanvas).setHeight(tabPane.getHeight() - 30);
        } else {
            renderCanvas.prefWidth(tabPane.getWidth());
            renderCanvas.prefHeight(tabPane.getHeight() - 30);
        }
    }

    private void reSize(ObservableValue<? extends Number> observable, Number oldValue, Number newValuee) {
        reSize();
    }

}

package com.ooqn.assist.view;

import com.ooqn.assist.core.FoolContextWindow;
import com.ooqn.assist.tab.FileSystemTab;
import com.ooqn.core.EditorJmeApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, FoolContextWindow {

    /**
     * 编辑区域，主区域
     */
    @FXML
    @Getter
    public TabPane tabPane1;
    @FXML
    @Getter
    public TabPane tabPane2;
    @FXML
    @Getter
    public TabPane tabPane3;
    @FXML
    @Getter
    public TabPane tabPane4;
    @FXML
    @Getter
    public TabPane tabPane5;
    @FXML
    @Getter
    public Label footerLabel;
    @FXML
    public Pane rightPane;
    @FXML
    public Pane leftPane;


    private final EditorJmeApplication editorJmeApplication;

    public MainViewController(EditorJmeApplication editorJmeApplication) {
        this.editorJmeApplication = editorJmeApplication;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        new JmeComponent().init();
        Platform.runLater(() -> {
            // 界面渲染后要执行的代码 
            // 由于Javafx SplitPane 的一些缺陷设置了下面代码，目的是为了让左右两侧的宽度固定。
            leftPane.setMaxWidth(Double.MAX_VALUE);
            leftPane.setMinWidth(25);

            rightPane.setMaxWidth(Double.MAX_VALUE);
            rightPane.setMinWidth(25);
        });
    }

    @Override
    public EditorJmeApplication getEditorJmeApplication() {
        return editorJmeApplication;
    }
}

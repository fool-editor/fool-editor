package com.ooqn.assist.view;

import com.ooqn.assist.component.JmeComponent;
import com.ooqn.assist.core.FoolContextInitializable;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, FoolContextInitializable {

    /**
     * 编辑区域，主区域
     */
    @FXML
    public TabPane editorPane;

    /**
     * 控制台，问题，终端 等区域的父级
     */
    @FXML
    public TabPane panelPane;

    /**
     * 左菜单
     */
    @FXML
    public AnchorPane leftPane;

    /**
     * 右菜单
     */
    @FXML
    public AnchorPane rightPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JmeComponent().init();
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
    public TabPane getEditor() {
        return editorPane;
    }

    @Override
    public TabPane getPanel() {
        return panelPane;
    }
}

package com.ooqn.assist.view;

import cn.hutool.core.io.FileUtil;
import com.ooqn.assist.component.JmeComponent;
import com.ooqn.assist.core.FoolContext;

import com.ooqn.assist.core.FoolContextWindow;
import com.ooqn.assist.fx.control.FileTreeItem;
import com.ooqn.assist.tab.FileSystemTab;
import com.ooqn.assist.util.SvgUtil;
import com.ooqn.core.project.Project;
import com.ooqn.modules.SimpleJfxApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import lombok.Getter;

import java.io.File;
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

    @FXML
    public TreeView fileTree;


    private final SimpleJfxApplication simpleJfxApplication;

    public MainViewController(SimpleJfxApplication simpleJfxApplication) {
        this.simpleJfxApplication = simpleJfxApplication;
    }

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

        FileSystemTab fileSystemTab = new FileSystemTab();
        tabPane2.getTabs().add(fileSystemTab);
    }





    @Override
    public SimpleJfxApplication getSimpleJfxApplication() {
        return simpleJfxApplication;
    }
}

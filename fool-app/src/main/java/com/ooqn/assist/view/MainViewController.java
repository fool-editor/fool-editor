package com.ooqn.assist.view;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ReUtil;
import com.ooqn.assist.component.JmeComponent;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolContextInitializable;

import com.ooqn.assist.fx.control.FileTreeItem;
import com.ooqn.core.project.Project;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
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

    @FXML
    public TreeView fileTree;

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

        Project project = FoolContext.getProject();
        File srcJavaDir = project.getSrcJavaDir();
        File resourcesDir = project.getSrcResourcesDir();
        FileTreeItem srcJavaFileTreeItem = new FileTreeItem(srcJavaDir);
        FileTreeItem resourcesFileTreeItem = new FileTreeItem(resourcesDir);
        srcJavaFileTreeItem.setGraphic(new ImageView(new Image("icon/sourceRoot.png")));
        loopFileTree(srcJavaFileTreeItem,true);
        loopFileTree(resourcesFileTreeItem,false);
        resourcesFileTreeItem.setGraphic(new ImageView(new Image("icon/resourcesRoot.png")));
        TreeItem root = new TreeItem<>();
        root.getChildren().add(srcJavaFileTreeItem);
        root.getChildren().add(resourcesFileTreeItem);
        fileTree.setShowRoot(false);
        fileTree.setRoot(root);
    }


    private void loopFileTree(FileTreeItem prentTreeItem,boolean java){
        File prent = prentTreeItem.file;
        File[] files = prent.listFiles();
        for (File file : files) {
            FileTreeItem fileTreeItem = new FileTreeItem(file);
            prentTreeItem.getChildren().add(fileTreeItem);
            if(file.isDirectory()){
                if(java){
                    fileTreeItem.setGraphic(new ImageView(new Image("icon/package.png")));
                }else {
                    fileTreeItem.setGraphic(new ImageView(new Image("icon/folder.png")));
                }
            }else {
                String extName = FileUtil.extName(file);
                switch (extName){
                    case "java":
                        fileTreeItem.setGraphic(new ImageView(new Image("icon/file/java.png")));
                        break;
                    default:
                        fileTreeItem.setGraphic(new ImageView(new Image("icon/file/unknown.png")));
                }
            }
            if(file.isDirectory()){
                loopFileTree(fileTreeItem,java);
            }
        }
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

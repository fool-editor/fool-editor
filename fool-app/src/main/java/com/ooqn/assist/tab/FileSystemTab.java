package com.ooqn.assist.tab;

import cn.hutool.core.io.FileUtil;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.fx.control.FileTreeItem;
import com.ooqn.assist.util.SvgUtil;
import com.ooqn.core.project.Project;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

import java.io.File;

public class FileSystemTab extends Tab {

    private TreeView treeView;

    public FileSystemTab() {
        treeView = new TreeView();
        setContent(treeView);
        init();
        setText("文件系统");
    }

    private void init() {
        Project project = FoolContext.getProject();
        File srcJavaDir = project.getSrcJavaDir();
        File resourcesDir = project.getSrcResourcesDir();
        FileTreeItem srcJavaFileTreeItem = new FileTreeItem(srcJavaDir, true);
        FileTreeItem resourcesFileTreeItem = new FileTreeItem(resourcesDir, false);
        srcJavaFileTreeItem.setGraphic(new ImageView(new Image("icon/sourceRoot.png")));
        loopFileTree(srcJavaFileTreeItem);
        loopFileTree(resourcesFileTreeItem);
        resourcesFileTreeItem.setGraphic(new ImageView(new Image("icon/resourcesRoot.png")));
        TreeItem root = new TreeItem<>();
        root.getChildren().add(srcJavaFileTreeItem);
        root.getChildren().add(resourcesFileTreeItem);

        SVGPath refresh = SvgUtil.getSvg("icon/refresh.svg");
        ContextMenu contextMenu = new ContextMenu();
        //不知道为什么设置contextMenu 宽度不起作用。
        MenuItem refreshItem = new MenuItem("刷新           ");
        refreshItem.setGraphic(refresh);

        contextMenu.getItems().add(refreshItem);
        refreshItem.setOnAction(event -> {
            Object selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem instanceof FileTreeItem) {
                ((FileTreeItem) selectedItem).getChildren().clear();
                loopFileTree((FileTreeItem) selectedItem);
            }
        });
        treeView.setContextMenu(contextMenu);
        treeView.setShowRoot(false);
        treeView.setRoot(root);
    }

    private void loopFileTree(FileTreeItem prentTreeItem) {
        File prent = prentTreeItem.file;
        File[] files = prent.listFiles();
        for (File file : files) {
            FileTreeItem fileTreeItem = new FileTreeItem(file, prentTreeItem.java);
            prentTreeItem.getChildren().add(fileTreeItem);
            if (file.isDirectory()) {
                if (prentTreeItem.java) {
                    fileTreeItem.setGraphic(new ImageView(new Image("icon/package.png")));
                } else {
                    fileTreeItem.setGraphic(new ImageView(new Image("icon/folder.png")));
                }
            } else {
                String extName = FileUtil.extName(file);
                switch (extName) {
                    case "java":
                        fileTreeItem.setGraphic(new ImageView(new Image("icon/file/java.png")));
                        break;
                    default:
                        fileTreeItem.setGraphic(new ImageView(new Image("icon/file/unknown.png")));
                }
            }
            if (file.isDirectory()) {
                loopFileTree(fileTreeItem);
            }
        }
    }
}

package com.ooqn.assist.tab;

import cn.hutool.core.io.FileUtil;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.fx.control.filetree.FileShowMenuItem;
import com.ooqn.assist.fx.control.filetree.FileTreeItem;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.assist.util.SvgUtil;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.OpenFileEvent;
import com.ooqn.core.project.Project;
import com.ooqn.core.scene.EditorScene;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.SVGPath;

import java.io.File;
import java.io.IOException;

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

        Node refresh = SvgUtil.getSvg("icon/refresh.svg");
        ContextMenu contextMenu = new ContextMenu();
        //不知道为什么设置contextMenu 宽度不起作用。
        MenuItem refreshItem = new MenuItem("刷新           ");
        Menu newItem = new Menu("新建", SvgUtil.getSvg("icon/addFile.svg"));
        initNewItem(newItem);
        refreshItem.setGraphic(refresh);
        contextMenu.getItems().add(refreshItem);
        contextMenu.getItems().add(newItem);
        refreshItem.setOnAction(event -> {
            Object selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem instanceof FileTreeItem) {
                ((FileTreeItem) selectedItem).getChildren().clear();
                loopFileTree((FileTreeItem) selectedItem);
            }
        });
        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && event.getButton().equals(MouseButton.PRIMARY)) {
                FileTreeItem treeViewSelected = getTreeViewSelected();
                if (treeViewSelected.file.isFile()) {
                    OpenFileEvent openFileEvent = new OpenFileEvent(treeViewSelected.file,treeView);
                    EditorEventBus.post(openFileEvent);
                }
            }
        });
        treeView.setContextMenu(contextMenu);
        treeView.setShowRoot(false);
        treeView.setRoot(root);

        treeView.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
            FileTreeItem selected = getTreeViewSelected();
            System.out.println(selected);
        });
    }

    private void loopFileTree(FileTreeItem prentTreeItem) {
        File prent = prentTreeItem.file;
        if (prent.isFile()) {
            return;
        }
        prentTreeItem.getChildren().clear();
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
                    case "scene":
                        fileTreeItem.setGraphic(SvgUtil.getSvg("icon/file/scene.svg"));
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

    private void initNewItem(Menu newMenu) {
        FileShowMenuItem scene = new FileShowMenuItem("场景(.scene)       ", false, false);
        scene.setGraphic(SvgUtil.getSvg("icon/file/scene.svg"));
        scene.setOnAction(event -> {
            FileTreeItem treeViewSelected = getTreeViewSelected();
            EditorScene editorScene = EditorScene.newScene();
            try {
                File newFile = com.ooqn.assist.util.FileUtil.getNewFile(treeViewSelected.file, "新建场景.scene");
                editorScene.save(newFile);
                loopFileTree(treeViewSelected);
            } catch (IOException e) {
                AlertHandel.exceptionHandel(e);
            }
        });

        treeView.getSelectionModel().getSelectedItems().addListener((InvalidationListener) observable -> {
            FileTreeItem selected = getTreeViewSelected();
            for (MenuItem item : newMenu.getItems()) {
                if (item instanceof FileShowMenuItem) {
                    ((FileShowMenuItem) item).show(selected);
                }
            }
        });
        newMenu.getItems().add(scene);
    }

    private FileTreeItem getTreeViewSelected() {
        Object selectedItem = treeView.getSelectionModel().getSelectedItem();
        return (FileTreeItem) selectedItem;
    }
}

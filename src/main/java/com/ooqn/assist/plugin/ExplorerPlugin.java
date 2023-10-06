package com.ooqn.assist.plugin;

import java.io.File;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ExplorerPlugin implements Plugin {

    @Override
    public void init() {
        
        String projectPath = FoolContext.projectPath;

        File file = new File(projectPath);
        File[] listFiles = file.listFiles();
        
        TreeItem<String> treeItem = new TreeItem<>(file.getName());
        TreeView<String> treeView = new TreeView<>(treeItem);
        for (File listFiles2 : listFiles) {
            loadFileTree(listFiles2,treeItem);
        }
        
        FoolContext.Layout.leftBody.getItems().add(new TitledPane("目录", treeView));

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(new MenuItem("save"));       
        FoolContext.Layout.menu.getMenus().add(fileMenu);
    }

    @Override
    public void delete() {
        
    }

    void loadFileTree(File file, TreeItem<String> treeItem) {
        TreeItem<String> item1 = new TreeItem<>(file.getName());
        treeItem.getChildren().add(item1);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File listFiles2 : listFiles) {
                loadFileTree(listFiles2, item1);
            }
        }
    }
}

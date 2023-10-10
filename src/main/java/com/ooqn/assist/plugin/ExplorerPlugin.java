package com.ooqn.assist.plugin;

import java.io.File;
import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ExplorerPlugin implements Plugin {


    @Override
    public void init() {

         String projectPath = "C:\\Users\\Bobcbui No\\Downloads";

        File file = new File(projectPath);
        File[] listFiles = file.listFiles();
        
        TreeItem<String> treeItem = new TreeItem<>(file.getName());
        TreeView<String> treeView = new TreeView<>(treeItem);
        for (File listFiles2 : listFiles) {
            loadFileTree(listFiles2,treeItem);
        }
        
        FoolContext.getLeft().getItems().addAll(treeView);
    }

    @Override
    public void destroy() {
        
    }

    /**
     * tabPane -> TabPane
     */
    @Override
    public Map<String, Object> getData() {
        return pluginData;
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

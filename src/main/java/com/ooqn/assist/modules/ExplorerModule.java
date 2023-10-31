package com.ooqn.assist.modules;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Module;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class ExplorerModule implements Module {

    @Override
    public void init() {
        File file = new File(FoolContext.getWorkPath());
        List<File> listFiles = Arrays.asList(file.listFiles());
        Collections.sort(listFiles, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                if (file1.isDirectory() && !file2.isDirectory()) {
                    return -1;
                } else if (!file1.isDirectory() && file2.isDirectory()) {
                    return 1;
                } else {
                    return file1.getName().compareTo(file2.getName());
                }
            }
        });

        TreeItem<String> treeItem = new TreeItem<>(file.getName());
        TreeView<String> treeView = new TreeView<>(treeItem);
        for (File listFile : listFiles) {
            loadFileTree(listFile, treeItem);
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
        return moduleData;
    }

    void loadFileTree(File file, TreeItem<String> treeItem) {
        TreeItem<String> item = new TreeItem<String>();
        treeItem.getChildren().add(item);
        if (file.isDirectory()) {
            List<File> listFiles = Arrays.asList(file.listFiles());
            // 对文件列表进行排序
            Collections.sort(listFiles, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    if (file1.isDirectory() && !file2.isDirectory()) {
                        return -1;
                    } else if (!file1.isDirectory() && file2.isDirectory()) {
                        return 1;
                    } else {
                        return file1.getName().compareTo(file2.getName());
                    }
                }
            });
            for (File listFile : listFiles) {
                loadFileTree(listFile, item);
            }
        }
    }

    public static void print(List<File> list) {
        for (File list2 : list) {
            System.out.print(list2.getName() + ",");
        }
    }

}

package com.ooqn.assist.module;

import javax.swing.tree.TreeNode;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

public class FileMl {

    public static TreeView createMl(){
        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> rootItem = new TreeItem<>("Root");
        TreeItem<String> childItem1 = new TreeItem<>("Child 1");
        TreeItem<String> childItem2 = new TreeItem<>("Child 2");

        rootItem.getChildren().addAll(childItem1, childItem2);
        treeView.setRoot(rootItem);
        return treeView;
    }
    
}

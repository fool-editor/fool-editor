package com.ooqn.assist.accordion;

import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MlTitledPane extends TitledPane{
    public MlTitledPane(String name){
        setText(name);
        TreeItem<String> rootItem = new TreeItem<>("Root");

        TreeItem<String> child1 = new TreeItem<>("Child 1");
        TreeItem<String> child2 = new TreeItem<>("Child 2");

        rootItem.getChildren().add(child1);
        rootItem.getChildren().add(child2);

        TreeView<String> treeView = new TreeView<>(rootItem);

        getChildren().add(0, treeView);
        
    }
}

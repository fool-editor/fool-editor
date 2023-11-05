package com.ooqn.assist.fx.control;

import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeItem extends TreeItem<String> {
    public final  File file;
    public FileTreeItem(File file) {
        this.file = file;
        setValue(file.getName());
    }
}

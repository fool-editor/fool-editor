package com.ooqn.assist.fx.control.filetree;

import javafx.scene.control.TreeItem;

import java.io.File;

public class FileTreeItem extends TreeItem<String> {
    public final  File file;
    /**
     * 是否java 目录下的源码
     */
    public final  boolean java;
    public FileTreeItem(File file,boolean java) {
        this.file = file;
        this.java = java;
        setValue(file.getName());
    }
}

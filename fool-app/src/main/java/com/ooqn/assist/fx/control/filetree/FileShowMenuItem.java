package com.ooqn.assist.fx.control.filetree;

import javafx.scene.control.MenuItem;


/**
 * 根据FileTreeItem 来决定是否可用的MenuItem
 */
public class FileShowMenuItem extends MenuItem {
    /**
     * 是否java 目录
     * false 是resources目录
     */
    private boolean java;
    /**
     * 是否是文件吗，否则是 文件夹
     */
    private boolean file;

    public FileShowMenuItem(String text, boolean java, boolean file) {
        super(text);
        this.file = file;
        this.java = java;
    }

    public FileShowMenuItem(String text) {
        this(text, false, false);
    }

    public void show(FileTreeItem fileTreeItem) {
        if (fileTreeItem.java == java && fileTreeItem.file.isFile() == file) {
            setVisible(true);
        } else {
            setVisible(false);
        }
    }
}

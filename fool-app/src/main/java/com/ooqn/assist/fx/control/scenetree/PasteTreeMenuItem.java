package com.ooqn.assist.fx.control.scenetree;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

import java.util.List;

public class PasteTreeMenuItem extends TreeMenuItem {
    private final Clipboard<TreeItem> clipboard;

    public PasteTreeMenuItem(String text, Clipboard<TreeItem> clipboard, List<Class> notDisable, Node graphic, EventHandler<ActionEvent> onAction) {
        super(text, notDisable, graphic,onAction);
        this.clipboard = clipboard;
    }

    @Override
    public void disable(TreeItem treeItem) {
        super.disable(treeItem);
        if (isDisable()) {
            return;
        }
        if(clipboard.getType()!=null && clipboard.getObject()!=null){
            setDisable(false);
            return;
        }
        setDisable(true);
    }
}

package com.ooqn.assist.fx.control.scenetree;


import com.ooqn.assist.fx.control.TreeItemValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class TreeMenuItem extends MenuItem {

    private List<Class> notDisable = new ArrayList<>();

    public TreeMenuItem(String text, List<Class> notDisable) {
        super(text);
        this.notDisable = notDisable;
    }

    public TreeMenuItem(String text, List<Class> notDisable, EventHandler<ActionEvent> onAction) {
        super(text);
        this.notDisable = notDisable;
        setOnAction(onAction);
    }

    public TreeMenuItem(String text) {
        super(text);
    }

    public TreeMenuItem(String text, boolean disable) {
        super(text);
        setDisable(disable);
    }

    public TreeMenuItem(String text, boolean disable, Node graphic) {
        super(text);
        setDisable(disable);
        setGraphic(graphic);
    }

    public TreeMenuItem(String text, Node graphic) {
        super(text, graphic);
    }

    public TreeMenuItem(String text, Node graphic, EventHandler<ActionEvent> onAction) {
        super(text, graphic);
        setOnAction(onAction);
    }

    public TreeMenuItem(String text, List<Class> notDisable, Node graphic) {
        super(text, graphic);
        this.notDisable = notDisable;
    }
    public TreeMenuItem(String text, List<Class> notDisable, Node graphic,EventHandler<ActionEvent> onAction) {
        super(text, graphic);
        this.notDisable = notDisable;
        setOnAction(onAction);
    }


    public void disable(TreeItem treeItem) {
        if (notDisable.size() == 0) {
            return;
        }
        Object value = treeItem.getValue();
        if (value instanceof TreeItemValue<?>) {
            value = ((TreeItemValue<?>) value).getValue();
        }
        Class<?> aClass = value.getClass();
        for (Class aClass1 : notDisable) {
            if (aClass1.isAssignableFrom(aClass)) {
                setDisable(false);
                return;
            }
        }
        setDisable(true);
    }

}

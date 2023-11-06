package com.ooqn.assist.fx.control;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import javafx.scene.control.TreeItem;

import java.io.File;

public class SceneTreeItem extends TreeItem<String> {

    private Spatial spatial;
    public SceneTreeItem(Spatial spatial) {
        this.spatial=spatial;
        setValue(spatial.getName());
    }

}

package com.ooqn.assist.fx.control;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.ooqn.core.scene.EditorScene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class SceneTreeView extends TreeView<String> {
    private EditorScene scene;
    public SceneTreeView() {
        setShowRoot(false);
    }

    private void initEditorScene(EditorScene editorScene){
        Node sceneNode = editorScene.getSceneNode();
        SceneTreeItem sceneTreeItem = new SceneTreeItem(sceneNode);
        loopNode(sceneNode,sceneTreeItem);
        TreeItem root=new TreeItem();
        root.getChildren().add(sceneTreeItem);
    }

    private void loopNode(Node node,SceneTreeItem prent){
        List<Spatial> children = node.getChildren();
        for (Spatial child : children) {
            SceneTreeItem sceneTreeItem = new SceneTreeItem(child);
            prent.getChildren().add(sceneTreeItem);
            if(child instanceof Node){
                loopNode((Node) child,sceneTreeItem);
            }
        }
    }

    public EditorScene getEditorScene() {
        return scene;
    }

    public void setEditorScene(EditorScene scene) {
        this.scene = scene;
        this.initEditorScene(scene);
    }
}

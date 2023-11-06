package com.ooqn.assist.fx.control.scenetree;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.ooqn.assist.fx.control.TreeItemValue;
import com.ooqn.core.scene.EditorScene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class SceneTreeView extends TreeView<String> {
    private EditorScene scene;


    public SceneTreeView() {
        setShowRoot(false);
        initContextMenu();
    }

    private void initContextMenu() {

    }

    private void initEditorScene(EditorScene editorScene) {
        Node sceneNode = editorScene.getSceneNode();
        TreeItem sceneTreeItem = new TreeItem(new TreeItemValue(sceneNode,sceneNode.getName()));
        sceneTreeItem.setValue("场景根节点");

        TreeItem camera = new TreeItem();
        camera.setValue("相机列表");

        loopNode(sceneNode, sceneTreeItem);
        cameraList(camera,editorScene);

        TreeItem root = new TreeItem();
        root.getChildren().add(sceneTreeItem);
        root.getChildren().add(camera);
        setRoot(root);
    }

    private void loopNode(Node node, TreeItem prent) {
        List<Spatial> children = node.getChildren();
        for (Spatial child : children) {
            TreeItem treeItem = new TreeItem(new TreeItemValue(child,child.getName()));
            prent.getChildren().add(treeItem);
            if (child instanceof Node) {
                loopNode((Node) child, treeItem);
            }
        }
    }

    private void cameraList(TreeItem prent, EditorScene editorScene) {
        //TODO:
    }

    public EditorScene getEditorScene() {
        return scene;
    }

    public void setEditorScene(EditorScene scene) {
        this.scene = scene;
        this.initEditorScene(scene);
    }
}

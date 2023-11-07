package com.ooqn.assist.fx.control.scenetree;

import cn.hutool.core.lang.tree.Tree;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.ooqn.assist.fx.control.TreeItemValue;
import com.ooqn.assist.util.SvgUtil;
import com.ooqn.core.scene.EditorScene;
import com.sun.jdi.connect.ListeningConnector;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class SceneTreeView extends TreeView {
    private EditorScene scene;
    private ContextMenu sceneRootNodeContextMenu;
    private ContextMenu sceneNodeContextMenu;
    private ContextMenu camerasContextMenu;

    public SceneTreeView() {
        setShowRoot(false);
        creatMenu();
    }

    private void creatMenu() {
        Menu nodeNewMenu = new Menu("新建", SvgUtil.getSvg("icon/addFile.svg"));
        nodeNewMenu.getItems().add(new TreeMenuItem("Node",List.of(Node.class)));
        nodeNewMenu.getItems().add(new SeparatorMenuItem());
        nodeNewMenu.getItems().add(new TreeMenuItem("Box",List.of(Node.class)));
        nodeNewMenu.getItems().add(new TreeMenuItem("Plane",List.of(Node.class)));
        sceneRootNodeContextMenu = new ContextMenu();
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("刷新          ", SvgUtil.getSvg("icon/refresh.svg")));
        sceneRootNodeContextMenu.getItems().add(new SeparatorMenuItem());
        sceneRootNodeContextMenu.getItems().add(nodeNewMenu);
        sceneRootNodeContextMenu.getItems().add(new SeparatorMenuItem());
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("复制", true,SvgUtil.getSvg("icon/copy.svg")));
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("剪切", true,SvgUtil.getSvg("icon/cut.svg")));
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("粘贴",SvgUtil.getSvg("icon/paste.svg")));
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("删除", true,SvgUtil.getSvg("icon/delete.svg")));


        sceneNodeContextMenu = new ContextMenu();
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("刷新          ", List.of(Node.class), SvgUtil.getSvg("icon/refresh.svg")));
        sceneNodeContextMenu.getItems().add(new SeparatorMenuItem());
        sceneNodeContextMenu.getItems().add(nodeNewMenu);
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("复制",SvgUtil.getSvg("icon/copy.svg")));
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("剪切",SvgUtil.getSvg("icon/cut.svg")));
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("粘贴", List.of(Node.class),SvgUtil.getSvg("icon/paste.svg")));
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("删除",SvgUtil.getSvg("icon/delete.svg")));

        camerasContextMenu=new ContextMenu();
        camerasContextMenu.getItems().add(new TreeMenuItem("刷新          ", List.of(List.class), SvgUtil.getSvg("icon/refresh.svg")));
        Menu menu = new Menu("新建", SvgUtil.getSvg("icon/addFile.svg"));
        menu.getItems().add(new TreeMenuItem("相机",List.of(List.class,Camera.class)));
        camerasContextMenu.getItems().add(menu);
        camerasContextMenu.getItems().add(new TreeMenuItem("删除",List.of(Camera.class),SvgUtil.getSvg("icon/delete.svg")));

    }

    private void initEditorScene(EditorScene editorScene) {
        Node sceneNode = editorScene.getSceneNode();
        TreeItem sceneTreeItem = new TreeItem(new TreeItemValue(sceneNode, "场景根节点(" + sceneNode.getName() + ")"));
        sceneTreeItem.setGraphic(SvgUtil.getSvg("icon/file/scene.svg"));
        TreeItem camera = new TreeItem();
        camera.setValue(new TreeItemValue<>(editorScene.getCameras(), "相机列表"));

        loopNode(sceneNode, sceneTreeItem);
        cameraList(camera, editorScene);

        TreeItem root = new TreeItem();
        root.getChildren().add(sceneTreeItem);
        root.getChildren().add(camera);
        setRoot(root);


//        contextMenu.getItems().add(new TreeMenuItem(true,"复制",List.of(Spatial.class)));
//        contextMenu.getItems().add(new TreeMenuItem(true,"剪切",List.of(Object.class)));
//        contextMenu.getItems().add(new TreeMenuItem(true,"粘贴",List.of(Object.class),List.of(Node.class)));


//        TreeMenuItem newNode = new TreeMenuItem("Node", List.of(Node.class));
//        menu3.getItems().add(newNode);


        getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem>) c -> {
            TreeItem selectedItem = (TreeItem) getSelectionModel().getSelectedItem();
            Object value = selectedItem.getValue();
            if (value instanceof TreeItemValue) {
                value = ((TreeItemValue<?>) value).getValue();
            }
            if (value == sceneNode) {
                setContextMenu(sceneRootNodeContextMenu);
                disableMenuItem(sceneRootNodeContextMenu.getItems());
                return;
            } else if (value instanceof Spatial) {
                setContextMenu(sceneNodeContextMenu);
                disableMenuItem(sceneNodeContextMenu.getItems());
                return;
            }
            setContextMenu(camerasContextMenu);
            disableMenuItem(camerasContextMenu.getItems());
        });
    }


    private void loopNode(Node node, TreeItem prent) {
        List<Spatial> children = node.getChildren();
        for (Spatial child : children) {
            TreeItem treeItem = new TreeItem(new TreeItemValue(child, child.getName()));
            prent.getChildren().add(treeItem);
            if (child instanceof Node) {
                loopNode((Node) child, treeItem);
            }
        }
    }

    private void disableMenuItem(List<MenuItem> menuItems){
        TreeItem selectedItem = (TreeItem)getSelectionModel().getSelectedItem();

        for (MenuItem menuItem : menuItems) {
            if (menuItem instanceof Menu) {
                disableMenuItem(((Menu) menuItem).getItems());
            }
            if(menuItem instanceof TreeMenuItem){
                ((TreeMenuItem) menuItem).disable(selectedItem);
            }
        }
    }

    private void cameraList(TreeItem prent, EditorScene editorScene) {
        ArrayList<Camera> cameras = editorScene.getCameras();
        for (Camera camera : cameras) {
            TreeItem treeItem = new TreeItem(new TreeItemValue<>(camera, camera.getName()));
            prent.getChildren().add(treeItem);
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

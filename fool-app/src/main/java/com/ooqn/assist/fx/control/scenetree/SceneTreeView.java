package com.ooqn.assist.fx.control.scenetree;

import cn.hutool.core.lang.tree.Tree;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.fx.control.Svg;
import com.ooqn.assist.fx.control.TreeItemValue;
import com.ooqn.assist.util.SvgUtil;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.SelectCameraEvent;
import com.ooqn.core.event.SelectSpatialEvent;
import com.ooqn.core.scene.EditorScene;
import com.sun.jdi.connect.ListeningConnector;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Group;
import javafx.scene.control.*;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class SceneTreeView extends TreeView {
    private EditorScene scene;
    private ContextMenu sceneRootNodeContextMenu;
    private ContextMenu sceneNodeContextMenu;
    private ContextMenu camerasContextMenu;
    private final Clipboard<TreeItem> clipboard;

    public SceneTreeView() {
        clipboard = new Clipboard();
        setShowRoot(false);
        creatMenu();
    }

    private void creatMenu() {
        Menu nodeNewMenu = new Menu("新建", SvgUtil.getSvg("icon/addFile.svg"));
        nodeNewMenu.getItems().add(new TreeMenuItem("Node", List.of(Node.class), event -> {
            Node node = getSelectTreeItemValue();
            Node newNode = new Node("new node");
            FoolContext.runJmeThread(() -> {
                node.attachChild(newNode);
                getSelectTreeItem().setExpanded(true);
                loopNode(node, getSelectTreeItem());
            });
        }));
        nodeNewMenu.getItems().add(new SeparatorMenuItem());
        nodeNewMenu.getItems().add(new TreeMenuItem("Box", List.of(Node.class), event -> {
            Node node = getSelectTreeItemValue();
            Box box = new Box(0.5f, 0.5f, 05f);
            Geometry geometry = new Geometry("new Box");
            geometry.setMesh(box);
            Material material = new Material(FoolContext.getEditorJmeApplication().getAssetManager(), Materials.UNSHADED);
            geometry.setMaterial(material);
            FoolContext.runJmeThread(() -> {
                node.attachChild(geometry);
                getSelectTreeItem().setExpanded(true);
                loopNode(node, getSelectTreeItem());
            });
        }));

        sceneRootNodeContextMenu = new ContextMenu();
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("刷新          ", SvgUtil.getSvg("icon/refresh.svg"), event -> {
            Node node = getSelectTreeItemValue();
            TreeItem selectTreeItem = getSelectTreeItem();
            loopNode(node, selectTreeItem);
        }));
        sceneRootNodeContextMenu.getItems().add(new SeparatorMenuItem());
        sceneRootNodeContextMenu.getItems().add(nodeNewMenu);
        sceneRootNodeContextMenu.getItems().add(new SeparatorMenuItem());
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("复制", true, SvgUtil.getSvg("icon/copy.svg")));
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("剪切", true, SvgUtil.getSvg("icon/cut.svg")));
        sceneRootNodeContextMenu.getItems().add(new PasteTreeMenuItem("粘贴", clipboard, List.of(Node.class), SvgUtil.getSvg("icon/paste.svg"), event -> {
            nodePasteClipboard();
        }));
        sceneRootNodeContextMenu.getItems().add(new TreeMenuItem("删除", true, SvgUtil.getSvg("icon/delete.svg")));


        sceneNodeContextMenu = new ContextMenu();
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("刷新          ", List.of(Node.class), SvgUtil.getSvg("icon/refresh.svg"), event -> {
            Node node = getSelectTreeItemValue();
            TreeItem selectTreeItem = getSelectTreeItem();
            loopNode(node, selectTreeItem);
        }));
        sceneNodeContextMenu.getItems().add(new SeparatorMenuItem());
        sceneNodeContextMenu.getItems().add(nodeNewMenu);
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("复制", SvgUtil.getSvg("icon/copy.svg"), event -> {
            clipboard.set(Clipboard.Type.Cope, getSelectTreeItem());
        }));
        sceneNodeContextMenu.getItems().add(new TreeMenuItem("剪切", SvgUtil.getSvg("icon/cut.svg"), event -> {
            clipboard.set(Clipboard.Type.Cut, getSelectTreeItem());
        }));
        sceneNodeContextMenu.getItems().add(new PasteTreeMenuItem("粘贴", clipboard, List.of(Node.class), SvgUtil.getSvg("icon/paste.svg"), event -> {
            nodePasteClipboard();
        }));

        sceneNodeContextMenu.getItems().add(new TreeMenuItem("删除", SvgUtil.getSvg("icon/delete.svg"), event -> {
            Spatial spatial = getSelectTreeItemValue();
            TreeItem selectTreeItem = getSelectTreeItem();
            TreeItem parent = getSelectTreeItem().getParent();
            parent.getChildren().remove(selectTreeItem);
            Node node = spatial.getParent();
            FoolContext.runFxThread(() -> {
                node.detachChild(spatial);
            });
        }));


        camerasContextMenu = new ContextMenu();
        camerasContextMenu.getItems().add(new TreeMenuItem("刷新          ", List.of(List.class), SvgUtil.getSvg("icon/refresh.svg")));
        Menu menu = new Menu("新建", SvgUtil.getSvg("icon/addFile.svg"));
        menu.getItems().add(new TreeMenuItem("相机           ", List.of(List.class, Camera.class), new Svg("icon/camera.svg")));
        camerasContextMenu.getItems().add(menu);
        camerasContextMenu.getItems().add(new TreeMenuItem("删除", List.of(Camera.class), SvgUtil.getSvg("icon/delete.svg")));


        TreeView treeView = this;
        getSelectionModel().getSelectedItems().addListener((ListChangeListener<TreeItem>) c -> {
            TreeItem selectedItem = (TreeItem) getSelectionModel().getSelectedItem();
            Object value = selectedItem.getValue();
            if (value instanceof TreeItemValue) {
                value = ((TreeItemValue<?>) value).getValue();
            }
            if (value instanceof Spatial) {
                EditorEventBus.editorEventBus.post(new SelectSpatialEvent(treeView, (Spatial) value));
            }
            if (value instanceof Camera) {
                EditorEventBus.editorEventBus.post(new SelectCameraEvent(treeView, (Camera) value));
            }
            if (value == getEditorScene().getSceneNode()) {
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

    private void initEditorScene(EditorScene editorScene) {
        Node sceneNode = editorScene.getSceneNode();
        TreeItem sceneTreeItem = new TreeItem(new TreeItemValue(sceneNode, "场景根节点(" + sceneNode.getName() + ")"));
        sceneTreeItem.setGraphic(SvgUtil.getSvg("icon/file/scene.svg"));
        TreeItem camera = new TreeItem();
        camera.setGraphic(new Svg("icon/camera.svg"));
        camera.setValue(new TreeItemValue<>(editorScene.getCameras(), "相机列表"));

        loopNode(sceneNode, sceneTreeItem);
        cameraList(camera, editorScene);

        TreeItem root = new TreeItem();
        root.getChildren().add(sceneTreeItem);
        root.getChildren().add(camera);
        setRoot(root);

    }


    private void loopNode(Node node, TreeItem prent) {
        List<Spatial> children = node.getChildren();
        if (prent.getChildren().size() > 0) {
            prent.getChildren().clear();
        }
        for (Spatial child : children) {
            TreeItem treeItem = new TreeItem(new TreeItemValue(child, child.getName()));
            prent.getChildren().add(treeItem);
            if (child instanceof Node) {
                loopNode((Node) child, treeItem);
            }
        }
    }


    private TreeItem getSelectTreeItem() {
        TreeItem treeItem = (TreeItem) getSelectionModel().getSelectedItem();
        return treeItem;
    }

    private <T> T getSelectTreeItemValue() {
        TreeItem treeItem = (TreeItem) getSelectionModel().getSelectedItem();
        Object value = treeItem.getValue();
        if (value instanceof TreeItemValue) {
            value = ((TreeItemValue<?>) value).getValue();
        }
        return (T) value;
    }

    private void disableMenuItem(List<MenuItem> menuItems) {
        TreeItem selectedItem = (TreeItem) getSelectionModel().getSelectedItem();

        for (MenuItem menuItem : menuItems) {
            if (menuItem instanceof Menu) {
                disableMenuItem(((Menu) menuItem).getItems());
            }
            if (menuItem instanceof TreeMenuItem) {
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

    /**
     * node 粘贴
     */
    private void nodePasteClipboard() {
        //当前选中TreeItem
        TreeItem selectTreeItem = getSelectTreeItem();
        //当前选中Node
        Node node = getSelectTreeItemValue();
        //剪切板中的 TreeItem
        TreeItem clipboardTreeItem = clipboard.getObject();
        //剪切板中的 TreeItemValue
        TreeItemValue treeItemValue = (TreeItemValue) clipboardTreeItem.getValue();
        //剪切板中的 spatial
        Spatial spatial = (Spatial) treeItemValue.getValue();
        if (clipboard.getType().equals(Clipboard.Type.Cope)) {
            Spatial clone = spatial.deepClone();
            FoolContext.runJmeThread(() -> {
                node.attachChild(clone);
            });
            TreeItem treeItem = new TreeItem(new TreeItemValue(clone, clone.getName()));
            selectTreeItem.getChildren().add(treeItem);
            if(clone instanceof Node){
                loopNode((Node) clone,treeItem);
            }
            clipboard.clear();
            return;
        }
        if (clipboard.getType().equals(Clipboard.Type.Cut)) {
            FoolContext.runJmeThread(() -> {
                node.attachChild(spatial);
            });
            clipboardTreeItem.getParent().getChildren().remove(clipboardTreeItem);
            selectTreeItem.getChildren().add(clipboardTreeItem);
            clipboard.clear();
            return;
        }

    }
}

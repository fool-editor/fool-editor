package com.ooqn.assist.plugin;


import cn.hutool.core.io.FileUtil;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jme3.asset.AssetManager;
import com.jme3.export.binary.BinaryImporter;
import com.ooqn.assist.App2;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.fx.control.Svg;
import com.ooqn.assist.tab.InspectTab;
import com.ooqn.assist.tab.JmeSceneTreeTab;
import com.ooqn.assist.view.BaseInfoController;
import com.ooqn.core.event.*;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.assist.tab.FileSystemTab;
import com.ooqn.assist.tab.JmeViewTab;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.plugin.Plugin;
import com.ooqn.core.scene.EditorScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class JmePlugin implements Plugin {

    @Override
    public void init(EventBus eventBus) {
        eventBus.register(this);
    }

    @Override
    public void destroy() {

    }

    @Subscribe
    private void openProject(OpenProjectEvent openProject) {
        JmeViewTab jmeTab = new JmeViewTab();
        FileSystemTab fileSystemTab = new FileSystemTab();
        FoolContext.getFoolContextWindow().getTabPane2().getTabs().add(fileSystemTab);
        FoolContext.getFoolContextWindow().getTabPane3().getTabs().add(jmeTab);
    }

    @Subscribe
    private void jmeStartCompleteEvent(JmeStartCompleteEvent jmeStartCompleteEvent) {


    }

    @Subscribe
    private void openFileEvent(OpenFileEvent openFileEvent) {
        File file = openFileEvent.getFile();
        EditorJmeApplication editorJmeApplication = FoolContext.getFoolContextWindow().getEditorJmeApplication();
        if (file.isFile() && FileUtil.extName(file).equals("scene")) {
            BinaryImporter binaryImporter = new BinaryImporter();
            AssetManager assetManager = editorJmeApplication.getAssetManager();
            binaryImporter.setAssetManager(assetManager);
            EditorScene editorScene;
            try {
                editorScene = (EditorScene) binaryImporter.load(file);
            } catch (IOException e) {
                AlertHandel.exceptionHandel(e);
                return;
            }
            editorJmeApplication.openScene(editorScene, file);
        }
    }

    @Subscribe
    private void editorCloseEvent(EditorCloseEvent editorCloseEvent) {
        EditorScene scene = FoolContext.getEditorJmeApplication().getScene();
        if (scene != null) {
            FoolContext.getEditorJmeApplication().saveScene();
        }
    }

    @Subscribe
    private void openSceneEvent(OpenSceneEvent openSceneEvent) {
        TabPane tabPane = FoolContext.getFoolContextWindow().getTabPane1();
        JmeSceneTreeTab jmeSceneTreeTab = null;
        for (Tab tab : tabPane.getTabs()) {
            if (tab instanceof JmeSceneTreeTab) {
                jmeSceneTreeTab = (JmeSceneTreeTab) tab;
                break;
            }
        }
        if (jmeSceneTreeTab == null) {
            jmeSceneTreeTab = new JmeSceneTreeTab();
            tabPane.getTabs().add(jmeSceneTreeTab);
        }
        jmeSceneTreeTab.init(openSceneEvent.editorScene);
    }

    @Subscribe
    private void setSelectSpatialEvent(SelectEvent spatialEvent) throws IOException {
        TabPane tabPane = FoolContext.getFoolContextWindow().getTabPane5();
        InspectTab inspectTab=null;
        for (Tab tab : tabPane.getTabs()) {
            if (tab instanceof InspectTab) {
                inspectTab=(InspectTab) tab;
                break;
            }
        }
        if(inspectTab==null){
            inspectTab=new InspectTab();
            tabPane.getTabs().add(inspectTab);
        }
        inspectTab.clearChildren();
        if(spatialEvent instanceof SelectSpatialEvent){
            SelectSpatialEvent selectSpatialEvent=(SelectSpatialEvent)spatialEvent;
            // 加载FXML文件
            FXMLLoader fxmlLoader = new FXMLLoader(App2.class.getClassLoader().getResource("baseInfo.fxml"));
            Parent root = fxmlLoader.load();
            BaseInfoController baseInfoController = fxmlLoader.getController();
            baseInfoController.input.setText(selectSpatialEvent.obj.getName());
            InspectTab finalInspectTab = inspectTab;
            baseInfoController.input.textProperty().addListener((observable, oldValue, newValue) -> {
                selectSpatialEvent.obj.setName(newValue);
                EditorEventBus.post(new SpatialNameChangeEvent(finalInspectTab,selectSpatialEvent.obj));
            });
            baseInfoController.info.setText(selectSpatialEvent.obj.getClass().getName());
            inspectTab.addNode(root);
            Svg svg = new Svg(50, "icon/camera.svg");
            baseInfoController.imgPane.getChildren().add(svg);
        }

    }
}

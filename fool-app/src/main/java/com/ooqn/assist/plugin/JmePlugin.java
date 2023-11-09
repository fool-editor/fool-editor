package com.ooqn.assist.plugin;


import cn.hutool.core.io.FileUtil;
import com.google.common.eventbus.Subscribe;
import com.jme3.asset.AssetManager;
import com.jme3.export.binary.BinaryImporter;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.tab.JmeSceneTreeTab;
import com.ooqn.core.event.*;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.assist.tab.FileSystemTab;
import com.ooqn.assist.tab.JmeViewTab;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.plugin.Plugin;
import com.ooqn.core.scene.EditorScene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;

public class JmePlugin implements Plugin {

    @Override
    public void init(EditorEventBus eventBus) {
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
}

package com.ooqn.core;

import com.jme3.app.Application;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.ooqn.core.scene.EditorScene;

import java.io.File;

public interface EditorJmeApplication extends Application{
    Camera getEditorCamera();
    Node getEditorNode();

    EditorScene getScene();

    void openScene(EditorScene scene, File file);
    void saveScene();

    boolean isInitialized();

    Thread getThread();
}

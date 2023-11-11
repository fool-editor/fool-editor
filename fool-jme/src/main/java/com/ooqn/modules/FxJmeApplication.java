package com.ooqn.modules;

import com.jme3.app.Application;
import com.ooqn.core.EditorJmeApplication;
import javafx.scene.Node;

public interface FxJmeApplication<FxNode extends Node> extends Application, EditorJmeApplication {
     boolean isJmeThread();
     FxNode getImageView();

    void reshape(int w,int h);
}

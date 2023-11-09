package com.ooqn.assist.tab;

import com.google.common.eventbus.Subscribe;
import com.ooqn.assist.fx.control.scenetree.SceneTreeView;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.SpatialNameChangeEvent;
import com.ooqn.core.scene.EditorScene;
import javafx.scene.control.*;

public class JmeSceneTreeTab extends Tab {
    private SceneTreeView sceneTreeView;

    public JmeSceneTreeTab() {
        setText("场景");
        EditorEventBus.register(this);
    }

    public void init(EditorScene editorScene) {
        sceneTreeView = new SceneTreeView();
        setContent(sceneTreeView);
        sceneTreeView.setEditorScene(editorScene);
        sceneTreeView.setShowRoot(false);
    }

    @Subscribe
    public void spatialNameChangeEvent(SpatialNameChangeEvent spatialNameChangeEvent) {
        sceneTreeView.event(spatialNameChangeEvent);
    }

}

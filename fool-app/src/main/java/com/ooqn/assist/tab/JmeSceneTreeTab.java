package com.ooqn.assist.tab;

import com.ooqn.assist.fx.control.scenetree.SceneTreeView;
import com.ooqn.core.scene.EditorScene;
import javafx.scene.control.*;

public class JmeSceneTreeTab extends Tab {
    private SceneTreeView sceneTreeView;

    public JmeSceneTreeTab() {
        setText("场景");

    }

    public void init(EditorScene editorScene) {
        sceneTreeView=new SceneTreeView();
        setContent(sceneTreeView);
        sceneTreeView.setEditorScene(editorScene);
        sceneTreeView.setShowRoot(false);
    }

}

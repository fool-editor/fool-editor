package com.ooqn.core.event;

import com.ooqn.core.project.Project;
import com.ooqn.core.scene.EditorScene;

public class OpenSceneEvent {
    public final EditorScene editorScene;

    public OpenSceneEvent(EditorScene editorScene) {
        this.editorScene = editorScene;
    }
}

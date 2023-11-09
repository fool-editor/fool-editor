package com.ooqn.core.event;

import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.project.Project;
import com.ooqn.core.scene.EditorScene;

/**
 * 打开一个editorScene
 */
public class OpenSceneEvent extends Event{
    public final EditorScene editorScene;

    public OpenSceneEvent(EditorScene editorScene , EditorJmeApplication editorJmeApplication) {
        super(editorJmeApplication);
        this.editorScene = editorScene;
    }

}

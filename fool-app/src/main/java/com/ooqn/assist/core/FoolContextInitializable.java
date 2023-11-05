package com.ooqn.assist.core;

import com.ooqn.modules.SimpleJfxApplication;
import javafx.scene.control.TabPane;

public interface FoolContextInitializable {
    
    TabPane getEditor();

    TabPane getPanel();
    SimpleJfxApplication getSimpleJfxApplication();
}

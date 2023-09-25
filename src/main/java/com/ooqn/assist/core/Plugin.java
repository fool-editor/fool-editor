package com.ooqn.assist.core;

import javafx.scene.layout.AnchorPane;

public interface Plugin {
    
    public AnchorPane open(String filePath);

    public AnchorPane menu();

}

package com.ooqn.assist.core;

import javafx.scene.layout.Pane;

public interface Plugin {
    
    /**
     * 插件可以查看的文件格式 ， 插件的注入菜单
     * @param mainContext
     */
    public void init();

}

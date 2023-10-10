package com.ooqn.assist.plugin;

import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ExplorerPlugin implements Plugin {

    @Override
    public void init() {
        TabPane tabPane = new TabPane();
        Tab ml = new Tab("目录");
        ml.setContent(new Label("kdjfkdjf"));
        Tab gj = new Tab("构建");
        tabPane.setSide(Side.LEFT);
        ml.setClosable(false);
        gj.setClosable(false);
        tabPane.getTabs().addAll(ml,gj);
        FoolContext.getLeft().getItems().addAll(tabPane);

        pluginData.put("tabPane", tabPane);
    }

    @Override
    public void destroy() {
        
    }

    /**
     * tabPane -> TabPane
     */
    @Override
    public Map<String, Object> getData() {
        return pluginData;
    }

}

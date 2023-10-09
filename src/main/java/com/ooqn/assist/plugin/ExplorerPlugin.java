package com.ooqn.assist.plugin;

import java.util.HashMap;
import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class ExplorerPlugin implements Plugin {

    Accordion accordion1;

    Accordion accordion2;

    @Override
    public void init() {

        accordion1 = new Accordion();
        accordion1.getPanes().addAll(new TitledPane("Panel 2", new VBox()),new TitledPane("Panel 2", new VBox()));

        accordion2 = new Accordion();
        TitledPane titledPane1 = new TitledPane("Panel 2", new VBox());
        TitledPane titledPane2 = new TitledPane("Panel 2", new VBox());
        accordion2.getPanes().addAll(titledPane1,titledPane2);


        FoolContext.left.getItems().addAll(accordion1, accordion2);
        
    }

    @Override
    public void destroy() {
        
    }

    /**
     * accordion1 -> Accordion
     * accordion2 -> Accordion
     */
    @Override
    public Map<String, Object> getData() {
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("accordion1", accordion1);
        map.put("accordion2", accordion2);
        return map;
    }

}

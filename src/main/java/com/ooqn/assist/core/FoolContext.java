package com.ooqn.assist.core;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Accordion;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class FoolContext {

    public static Map<String,FoolOpenTab> openTab = new HashMap<>();

    public static class MainLayout{

        public static MenuBar menu;

        public static SplitPane createMainBody;

        public static HBox createFloot;

        public static Accordion leftTop;

        public static Accordion leftDown;

        public static SplitPane leftSplitPane;

        public static TabPane tabDown;
    
    }
    
}

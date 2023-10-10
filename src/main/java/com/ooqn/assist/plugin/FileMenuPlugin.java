package com.ooqn.assist.plugin;

import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Plugin;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class FileMenuPlugin implements Plugin {

    @Override
    public void destroy() {
       
    }

    /*
     * menu - > Menu
     */
    @Override
    public Map<String, Object> getData() {
        
        return pluginData;
    }

    @Override
    public void init() {
        Menu menu = new Menu("File");
        
       
        menu.getItems().addAll(open(),save(),exit());
        pluginData.put("menu", menu);
        FoolContext.getMenu().getMenus().add(menu);
    }

    private MenuItem open(){
        MenuItem open = new MenuItem("Open");
        DirectoryChooser  directoryChooser = new DirectoryChooser ();
        open.setOnAction(e -> {
            directoryChooser.showDialog(FoolContext.getStage());
        });
        return open;
    }

    private MenuItem save(){
        MenuItem save = new MenuItem("Save");
        save.setOnAction(e -> {
            
        });
        return save;
    }

     private MenuItem exit(){
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {

        });
        return exit;
    }

}

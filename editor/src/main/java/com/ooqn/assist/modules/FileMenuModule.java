package com.ooqn.assist.modules;

import java.io.File;
import java.util.Map;

import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.Module;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;

public class FileMenuModule implements Module {

    @Override
    public void destroy() {

    }

    /*
     * menu - > Menu
     */
    @Override
    public Map<String, Object> getData() {

        return moduleData;
    }

    @Override
    public void init() {
        Menu menu = new Menu("File");
        menu.getItems().addAll(open(), save(), exit());
        moduleData.put("menu", menu);
        FoolContext.getMenu().getMenus().add(menu);
    }

    private MenuItem open() {
        MenuItem open = new MenuItem("Open");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        open.setOnAction(e -> {
            File showDialog = directoryChooser.showDialog(FoolContext.getStage());
            FoolContext.setWorkPath(showDialog.getPath());
            FoolContext.getModuleManage().addModule(new ExplorerModule());
        });
        return open;
    }

    private MenuItem save() {
        MenuItem save = new MenuItem("Save");
        save.setOnAction(e -> {

        });
        return save;
    }

    private MenuItem exit() {
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> {

        });
        return exit;
    }

}

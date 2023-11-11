package test;


import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.ooqn.assist.plugin.state.CoordinateViewState;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.JmeStartCompleteEvent;
import com.ooqn.core.event.OpenSceneEvent;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.core.scene.EditorScene;
import com.ooqn.modules.jme.*;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class JmeApplication extends SimpleApplication  {


    @Override
    public void simpleInitApp() {
        CoordinateViewState coordinateViewState=new CoordinateViewState();
        getStateManager().attach(coordinateViewState);
    }

    public static void main(String[] args) {
        new JmeApplication().start();
    }
}

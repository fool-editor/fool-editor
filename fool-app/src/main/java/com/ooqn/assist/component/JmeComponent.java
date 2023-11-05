package com.ooqn.assist.component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.renderer.Camera;
import com.jme3.system.AppSettings;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.core.Component;
import com.ooqn.modules.SimpleJfxApplication;
import com.ooqn.modules.jme.EditorFxImageView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;

public class JmeComponent implements Component{

    @Override
    public void destroy() {
        
    }

    @Override
    public Map<String, Object> getData() {
        return null;
    }


    @Override
    public void init() {

        // We need to start JME on a new thread, not on the JFX thread.
        // We could do this a million ways, but let's just be as safe as possible.


        FoolTab foolTab = new FoolTab("JME");
        FoolContext.getBodyTop().getTabs().add(foolTab);
        EditorFxImageView imageView = FoolContext.getSimpleJfxApplication().getImageView();
        BorderPane pane = new BorderPane(imageView);
        foolTab.setContent(pane);
        // 监听宽度发送时修改JME的宽度
        pane.widthProperty().addListener((observable, oldValue, newValue) -> {
           imageView.resize(newValue.doubleValue(), pane.getWidth());
           System.out.println(newValue.doubleValue());
        });
        // 监听高度发送时修改JME的高度
        pane.heightProperty().addListener((observable, oldValue, newValue) -> imageView.resize(pane.getHeight(), newValue.doubleValue()));

    }
    
}

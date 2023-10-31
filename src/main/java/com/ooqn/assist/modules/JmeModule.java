package com.ooqn.assist.modules;

import java.util.Map;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.StatsAppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.system.AppSettings;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolTab;
import com.ooqn.assist.core.Module;
import com.ooqn.assist.modules.jme.EditorFxImageView;
import com.ooqn.assist.modules.jme.MyJmeGame;
import com.ooqn.assist.modules.jme.SimpleJfxApplication;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.concurrent.atomic.AtomicReference;

public class JmeModule implements Module{

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
        AtomicReference<SimpleJfxApplication> jfxApp = new AtomicReference<>();

        new Thread(new ThreadGroup("LWJGL"), () -> {

            // create a new instance of our game.
            // SimpleJfxApplication myJmeGame = new MyJmeGame();

            // or add some appstates..
            SimpleJfxApplication myJmeGame = new MyJmeGame(
                    new StatsAppState(),
                    new AudioListenerState(),
                    new FlyCamAppState()
            );

            // set our appSettings here
            AppSettings appSettings = myJmeGame.getSettings();
            appSettings.setUseJoysticks(true);
            appSettings.setGammaCorrection(true);
            appSettings.setSamples(16);


            jfxApp.set(myJmeGame);

            // we have a specific "start" method because due to LWJGL3 behavior this method will never return.
            // If we called this method in the constructor, it would never get constructed, so we have seperated
            // the blocking line of code in a method that gets called after construction.
            jfxApp.get().start();

        }, "LWJGL Render").start();
            
        // wait for the engine to initialize...
        // You can show some kind of indeterminate progress bar in a splash screen while you wait if you like...|| !jfxApp.get().isInitialized()
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (jfxApp.get() == null ) {
            System.err.println(jfxApp.get().isInitialized());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // The application is never going to change from hereon in, so we can just reference the actual value.
        // Just remember that any calls to JME need to be enqueued from app.enqueue(Runnable) if you are not on the JME
        // thread (e.g. you're on the JavaFX thread). Any calls to JavaFx need to be done on the JavaFX thread, or via
        // Plaform.runLater(Runnable).
        SimpleJfxApplication app = jfxApp.get();

       
        FoolTab foolTab = new FoolTab("JME");
        FoolContext.getBodyTop().getTabs().add(foolTab);
        EditorFxImageView imageView = app.getImageView();
        BorderPane pane = new BorderPane(imageView);
        foolTab.setContent(pane);

        

        pane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
               imageView.resize(newValue.doubleValue(), pane.getWidth());
               System.out.println(newValue.doubleValue());
              
            }
        });

        pane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                imageView.resize(pane.getHeight(), newValue.doubleValue());
            }
        });

        
        
    }
    
}

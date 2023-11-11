package com.ooqn.modules;


import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.JmeStartCompleteEvent;
import com.ooqn.core.event.OpenSceneEvent;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.core.scene.EditorScene;
import com.ooqn.modules.jme.*;
import com.ooqn.modules.jme.state.JmeViewAppState;
import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class SimpleJfxApplicationV2 extends SimpleApplication implements EditorJmeApplication, FxJmeApplication<Canvas> {

    private static final Logger log = Logger.getLogger(SimpleJfxApplicationV2.class.getName());

    private Thread jmeThread;

    private Node editorNode = new Node("editorNode");

    private EditorScene scene;
    private File secenFile;

    private boolean initialized = false;
    private JmeViewAppState jmeViewAppState;
    public SimpleJfxApplicationV2(AppState... initialStates) {
        super(initialStates);
        jmeViewAppState=new JmeViewAppState();
        jmeThread = new Thread(new ThreadGroup("LWJGL"), () -> {
            start();
        }, "LWJGL Render");
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        settings.setCustomRenderer(JmeOffscreenSurfaceContext.class);
        settings.setResizable(true);
        //不知道是不是bug 设置小了 截图出来最大只有初始大小
        settings.setHeight(1204);
        settings.setWidth(1204);
        // setPauseOnLostFocus(false);
        setSettings(settings);

        createCanvas();

        jmeThread.start();
    }

    public void start() {
        JmeOffscreenSurfaceContext canvasContext = (JmeOffscreenSurfaceContext) getContext();
        canvasContext.setApplication(this);
        canvasContext.setSystemListener(this);
        startCanvas(true);
    }



    @Override
    public void simpleInitApp() {
        rootNode.attachChild(editorNode);
        getStateManager().attach(jmeViewAppState);
        viewPort.setBackgroundColor(ColorRGBA.Black);
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(6);

        initialized = true;
        log.info("jMonkeyEngine Initialized.");
        EditorEventBus.post(new JmeStartCompleteEvent(this));
    }

    public Canvas getImageView() {
        return jmeViewAppState.getCanvas();
    }

    public boolean isInitialized() {
        return initialized;
    }



    @Override
    public Thread getThread() {
        return jmeThread;
    }

    public boolean isJmeThread() {
        return Thread.currentThread() == jmeThread;
    }



    @Override
    public void simpleUpdate(float tpf) {
    }


    @Override
    public Camera getEditorCamera() {
        return getCamera();
    }

    @Override
    public Node getEditorNode() {
        return editorNode;
    }

    @Override
    public EditorScene getScene() {
        return scene;
    }

    @Override
    public void openScene(EditorScene scene, File file) {
        if (this.scene != null) {
            try {
                scene.save(secenFile);
            } catch (IOException e) {
                AlertHandel.exceptionHandel("场景保存失败", e);
                return;
            }
            editorNode.detachChild(scene.getSceneNode());
        }
        this.scene = scene;
        this.secenFile = file;
        editorNode.attachChild(scene.getSceneNode());
        EditorEventBus.post(new OpenSceneEvent(scene, this));
    }

    @Override
    public void saveScene() {

        if (this.scene != null) {
            try {
                log.info("保存场景"+secenFile);
                scene.save(secenFile);
            } catch (IOException e) {
                AlertHandel.exceptionHandel("场景保存失败", e);
            }
        }
    }
}

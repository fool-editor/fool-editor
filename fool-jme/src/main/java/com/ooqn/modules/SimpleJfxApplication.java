package com.ooqn.modules;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.JmeStartCompleteEvent;
import com.ooqn.core.event.OpenSceneEvent;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.core.scene.EditorScene;
import com.ooqn.modules.jme.EditorFxImageView;
import com.ooqn.modules.jme.FrameTransferSceneProcessor;
import com.ooqn.modules.jme.ImageViewFrameTransferSceneProcessor;
import com.ooqn.modules.jme.JfxMouseInput;
import com.ooqn.modules.jme.JmeOffscreenSurfaceContext;
import javafx.application.Platform;

public class SimpleJfxApplication extends SimpleApplication implements EditorJmeApplication {

    private static final Logger log = Logger.getLogger(SimpleJfxApplication.class.getName());

    private Thread jmeThread;
    private EditorFxImageView imageView;
    private ImageViewFrameTransferSceneProcessor sceneProcessor;


    private Node editorNode = new Node("editorNode");

    private EditorScene scene;
    private File secenFile;

    private boolean initialized = false;

    public SimpleJfxApplication(AppState... initialStates) {
        super(initialStates);
        jmeThread = new Thread(new ThreadGroup("LWJGL"), () -> {
            start();
        }, "LWJGL Render");
        imageView = new EditorFxImageView();
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);

        settings.setCustomRenderer(JmeOffscreenSurfaceContext.class);
        settings.setResizable(true);

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

    private void initJavaFxImage() {

        imageView.getProperties().put(JfxMouseInput.PROP_USE_LOCAL_COORDS, true);
        // imageView.setMouseTransparent(true);
        imageView.setFocusTraversable(true);

        List<ViewPort> vps = renderManager.getPostViews();
        ViewPort last = vps.get(vps.size() - 1);

        sceneProcessor = new ImageViewFrameTransferSceneProcessor();
        sceneProcessor.bind(imageView, this, last);
        sceneProcessor.setEnabled(true);
        sceneProcessor.setTransferMode(FrameTransferSceneProcessor.TransferMode.ON_CHANGES);
    }

    @Override
    public void simpleInitApp() {
        rootNode.attachChild(editorNode);
        initJavaFxImage();

        viewPort.setBackgroundColor(ColorRGBA.Black);


        log.info("jMonkeyEngine Initialized.");

        initApp();

        initialized = true;

        EditorEventBus.post(new JmeStartCompleteEvent(this));
    }

    public EditorFxImageView getImageView() {
        return imageView;
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void reBindPostViewPort() {
        SimpleJfxApplication simpleJfxApplication = this;
        Platform.runLater(() -> {
//            sceneProcessor.unbind();
            sceneProcessor = new ImageViewFrameTransferSceneProcessor();
            List<ViewPort> vps = renderManager.getPostViews();
            ViewPort last = vps.get(vps.size() - 1);
            sceneProcessor.bind(imageView, this, last);
            sceneProcessor.setEnabled(true);
            sceneProcessor.setTransferMode(FrameTransferSceneProcessor.TransferMode.ON_CHANGES);
        });


    }

    @Override
    public Thread getThread() {
        return jmeThread;
    }

    public boolean isJmeThread() {
        return Thread.currentThread() == jmeThread;
    }



    public void initApp() {
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(6);



//        rootNode.attachChild(box);

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

package com.ooqn.assist.modules.jme;


import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;

import java.util.List;
import java.util.logging.Logger;

public abstract class SimpleJfxApplication extends SimpleApplication {

    private static final Logger log = Logger.getLogger(SimpleJfxApplication.class.getName());

    private Thread jmeThread;

    private EditorFxImageView imageView;
    private ImageViewFrameTransferSceneProcessor sceneProcessor;

    private boolean initialized = false;

    public SimpleJfxApplication(AppState... initialStates) {
        super(initialStates);

        jmeThread = Thread.currentThread();

        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);

        settings.setCustomRenderer(JmeOffscreenSurfaceContext.class);
        settings.setResizable(true);

        // setPauseOnLostFocus(false);

        setSettings(settings);

        createCanvas();

    }

    public void start() {
        JmeOffscreenSurfaceContext canvasContext = (JmeOffscreenSurfaceContext) getContext();
        canvasContext.setApplication(this);
        canvasContext.setSystemListener(this);
        startCanvas(true);
    }

    private void initJavaFxImage() {

        imageView = new EditorFxImageView();
        imageView.getProperties().put(JfxMouseInput.PROP_USE_LOCAL_COORDS, true);
        // imageView.setMouseTransparent(true);
        imageView.setFocusTraversable(true);

        List<ViewPort> vps = renderManager.getPostViews();
        ViewPort last = vps.get(vps.size()-1);

        sceneProcessor = new ImageViewFrameTransferSceneProcessor();
        sceneProcessor.bind(imageView, this, last);
        sceneProcessor.setEnabled(true);

        sceneProcessor.setTransferMode(FrameTransferSceneProcessor.TransferMode.ON_CHANGES);





        // setView3d();

    }

    @Override
    public void simpleInitApp() {
        initJavaFxImage();

        viewPort.setBackgroundColor(ColorRGBA.Black);

        initialized = true;

        log.info("jMonkeyEngine Initialized.");

        initApp();
    }

    public EditorFxImageView getImageView() {
        return imageView;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isJmeThread() {
        return Thread.currentThread() == jmeThread;
    }

    public AppSettings getSettings() {
        return settings;
    }

    public abstract void initApp();

}

package com.ooqn.modules;


import java.util.List;
import java.util.logging.Logger;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.ooqn.modules.jme.EditorFxImageView;
import com.ooqn.modules.jme.FrameTransferSceneProcessor;
import com.ooqn.modules.jme.ImageViewFrameTransferSceneProcessor;
import com.ooqn.modules.jme.JfxMouseInput;
import com.ooqn.modules.jme.JmeOffscreenSurfaceContext;

public class SimpleJfxApplication extends SimpleApplication {

    private static final Logger log = Logger.getLogger(SimpleJfxApplication.class.getName());

    private Thread jmeThread;

    private EditorFxImageView imageView;
    private ImageViewFrameTransferSceneProcessor sceneProcessor;

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
    private Geometry box;
    public void initApp() {
        flyCam.setDragToRotate(true);
        flyCam.setMoveSpeed(6);

        DirectionalLight directionalLight = new DirectionalLight(
                new Vector3f(-1, -1, -1).normalizeLocal(),
                ColorRGBA.White.clone()
        );

        rootNode.addLight(directionalLight);

        Texture texture = assetManager.loadTexture("com/jme3/app/Monkey.png");

        box = new Geometry("Box", new Box(1,1,1));
        box.setMaterial(new Material(assetManager, Materials.PBR));
        box.getMaterial().setTexture("BaseColorMap", texture);
        box.getMaterial().setColor("BaseColor", ColorRGBA.White);
        box.getMaterial().setFloat("Roughness", 0.001f);
        box.getMaterial().setFloat("Metallic", 0.001f);

        rootNode.attachChild(box);

    }

    @Override
    public void simpleUpdate(float tpf) {
        box.rotate(tpf * 1f, tpf * 1f, tpf * 1f);
    }

    
}

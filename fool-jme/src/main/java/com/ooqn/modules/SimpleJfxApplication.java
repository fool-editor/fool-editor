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

        EditorEventBus.editorEventBus.post(new JmeStartCompleteEvent());
    }

    public EditorFxImageView getImageView() {
        return imageView;
    }

    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void reBindPostViewPort() {
        SimpleJfxApplication simpleJfxApplication=this;
        Platform.runLater(() -> {
            sceneProcessor.unbind();
            List<ViewPort> postViews = getRenderManager().getPostViews();
            sceneProcessor.bind(imageView, simpleJfxApplication, postViews.get(postViews.size() - 1));
        });
    }

    public boolean isJmeThread() {
        return Thread.currentThread() == jmeThread;
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

        box = new Geometry("Box", new Box(1, 1, 1));
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
        if(this.scene!=null){
            try {
                scene.save(secenFile);
            } catch (IOException e) {
                AlertHandel.exceptionHandel("场景保存失败",e);
                return;
            }
            editorNode.detachChild(scene.getSceneNode());
        }
        this.scene = scene;
        this.secenFile=file;
        editorNode.attachChild(scene.getSceneNode());
        EditorEventBus.editorEventBus.post(new OpenSceneEvent(scene));
    }
}

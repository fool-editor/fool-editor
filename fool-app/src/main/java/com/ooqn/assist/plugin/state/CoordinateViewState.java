package com.ooqn.assist.plugin.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.util.Screenshots;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.core.EditorJmeApplication;
import com.ooqn.modules.SimpleJfxApplication;
import com.ooqn.modules.jme.post.DefSceneProcessor;

/**
 * 右上角的坐标
 */
public class CoordinateViewState extends AbstractAppState {
    Application application;
    private Node node;
    private Camera camera;

    private float coordinateViewSize = 100;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.application = app;
        Geometry X = creatLing(Vector3f.UNIT_X, ColorRGBA.Red);
        Geometry Y = creatLing(Vector3f.UNIT_Y, ColorRGBA.Green);
        Geometry Z = creatLing(Vector3f.UNIT_Z, ColorRGBA.Blue);

        node = new Node();

        node.attachChild(X);
        node.attachChild(Y);
        node.attachChild(Z);
        node.setLocalTranslation(0, 0, -5f);
        camera = new Camera(app.getCamera().getWidth(), app.getCamera().getHeight());
        camera.setName("CoordinateViewCamera");
        camera.setFrustumPerspective(45f, (float) camera.getWidth() / camera.getHeight(), 1f, 1000f);
        camera.setLocation(new Vector3f(0f, 0f, 0f));
        camera.lookAt(new Vector3f(0f, 0f, -5f), Vector3f.UNIT_Y);
        camera.setViewPort(1 - coordinateViewSize / camera.getWidth(), 1, 1 - coordinateViewSize / camera.getHeight(), 1);
        camera.resize(camera.getWidth(),camera.getHeight(),true);



        RenderManager renderManager = application.getRenderManager();
        ViewPort coordinateView = renderManager.createPostView("CoordinateView", camera);
        coordinateView.setClearFlags(false, false, false);
        coordinateView.attachScene(node);
//        coordinateView.setBackgroundColor(ColorRGBA.White);
        coordinateView.addProcessor(new SceneProcessor());


    }

    @Override
    public void update(float tpf) {


        Camera camera = application.getCamera();
        Quaternion rotation = camera.getRotation();
        this.camera.setRotation(rotation);

        Vector3f direction = camera.getDirection();

        Vector3f mult = direction.mult(3);
        node.setLocalTranslation(mult);

        node.updateLogicalState(tpf);
        node.updateGeometricState();
    }

    private Geometry creatLing(Vector3f end, ColorRGBA colorRGBA) {
        Line line = new Line(Vector3f.ZERO, end);
        Geometry geometry = new Geometry(end.toString());
        geometry.setMesh(line);
        Material material = new Material(application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", colorRGBA);
        geometry.setMaterial(material);
        return geometry;
    }


    private class SceneProcessor implements DefSceneProcessor {
        boolean initialized = false;

        @Override
        public void initialize(RenderManager rm, ViewPort vp) {
            initialized = true;
        }

        @Override
        public boolean isInitialized() {
            return initialized;
        }

        @Override
        public void reshape(ViewPort vp, int w, int h) {
            camera.setFrustumPerspective(45f, (float) w / h, 1f, 1000f);
            camera.setViewPort(1f - coordinateViewSize / w, 1f, 1f - coordinateViewSize / h, 1f);
            camera.resize(w,h,true);
        }
    }
}

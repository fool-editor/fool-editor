package com.ooqn.assist.modules.jme;

import com.jme3.app.state.AppState;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class MyJmeGame extends SimpleJfxApplication {

    public MyJmeGame(AppState... initialStates) {
        super(initialStates);
    }

    private Geometry box;

    @Override
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
        box.rotate(tpf * .2f, tpf * .3f, tpf * .4f);
    }

}

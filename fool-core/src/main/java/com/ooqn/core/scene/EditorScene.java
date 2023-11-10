package com.ooqn.core.scene;

import com.jme3.export.*;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.ooqn.core.scene.wrapper.Wrapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;

public class EditorScene implements Savable {
    @Getter
    private Node sceneNode;
    @Getter
    private ArrayList<Camera> cameras;

    private ArrayList<Wrapper> wrapperList;


    public EditorScene() {
        cameras = new ArrayList<>();
        wrapperList=new ArrayList<>();
    }


    public <T extends Wrapper> T getWrapper(Savable savable, Class<T> tClass){
        for (Wrapper wrapper : wrapperList) {
            if (wrapper.getData()==savable) {
                return (T)wrapper;
            }
        }
        try {
            T t = tClass.newInstance();
            t.setData(savable);
            wrapperList.add(t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(sceneNode,"sceneNode",new Node());
        capsule.writeSavableArrayList(cameras,"cameras",new ArrayList());
        capsule.writeSavableArrayList(wrapperList,"wrapperList",new ArrayList());
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        sceneNode = (Node) capsule.readSavable("sceneNode", new Node());
        cameras =  capsule.readSavableArrayList("cameras", new ArrayList());
        wrapperList =  capsule.readSavableArrayList("wrapperList", new ArrayList());
    }


    public static EditorScene newScene() {
        EditorScene scene = new EditorScene();
        scene.sceneNode = new Node("场景根节点");
        scene.sceneNode.attachChild(new Node("Node"));

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.3f));
        scene.sceneNode.addLight(ambientLight);

        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setColor(ColorRGBA.White.mult(0.5f));
        directionalLight.setDirection(new Vector3f(-1, -2, -3));
        scene.sceneNode.addLight(directionalLight);
        return scene;
    }


    public void save(File file) throws IOException {
        BinaryExporter binaryExporter = new BinaryExporter();
        binaryExporter.save(this,file);
    }
}

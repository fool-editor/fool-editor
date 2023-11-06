package com.ooqn.core.scene;

import com.jme3.export.*;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditorScene implements Savable {
    @Getter
    private Node sceneNode;
    @Getter
    private ArrayList<Camera> cameras;


    public EditorScene() {
        cameras = new ArrayList<>();
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(sceneNode,"sceneNode",new Node());
        capsule.writeSavableArrayList(cameras,"cameras",new ArrayList());
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        sceneNode = (Node) capsule.readSavable("sceneNode", new Node());
        cameras =  capsule.readSavableArrayList("cameras", new ArrayList());
    }


    public static EditorScene newScene() {
        EditorScene scene = new EditorScene();
        scene.sceneNode = new Node("场景根节点");
        scene.sceneNode.attachChild(new Node("Node"));
        return scene;
    }


    public void save(File file) throws IOException {
        BinaryExporter binaryExporter = new BinaryExporter();
        binaryExporter.save(this,file);
    }
}

package com.ooqn.core.scene;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
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
    private List<Camera> cameras;


    public EditorScene() {
        cameras = new ArrayList<>();
    }

    @Override
    public void write(JmeExporter ex) throws IOException {

    }

    @Override
    public void read(JmeImporter im) throws IOException {

    }


    public static EditorScene newScene() {
        EditorScene scene = new EditorScene();
        scene.sceneNode = new Node("sceneNode");
        return scene;
    }


    public void save(File file) throws IOException {
        BinaryExporter binaryExporter = new BinaryExporter();
        binaryExporter.save(this,file);
    }
}

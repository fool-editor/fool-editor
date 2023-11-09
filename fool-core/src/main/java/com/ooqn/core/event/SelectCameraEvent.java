package com.ooqn.core.event;

import com.jme3.renderer.Camera;

public class SelectCameraEvent extends Event{
    private Camera camera;

    public SelectCameraEvent(Object sources, Camera camera) {
        super(sources);
        this.camera = camera;
    }
}

package com.ooqn.core.event;

import com.jme3.scene.Spatial;

public class SelectSpatialEvent extends Event{
    private Spatial spatial;

    public SelectSpatialEvent(Object sources, Spatial spatial) {
        super(sources);
        this.spatial = spatial;
    }
}

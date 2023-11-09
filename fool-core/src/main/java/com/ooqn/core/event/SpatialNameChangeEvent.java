package com.ooqn.core.event;

import com.jme3.scene.Spatial;

public class SpatialNameChangeEvent extends Event{
    public final Spatial spatial;
    public SpatialNameChangeEvent(Object sources, Spatial spatial) {
        super(sources);
        this.spatial = spatial;
    }
}

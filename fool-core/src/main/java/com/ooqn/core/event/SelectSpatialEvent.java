package com.ooqn.core.event;

import com.jme3.scene.Spatial;

/**
 * 选择Spatial
 */
public class SelectSpatialEvent extends SelectEvent<Spatial>{
    public SelectSpatialEvent(Object sources, Spatial spatial) {
        super(sources,spatial);
    }
}

package com.ooqn.core.event;

import com.jme3.scene.Spatial;

/**
 * 选择Spatial
 */
public class SelectEvent<T> extends Event {
    public final T obj;

    public SelectEvent(Object sources, T obj) {
        super(sources);
        this.obj = obj;
    }


}

package com.ooqn.assist.jme.post;

import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.FrameBuffer;

public interface DefSceneProcessor extends SceneProcessor {
    @Override
    default void reshape(ViewPort vp, int w, int h) {

    }


    @Override
    default void preFrame(float tpf) {

    }

    @Override
    default void postQueue(RenderQueue rq) {

    }

    @Override
    default void postFrame(FrameBuffer out) {

    }

    @Override
    default void cleanup() {

    }

    @Override
    default void setProfiler(AppProfiler profiler) {

    }
}

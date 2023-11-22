package com.ooqn.core.builder.mesh;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Sphere;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.builder.AttributeBuilder;

import java.util.List;

/**
 * 用于生成 mesh 的  inspect
 *
 * @param <T>
 */
public abstract class MeshAttributeBuilder<T extends Mesh> implements AttributeBuilder<T> {


    @Override
    public void attribute(List<Attribute> attributes, T t) {

    }

    public abstract T createNewMesh();
}

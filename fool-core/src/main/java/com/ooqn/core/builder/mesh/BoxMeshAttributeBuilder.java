package com.ooqn.core.builder.mesh;

import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.ooqn.core.FoolContext;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.attribute.lmpl.FloatAttribute;
import com.ooqn.core.attribute.lmpl.IntAttribute;

import java.util.List;

public class BoxMeshAttributeBuilder extends MeshAttributeBuilder<Box> {
    @Override
    public boolean isHandle(Object o) {
        return o instanceof Box;
    }

    @Override
    public void attribute(List<Attribute> attributes, Box box) {
        super.attribute(attributes,box);
    }

    @Override
    public Box createNewMesh() {
        return new Box(0.5f,0.5f,0.5f);
    }
}

package com.ooqn.core.attribute.lmpl;


import com.jme3.math.Vector3f;
import com.ooqn.core.attribute.Attribute;
import javafx.scene.Node;

public class Vector3fAttribute extends Attribute<Vector3f> {

    public Vector3fAttribute(Vector3f value) {
        super(value);
    }

    public Vector3fAttribute(String title, Vector3f value) {
        super(title, value);
    }

    @Override
    public Node getUiNode() {
        return null;
    }
}

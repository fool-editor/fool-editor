package com.ooqn.core.attribute.lmpl;


import com.jme3.math.ColorRGBA;
import com.ooqn.core.attribute.Attribute;
import javafx.scene.Node;

public class FloatAttribute extends Attribute<Float> {

    public FloatAttribute(float value) {
        super(value);
    }

    @Override
    public Node getUiNode() {
        return null;
    }
}

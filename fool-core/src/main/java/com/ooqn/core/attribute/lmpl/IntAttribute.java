package com.ooqn.core.attribute.lmpl;


import com.ooqn.core.attribute.Attribute;
import javafx.scene.Node;

public class IntAttribute extends Attribute<Integer> {

    public IntAttribute(int value) {
        super(value);
    }

    @Override
    public Node getUiNode() {
        return null;
    }
}

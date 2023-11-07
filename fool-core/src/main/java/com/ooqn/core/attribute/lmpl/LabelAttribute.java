package com.ooqn.core.attribute.lmpl;


import com.ooqn.core.attribute.Attribute;
import javafx.scene.Node;

public class LabelAttribute extends Attribute<String> {
    public LabelAttribute(String value) {
        super(value);
    }

    @Override
    public Node getUiNode() {
        return null;
    }
}

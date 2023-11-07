package com.ooqn.core.attribute.lmpl;


import com.ooqn.core.attribute.Attribute;
import javafx.scene.Node;

public class StringAttribute extends Attribute<String> {

    public StringAttribute(String value) {
        super(value);
    }

    @Override
    public Node getUiNode() {
        return null;
    }
}

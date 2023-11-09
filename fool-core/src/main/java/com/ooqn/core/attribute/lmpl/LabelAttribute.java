package com.ooqn.core.attribute.lmpl;


import com.ooqn.core.attribute.Attribute;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class LabelAttribute extends Attribute<String> {
    private Label label;

    public LabelAttribute() {
        root = new Label();
        label = (Label) root;
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        label.setText(title);
    }

}

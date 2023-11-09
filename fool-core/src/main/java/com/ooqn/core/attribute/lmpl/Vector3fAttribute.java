package com.ooqn.core.attribute.lmpl;


import com.jme3.math.Vector3f;
import com.ooqn.core.attribute.Attribute;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Vector3fAttribute extends Attribute<Vector3f> {

    @FXML
    public Label titleLabel;
    @FXML
    public TextField xTextField;
    @FXML
    public TextField yTextField;
    @FXML
    public TextField zTextField;


    @Override
    public void setValue(Vector3f value) {
        xTextField.setText(value.x + "");
        yTextField.setText(value.y + "");
        zTextField.setText(value.z + "");
    }

    @Override
    public Vector3f getValue() {
        return new Vector3f(
                Float.parseFloat(xTextField.getText()),
                Float.parseFloat(yTextField.getText()),
                Float.parseFloat(zTextField.getText()));
    }
}

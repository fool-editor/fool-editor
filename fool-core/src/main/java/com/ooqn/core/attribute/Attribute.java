package com.ooqn.core.attribute;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

public abstract class Attribute<T> {
    private ValueChangeListener<T> valueChangeListener;

    @FXML
    protected Node uiNode;
    @FXML
    public Label titleLabel;

    public Attribute() {
    }


    public abstract void setValue(T value);

    public abstract T getValue();

    public void setTitle(String title) {
        titleLabel.setText(title);
    }


    public void valueChange(T newV) {
        if (this.valueChangeListener != null) {
            this.valueChangeListener.change(newV);
        }
    }

    public void setValueChangeListener(ValueChangeListener<T> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public Node getUiNode() {
        return uiNode;
    }
}

package com.ooqn.core.attribute;


import javafx.fxml.FXML;
import javafx.scene.Node;

public abstract class Attribute<T> {
    private ValueChangeListener<T> valueChangeListener;

    @FXML
    protected Node root;

    public Attribute() {
    }


    public  abstract void setValue(T value);
    public  abstract T getValue();




    public void setValueChangeListener(ValueChangeListener<T> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public Node getUiNode() {
        return root;
    }
}

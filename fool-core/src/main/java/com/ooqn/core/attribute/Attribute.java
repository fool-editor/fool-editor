package com.ooqn.core.attribute;


import javafx.fxml.FXML;
import javafx.scene.Node;

public abstract class Attribute<T> {
    private T value;
    private String title;
    private ValueChangeListener<T> valueChangeListener;

    @FXML
    protected Node root;

    public Attribute() {
    }

    public void setValue(T value) {
        T old = this.value;
        this.value = value;
        if (valueChangeListener != null) {
            valueChangeListener.change(old, value);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public T getValue() {
        return value;
    }

    public void setValueChangeListener(ValueChangeListener<T> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public Node getUiNode() {
        return root;
    }
}

package com.ooqn.core.attribute;


import javafx.scene.Node;

public abstract class Attribute<T> {
    private T value;
    private ValueChangeListener<T> valueChangeListener;

    public Attribute(T value) {
        this.value = value;
    }

    public Attribute() {
    }

    public void setValue(T value) {
        T old=this.value;
        this.value = value;
        if(valueChangeListener!=null){
            valueChangeListener.change(old,value);
        }

    }

    public T getValue() {
        return value;
    }

    public void setValueChangeListener(ValueChangeListener<T> valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public  abstract Node getUiNode();
}

package com.ooqn.assist.fx.control;

import lombok.Getter;

@Getter
public class TreeItemValue<T> {
    private T value;
    private String label;
    public TreeItemValue(T value, String label) {
        this.value = value;
        this.label = label;
    }
    public void setValue(T value,String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}

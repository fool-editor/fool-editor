package com.ooqn.core.attribute;

@FunctionalInterface
public interface ValueChangeListener<T> {
    void change(T newValue);
}

package com.ooqn.core.builder;

import com.ooqn.core.attribute.Attribute;

import java.util.List;

public interface AttributeBuilder<T> {
    boolean isHandle(Object o);
    void attribute(List<Attribute> attributes,T t);
}

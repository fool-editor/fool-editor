package com.ooqn.core.builder.light;

import com.jme3.light.DirectionalLight;
import com.ooqn.core.FoolContext;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.attribute.lmpl.Vector3fAttribute;

import java.util.List;

public class DirectionalLightAttributeBuilder implements LightAttributeBuilder<DirectionalLight>{
    @Override
    public boolean isHandle(Object o) {
        return o instanceof DirectionalLight;
    }

    @Override
    public void attribute(List<Attribute> attributes, DirectionalLight directionalLight) {
        Vector3fAttribute vector3fAttribute = Vector3fAttribute.newInstance();
        vector3fAttribute.setTitle("方向:");
        vector3fAttribute.setValue(directionalLight.getDirection());
        vector3fAttribute.setValueChangeListener(newValue -> FoolContext.runJmeThread(() -> {
            directionalLight.setDirection(newValue);
        }));
        attributes.add(vector3fAttribute);
    }
}

package com.ooqn.core.builder.light;

import com.jme3.light.Light;
import com.ooqn.core.FoolContext;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.attribute.lmpl.ColorRgbaAttribute;
import com.ooqn.core.attribute.lmpl.FloatAttribute;
import com.ooqn.core.scene.EditorScene;
import com.ooqn.core.scene.wrapper.LightWrapper;

import java.util.List;

public class BaseLightAttributeBuilder implements LightAttributeBuilder<Light>{
    @Override
    public boolean isHandle(Object o) {
        return o instanceof Light;
    }

    @Override
    public void attribute(List<Attribute> attributes, Light light) {
        EditorScene scene = FoolContext.getEditorJmeApplication().getScene();
        LightWrapper wrapper = scene.getWrapper(light, LightWrapper.class);
        ColorRgbaAttribute colorRgbaAttribute = ColorRgbaAttribute.newInstance();
        colorRgbaAttribute.setTitle("颜色");
        colorRgbaAttribute.setValue(wrapper.getColorRGBA());
        colorRgbaAttribute.setValueChangeListener(newValue -> {
            FoolContext.runJmeThread(() -> {
                wrapper.setColorRGBA(newValue);
            });
        });

        FloatAttribute attribute = FloatAttribute.newInstance();
        attribute.setTitle("强度");
        attribute.setValue(wrapper.getIntensity());
        attribute.setValueChangeListener(newValue -> {
            FoolContext.runJmeThread(() -> {
                wrapper.setIntensity(newValue);
            });
        });

        attributes.add(colorRgbaAttribute);
        attributes.add(attribute);
    }
}

package com.ooqn.core.builder.mesh;

import com.jme3.scene.shape.Sphere;
import com.ooqn.core.FoolContext;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.attribute.lmpl.FloatAttribute;
import com.ooqn.core.attribute.lmpl.IntAttribute;

import java.util.List;

public class SphereMeshAttributeBuilder implements MeshAttributeBuilder<Sphere> {
    @Override
    public boolean isHandle(Object o) {
        return o instanceof Sphere;
    }

    @Override
    public void attribute(List<Attribute> attributes, Sphere mesh) {
        IntAttribute zSamples = IntAttribute.newInstance();
        zSamples.setTitle("zSamples");
        zSamples.setValue(mesh.getZSamples());
        zSamples.setValueChangeListener(newValue ->
                {
                    FoolContext.runJmeThread(() -> mesh.updateGeometry(newValue, mesh.getRadialSamples(), mesh.getRadius()));
                }
        );
        attributes.add(zSamples);

        IntAttribute radialSamples = IntAttribute.newInstance();
        radialSamples.setTitle("radialSamples");
        radialSamples.setValue(mesh.getRadialSamples());
        radialSamples.setValueChangeListener(newValue ->
                {
                    FoolContext.runJmeThread(() -> mesh.updateGeometry(mesh.getZSamples(), newValue, mesh.getRadius()));
                }
        );
        attributes.add(radialSamples);


        FloatAttribute radius = FloatAttribute.newInstance();
        radius.setTitle("radius");
        radius.setValue(mesh.getRadius());
        radius.setValueChangeListener(newValue ->
                {
                    FoolContext.runJmeThread(() -> mesh.updateGeometry(mesh.getZSamples(), mesh.getRadialSamples(), newValue));
                }
        );
        attributes.add(radius);
    }
}

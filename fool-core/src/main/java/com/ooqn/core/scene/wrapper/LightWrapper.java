package com.ooqn.core.scene.wrapper;

import com.jme3.export.*;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;

import java.io.IOException;

public class LightWrapper extends Wrapper<Light> {


    private ColorRGBA colorRGBA;
    private float intensity;


    @Override
    public void setData(Light data) {
        super.setData(data);
        colorRGBA=data.getColor().clone();
        intensity=1;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
        getData().setColor(colorRGBA.mult(intensity));
    }

    public void setColorRGBA(ColorRGBA colorRGBA) {
        this.colorRGBA = colorRGBA;
        getData().setColor(colorRGBA.mult(intensity));
    }

    public ColorRGBA getColorRGBA() {
        return colorRGBA;
    }

    public float getIntensity() {
        return intensity;
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(colorRGBA,"colorRGBA",null);
        capsule.write(intensity,"intensity",1);

    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule capsule = im.getCapsule(this);
        colorRGBA=(ColorRGBA) capsule.readSavable("colorRGBA",null);
        intensity= capsule.readFloat("intensity",1);
    }
}

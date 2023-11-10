package com.ooqn.core.scene.wrapper;

import com.jme3.export.*;

import java.io.IOException;

public class Wrapper<Date extends Savable> implements Savable {

    private Date data;

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(data,"value",null);
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        data=(Date) capsule.readSavable("value",null);
    }
}

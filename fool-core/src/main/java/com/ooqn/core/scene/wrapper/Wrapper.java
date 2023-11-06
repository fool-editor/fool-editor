package com.ooqn.core.scene.wrapper;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;

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

    }

    @Override
    public void read(JmeImporter im) throws IOException {

    }
}

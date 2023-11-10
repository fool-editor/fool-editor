package com.ooqn.core.attribute.lmpl;


import com.jme3.math.ColorRGBA;
import com.ooqn.core.attribute.Attribute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ColorRgbaAttribute extends Attribute<ColorRGBA> implements Initializable {

    @FXML
    public ColorPicker colorPicker;

    @Override
    public void setValue(ColorRGBA value) {
       colorPicker.setValue(Color.color(value.r,value.g,value.a,value.a));
    }

    @Override
    public ColorRGBA getValue() {
        Color value = colorPicker.getValue();
        return new ColorRGBA((float) value.getRed(),(float) value.getGreen(),(float) value.getBlue(),(float) value.getOpacity());
    }
    public static ColorRgbaAttribute newInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader(FloatAttribute.class.getClassLoader().getResource("fxml/attribute/colorRgbaAttribute.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ColorRgbaAttribute attribute = fxmlLoader.getController();
        return attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            ColorRGBA colorRGBA = new ColorRGBA((float) newValue.getRed(), (float) newValue.getGreen(), (float) newValue.getBlue(), (float) newValue.getOpacity());
            valueChange(colorRGBA);
        });
    }
}

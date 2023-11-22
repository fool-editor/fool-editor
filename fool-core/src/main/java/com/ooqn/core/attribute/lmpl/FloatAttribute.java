package com.ooqn.core.attribute.lmpl;


import com.ooqn.core.attribute.Attribute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FloatAttribute extends Attribute<Float> implements Initializable {

    private float step = 1.0f;

    private Float minValue;
    @FXML
    public TextField textField;


    @Override
    public void setValue(Float value) {
        textField.textProperty().setValue(value.toString());
    }

    @Override
    public Float getValue() {
        return Float.parseFloat(textField.getText());
    }

    public TextFormatter creatFloatTextFormatter() {
        return new TextFormatter<>(new FloatStringConverter(), 0f, c ->
                (c.getControlNewText().matches("-?\\d*\\.?\\d*")) ? c : null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textField.setTextFormatter(creatFloatTextFormatter());
        textField.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            String text = textField.getText();
            float v = Float.parseFloat(text);
            if (deltaY > 0) {
                v = v + step;
            } else if (deltaY < 0) {
                v = v + -step;
            }
            textField.setText(v + "");
        });
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            float v = Float.parseFloat(newValue);
            if (minValue != null && v < minValue) {
                setValue(minValue);
                return;
            }
            valueChange(getValue());
        });
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public static FloatAttribute newInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader(FloatAttribute.class.getClassLoader().getResource("fxml/attribute/floatAttribute.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FloatAttribute attribute = fxmlLoader.getController();
        return attribute;
    }


}

package com.ooqn.core.attribute.lmpl;


import com.jme3.math.Vector3f;
import com.ooqn.core.attribute.Attribute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.ScrollEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class Vector3fAttribute extends Attribute<Vector3f> implements Initializable {


    @FXML
    public TextField xTextField;
    @FXML
    public TextField yTextField;
    @FXML
    public TextField zTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 使用TextFormatter和DoubleStringConverter来限制输入为小数
        xTextField.setText("0");
        yTextField.setText("0");
        zTextField.setText("0");

        xTextField.setTextFormatter(creatFloatTextFormatter());
        yTextField.setTextFormatter(creatFloatTextFormatter());
        zTextField.setTextFormatter(creatFloatTextFormatter());


        xTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            change(zTextField, oldValue, newValue);
        });
        yTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            change(zTextField, oldValue, newValue);
        });
        zTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            change(zTextField, oldValue, newValue);
        });

        xTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            unFocus(zTextField, oldValue, newValue);
        });
        yTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            unFocus(zTextField, oldValue, newValue);
        });
        zTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            unFocus(zTextField, oldValue, newValue);
        });

        xTextField.setOnScroll(event -> {
            onScroll(xTextField, event);
        });
        yTextField.setOnScroll(event -> {
            onScroll(yTextField, event);
        });
        zTextField.setOnScroll(event -> {
            onScroll(zTextField, event);
        });
    }

    public TextFormatter creatFloatTextFormatter() {
        return new TextFormatter<>(new FloatStringConverter(), 0f, c ->
                (c.getControlNewText().matches("-?\\d*\\.?\\d*")) ? c : null);
    }

    private void onScroll(TextField textField, ScrollEvent event) {
        double deltaY = event.getDeltaY();
        String text = textField.getText();
        float v = Float.parseFloat(text);
        if (deltaY > 0) {
            v = v + 1;
        } else if (deltaY < 0) {
            v = v + -1;
        }
        textField.textProperty().setValue(v + "");
    }

    private void change(TextField textField, String oldValue, String newValue) {
        try {
            valueChange(getValue());
        }catch (Exception ex){

        }
    }

    private void unFocus(TextField textField, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
            String text = textField.getText();
            try {
                Float.parseFloat(text);
            } catch (Exception ex) {
                log.warn(ex.getMessage());
                textField.setText("0");
            }
        }
    }

    @Override
    public void setValue(Vector3f value) {
        xTextField.setText(value.x + "");
        yTextField.setText(value.y + "");
        zTextField.setText(value.z + "");
    }

    @Override
    public Vector3f getValue() {
        return new Vector3f(
                Float.parseFloat(xTextField.getText()),
                Float.parseFloat(yTextField.getText()),
                Float.parseFloat(zTextField.getText()));
    }


    public static Vector3fAttribute newInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader(Vector3fAttribute.class.getClassLoader().getResource("fxml/attribute/vector3Attribute.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Vector3fAttribute vector3fAttribute = fxmlLoader.getController();
        return vector3fAttribute;
    }


}

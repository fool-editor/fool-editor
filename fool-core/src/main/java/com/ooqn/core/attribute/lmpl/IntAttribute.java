package com.ooqn.core.attribute.lmpl;


import com.ooqn.core.attribute.Attribute;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntAttribute extends Attribute<Integer> implements Initializable {

    private int step = 1;
    @FXML
    public TextField textField;

    private Integer minValue;

    @Override
    public void setValue(Integer value) {
        textField.textProperty().setValue(value.toString());
    }

    @Override
    public Integer getValue() {
        return Integer.parseInt(textField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 使用TextFormatter来限制只能输入数字
        textField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, c ->
                (c.getControlNewText().matches("\\d*")) ? c : null));

        textField.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            String text = textField.getText();
            int v = Integer.parseInt(text);
            if (deltaY > 0) {
                v=v+step;
            } else if (deltaY < 0) {
                v=v+-step;
            }
            textField.setText(v+"");
        });
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            int v = Integer.parseInt(newValue);
            if(minValue!=null && v < minValue){
                setValue(minValue);
                return;
            }
            valueChange(v);
        });
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public static IntAttribute newInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader(FloatAttribute.class.getClassLoader().getResource("fxml/attribute/intAttribute.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IntAttribute attribute = fxmlLoader.getController();
        return attribute;
    }


}

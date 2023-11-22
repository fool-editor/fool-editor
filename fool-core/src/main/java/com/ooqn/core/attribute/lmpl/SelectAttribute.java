package com.ooqn.core.attribute.lmpl;

import com.ooqn.core.attribute.Attribute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectAttribute<T> extends Attribute<T> implements Initializable {

    @FXML
    public ChoiceBox<Option<T>> choiceBox;

    @Override
    public void setValue(T value) {
        for (Option<T> item : choiceBox.getItems()) {
            if (item.value.equals(value)) {
                choiceBox.setValue(item);
                return;
            }
        }
    }

    public void addOption(Option<T> option) {
        choiceBox.getItems().add(option);
    }


    public void addOption(T value, String label) {
        choiceBox.getItems().add(new Option<>(value, label));
    }


    @Override
    public T getValue() {
        return choiceBox.getValue().value;
    }

    public static <T> SelectAttribute<T> newInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader(Vector3fAttribute.class.getClassLoader().getResource("fxml/attribute/selectAttribute.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SelectAttribute<T> attribute = fxmlLoader.getController();
        return attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> valueChange(newValue.value));
    }

    public static class Option<T> {
        private final T value;
        private final String label;

        public Option(T value, String label) {
            this.value = value;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }


}

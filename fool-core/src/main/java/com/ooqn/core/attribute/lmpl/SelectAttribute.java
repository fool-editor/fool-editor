package com.ooqn.core.attribute.lmpl;

import com.ooqn.core.attribute.Attribute;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class SelectAttribute<T> extends Attribute<T> {

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

    public void addOption(Option<T> option){
        choiceBox.getItems().add(option);
    }


    public void addOption(T value, String label){
        choiceBox.getItems().add(new Option<>(value,label));
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

    public static class Option<T>{
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

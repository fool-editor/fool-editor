package com.ooqn.assist.fx.control.input;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberTextField extends TextField {

    private InputType inputType = InputType.Int;

    public NumberTextField() {
        init();
    }

    public NumberTextField(String text) {
        super(text);
        init();
    }

    private void init() {
        setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (change.isContentChange()) {
                try {
                    if (newText.isEmpty()) {
                        return change;
                    }
                    if(inputType.equals(InputType.Int)){
                        Integer.parseInt(newText);
                        return change;
                    }
                    if(inputType.equals(InputType.Float)){
                        Float.parseFloat(newText);
                        return change;
                    }
                    return change;
                } catch (NumberFormatException e) {
                    log.warn("{} 不是一个有效数字:{}",newText,e.getMessage());
                    return null; //不是有效的数字
                }
            }
            return change;
        }));
    }

    public float getFloatValue(){
        return Float.parseFloat(getText());
    }
    public int getIntValue(){
        return Integer.parseInt(getText());
    }
    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }


    public static enum InputType {
        Int, Float
    }
}

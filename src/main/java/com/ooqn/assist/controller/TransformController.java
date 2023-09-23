package com.ooqn.assist.controller;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TransformController implements Initializable  {

    @FXML
    public TextField textField;

    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    public void hanldeButtonAction(ActionEvent event) {
        label.setText(textField.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}

package com.ooqn.assist.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseInfoController implements Initializable {
    @FXML
    public Pane imgPane;
    @FXML
    public TextField input;
    @FXML
    public Label info;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

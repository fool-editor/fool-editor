package com.ooqn.assist.view;

import com.ooqn.assist.component.JmeComponent;
import com.ooqn.assist.core.FoolContextInitializable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, FoolContextInitializable {

    @FXML
    public TabPane bodyMainTabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new JmeComponent().init();
    }

    @Override
    public TabPane getBodyTop() {
        return bodyMainTabPane;
    }
}

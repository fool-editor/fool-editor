package com.ooqn.core.attribute;

import com.jme3.math.Plane;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class AttributeGroup<T extends Pane> {
    private List<Attribute> attributes=new ArrayList<>();

    @FXML
    private T pane;

    public AttributeGroup(T pane) {
        this.pane = pane;
    }

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
        Node uiNode = attribute.getUiNode();
        pane.getChildren().add(uiNode);
    }

    public T getUiNode() {
        return pane;
    }
}

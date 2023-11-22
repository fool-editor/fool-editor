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


public abstract class AttributeGroup<T extends Node> {


    @FXML
    public T uiNode;

    public AttributeGroup(T pane) {
        this.uiNode = pane;
    }

    public AttributeGroup() {
    }

    public abstract void addAttribute(Attribute attribute);
    public abstract void removeAttribute(Attribute attribute);
    public abstract int attributeSize();
    public abstract void removeAttribute(int start,int end);
    public  void addAttribute(List<Attribute> attributes){
        for (Attribute attribute : attributes) {
            addAttribute(attribute);
        }
    }
    public T getUiNode() {
        return uiNode;
    }
}

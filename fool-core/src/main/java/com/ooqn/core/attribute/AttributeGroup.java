package com.ooqn.core.attribute;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class AttributeGroup extends TitledPane{

   private VBox vbox;
    public AttributeGroup(String title) {
        setText(title);
        vbox=new VBox();
        setContent(vbox);
    }
    private List<Attribute> attributes=new ArrayList<>();

    public void addAttribute(Attribute attribute){
        attributes.add(attribute);
        Node uiNode = attribute.getUiNode();
        vbox.getChildren().add(uiNode);
    }

}

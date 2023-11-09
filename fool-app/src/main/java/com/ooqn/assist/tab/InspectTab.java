package com.ooqn.assist.tab;

import com.ooqn.core.attribute.AttributeGroup;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * 检查窗口Tab
 */
public class InspectTab extends Tab {

    private VBox vBox = new VBox();

    public InspectTab() {
        setText("检查");
        setContent(vBox);
    }
    public void clearChildren(){
        vBox.getChildren().clear();
    }
    public void addNode(Node node){
        vBox.getChildren().add(node);
    }
    public void addAttributeGroup(AttributeGroup attributeGroup){

    }
}

package com.ooqn.core.attribute.group;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.attribute.AttributeGroup;
import com.ooqn.core.attribute.lmpl.Vector3fAttribute;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.SpatialNameChangeEvent;
import com.ooqn.core.fx.Svg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BaseInfoAttributeGroup extends AttributeGroup<AnchorPane> implements Initializable {

    @FXML
    public Pane imgPane;
    @FXML
    public TextField input;
    @FXML
    public Label info;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(Spatial spatial) {
        input.setText(spatial.getName());
        BaseInfoAttributeGroup baseInfoAttribute = this;
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            spatial.setName(newValue);
            EditorEventBus.post(new SpatialNameChangeEvent(baseInfoAttribute, spatial));
        });
        info.setText(spatial.getClass().getName());
        if (spatial instanceof Node) {
            imgPane.getChildren().add(new Svg(50, "icon/node.svg"));
        }else if(spatial instanceof Spatial) {
            imgPane.getChildren().add(new Svg(50, "icon/spatial.svg"));
        }
    }

    @Override
    public void addAttribute(Attribute attribute) {

    }

    @Override
    public void removeAttribute(Attribute attribute) {

    }

    @Override
    public int attributeSize() {
        return 0;
    }

    @Override
    public void removeAttribute(int start, int end) {
    }



    public static BaseInfoAttributeGroup newInstance()  {
        FXMLLoader fxmlLoader = new FXMLLoader(Vector3fAttribute.class.getClassLoader().getResource("fxml/group/baseInfoGroup.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BaseInfoAttributeGroup group = fxmlLoader.getController();
        return group;
    }

}

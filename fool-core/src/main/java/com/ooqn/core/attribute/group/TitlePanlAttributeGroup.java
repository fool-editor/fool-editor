package com.ooqn.core.attribute.group;

import com.ooqn.core.attribute.*;
import com.ooqn.core.attribute.lmpl.Vector3fAttribute;
import com.ooqn.core.fx.Svg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TitlePanlAttributeGroup extends AttributeGroup<AnchorPane> implements Initializable, GroupDelete {

    @FXML
    public VBox vBox;
    @FXML
    public TitledPane titledPane;

    private HBox graphicHBox;
    private Node icon;
    private CheckBox enableCheckBox;
    private boolean showEnableCheckBox;

    private boolean allowDelete = true;

    private MenuItem deleteMenuItem;

    private ValueChangeListener<Boolean> enableValueChangeListener;
    private List<CallBack> deleteCallBacks;

    public TitlePanlAttributeGroup() {
        showEnableCheckBox = false;
        deleteCallBacks=new ArrayList<>();
    }

    public void setTitle(String title) {
        titledPane.setText(title);
    }

    public void setIcon(Node node) {
        if (this.icon == null) {
            graphicHBox.getChildren().add(0, node);
        } else {
            graphicHBox.getChildren().set(0, node);
        }
        this.icon = node;
    }

    public void setEnableValueChangeListener(ValueChangeListener<Boolean> enableValueChangeListener) {
        this.enableValueChangeListener = enableValueChangeListener;
    }

    public void setShowEnable(boolean showEnable) {
        if (!showEnableCheckBox && showEnable) {
            graphicHBox.getChildren().add(enableCheckBox);
        }
        if (showEnableCheckBox && !showEnable) {
            graphicHBox.getChildren().remove(enableCheckBox);
        }
    }

    public void setEnable(boolean enable) {
        enableCheckBox.setSelected(enable);
    }

    @Override
    public void addAttribute(Attribute attribute) {
        vBox.getChildren().add(attribute.getUiNode());
    }

    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
        deleteMenuItem.setDisable(!allowDelete);
    }

    public static TitlePanlAttributeGroup newInstance() {
        FXMLLoader fxmlLoader = new FXMLLoader(Vector3fAttribute.class.getClassLoader().getResource("fxml/group/titlePanlAttributeGroup.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TitlePanlAttributeGroup group = fxmlLoader.getController();
        return group;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicHBox = new HBox();
        enableCheckBox = new CheckBox();
        enableCheckBox.setPadding(new Insets(0, 0, 0, 5));
        enableCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (enableValueChangeListener != null) {
                enableValueChangeListener.change(newValue);
            }
        });
        titledPane.setGraphic(graphicHBox);
        deleteMenuItem = new MenuItem("删除      ", new Svg(16, "icon/delete.svg"));
        deleteMenuItem.setOnAction(event -> {
            for (CallBack deleteCallBack : deleteCallBacks) {
                deleteCallBack.callBack();
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(deleteMenuItem);
        titledPane.setContextMenu(contextMenu);

    }

    @Override
    public void addDeleteCallBack(CallBack callBack) {
        deleteCallBacks.add(callBack);
    }
}

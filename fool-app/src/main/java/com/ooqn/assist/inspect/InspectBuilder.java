package com.ooqn.assist.inspect;

import com.jme3.scene.Spatial;
import com.ooqn.assist.App2;
import com.ooqn.assist.fx.control.Svg;
import com.ooqn.core.BaseInfoController;
import com.ooqn.core.attribute.AttributeGroup;
import com.ooqn.core.event.EditorEventBus;
import com.ooqn.core.event.SpatialNameChangeEvent;
import com.ooqn.core.handel.AlertHandel;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InspectBuilder {


    public static List<AttributeGroup> creatAttributeGroup(Spatial spatial) {
        try {
            List<AttributeGroup> attributeGroups = new ArrayList<>();
            attributeGroups.add(creatBaseInfo(spatial));
//            attributeGroups.add(creatTransform(spatial));
            return attributeGroups;
        } catch (Exception e) {
            AlertHandel.exceptionHandel(e);
            return new ArrayList<>();
        }
    }

    public static AttributeGroup creatBaseInfo(Spatial spatial) throws IOException {
        // 加载FXML文件
        FXMLLoader fxmlLoader = new FXMLLoader(App2.class.getClassLoader().getResource("baseInfo.fxml"));
        Pane root = fxmlLoader.load();
        AttributeGroup attributeGroup = new AttributeGroup(root);
        BaseInfoController baseInfoController = fxmlLoader.getController();
        baseInfoController.input.setText(spatial.getName());
        baseInfoController.input.textProperty().addListener((observable, oldValue, newValue) -> {
            spatial.setName(newValue);
            EditorEventBus.post(new SpatialNameChangeEvent(attributeGroup, spatial));
        });
        baseInfoController.info.setText(spatial.getClass().getName());
        baseInfoController.imgPane.getChildren().add(new Svg(50,"icon/node.svg"));
        return attributeGroup;
    }

    private static AttributeGroup creatTransform(Spatial spatial) {
//        AttributeGroup attributeGroup = new AttributeGroup("Transform");
//        Transform localTransform = spatial.getLocalTransform();
//        Vector3f translation = localTransform.getTranslation();
//        Vector3f scale = localTransform.getScale();
//        Quaternion rotation = localTransform.getRotation();
//        float[] angles = rotation.toAngles(null);
//        Vector3f angle = new Vector3f(angles[0], angles[1], angles[2]);
//
//        LabelAttribute labelAttribute = new LabelAttribute("translation");
//        Vector3fAttribute vector3fAttribute=new Vector3fAttribute("translation",translation);
//        Vector3fAttribute scaleAttribute=new Vector3fAttribute("scale",scale);
//        Vector3fAttribute rotationAttribute=new Vector3fAttribute("rotation",angle);
//
//        attributeGroup.addAttribute(labelAttribute);
//        attributeGroup.addAttribute(vector3fAttribute);
//        attributeGroup.addAttribute(scaleAttribute);
//        attributeGroup.addAttribute(rotationAttribute);

//        return attributeGroup;
        return null;

    }
}

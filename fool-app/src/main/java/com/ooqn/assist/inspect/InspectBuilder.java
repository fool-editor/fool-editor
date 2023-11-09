package com.ooqn.assist.inspect;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.ooqn.core.attribute.AttributeGroup;
import com.ooqn.core.attribute.lmpl.LabelAttribute;
import com.ooqn.core.attribute.lmpl.Vector3fAttribute;

import java.util.ArrayList;
import java.util.List;

public class InspectBuilder {


    public static List<AttributeGroup> creatAttributeGroup(Spatial spatial) {
        List<AttributeGroup> attributeGroups=new ArrayList<>();
        attributeGroups.add(creatTransform(spatial));
        return attributeGroups;
    }

    private static AttributeGroup creatTransform(Spatial spatial){
        AttributeGroup attributeGroup = new AttributeGroup("Transform");
        Transform localTransform = spatial.getLocalTransform();
        Vector3f translation = localTransform.getTranslation();
        Vector3f scale = localTransform.getScale();
        Quaternion rotation = localTransform.getRotation();
        float[] angles = rotation.toAngles(null);
        Vector3f angle = new Vector3f(angles[0], angles[1], angles[2]);
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

        return attributeGroup;


    }
}

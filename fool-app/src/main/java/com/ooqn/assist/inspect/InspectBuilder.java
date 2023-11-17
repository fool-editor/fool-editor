package com.ooqn.assist.inspect;

import cn.hutool.core.util.ClassUtil;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.ooqn.core.FoolContext;
import com.ooqn.core.attribute.Attribute;
import com.ooqn.core.attribute.group.BaseInfoAttributeGroup;
import com.ooqn.core.attribute.AttributeGroup;
import com.ooqn.core.attribute.group.TitlePanlAttributeGroup;
import com.ooqn.core.attribute.lmpl.ColorRgbaAttribute;
import com.ooqn.core.attribute.lmpl.FloatAttribute;
import com.ooqn.core.attribute.lmpl.SelectAttribute;
import com.ooqn.core.attribute.lmpl.Vector3fAttribute;
import com.ooqn.core.builder.AttributeBuilder;
import com.ooqn.core.builder.mesh.SphereMeshAttributeBuilder;
import com.ooqn.core.handel.AlertHandel;
import com.ooqn.core.plugin.Plugin;
import com.ooqn.core.plugin.PluginManager;
import com.ooqn.core.scene.EditorScene;
import com.ooqn.core.scene.wrapper.LightWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class InspectBuilder {
    private static List<AttributeBuilder> attributeBuilders;


    public static List<AttributeGroup> createAttributeGroup(Spatial spatial) {
        checkAttributeBuilders();
        try {
            List<AttributeGroup> attributeGroups = new ArrayList<>();
            attributeGroups.add(createBaseInfo(spatial));
            attributeGroups.add(createTransform(spatial));
            if (spatial instanceof Geometry) {
                attributeGroups.add(createMesh((Geometry) spatial));
            }

            List<AttributeGroup> lights = createLight(spatial);
            attributeGroups.addAll(lights);

            return attributeGroups;
        } catch (Exception e) {
            AlertHandel.exceptionHandel(e);
            return new ArrayList<>();
        }
    }

    public static AttributeGroup createBaseInfo(Spatial spatial) {
        checkAttributeBuilders();
        // 加载FXML文件
        BaseInfoAttributeGroup baseInfo = BaseInfoAttributeGroup.newInstance();
        baseInfo.init(spatial);
        return baseInfo;
    }


    public static List<AttributeGroup> createLight(Spatial spatial) {
        checkAttributeBuilders();
        List<AttributeGroup> groups = new ArrayList<>();
        LightList localLightList = spatial.getLocalLightList();
        for (Light light : localLightList) {
            TitlePanlAttributeGroup group = TitlePanlAttributeGroup.newInstance();
            group.setTitle(light.getClass().getSimpleName() + "{" + light.getType() + "}");
            group.setShowEnable(true);
            group.setEnable(light.isEnabled());
            group.setEnableValueChangeListener(newValue -> FoolContext.runJmeThread(() -> {
                light.setEnabled(newValue);
            }));
            group.addDeleteCallBack(() -> {
                spatial.removeLight(light);
            });
            List<Attribute> attributes=new ArrayList<>();
            for (AttributeBuilder attributeBuilder : getAttributeBuilders(light)) {
                attributeBuilder.attribute(attributes,light);
            }
            group.addAttribute(attributes);
        }

        return groups;

    }


    private static AttributeGroup createTransform(Spatial spatial) {
        TitlePanlAttributeGroup titlePanlAttributeGroup = TitlePanlAttributeGroup.newInstance();
        titlePanlAttributeGroup.setTitle("Transform");
        titlePanlAttributeGroup.titledPane.setExpanded(true);

        Vector3fAttribute translationAttribute = Vector3fAttribute.newInstance();
        translationAttribute.setTitle("translation:");
        Vector3f localTranslation = spatial.getLocalTranslation();
        translationAttribute.setValue(localTranslation);
        translationAttribute.setValueChangeListener((newValue) -> {
            FoolContext.runJmeThread(() -> {
                spatial.setLocalTranslation(newValue);
            });
        });
        titlePanlAttributeGroup.addAttribute(translationAttribute);

        Vector3fAttribute rotationAttribute = Vector3fAttribute.newInstance();
        rotationAttribute.setTitle("rotation:");
        float[] angles = spatial.getLocalRotation().toAngles(null);
        rotationAttribute.setValue(new Vector3f(angles[0] / FastMath.PI * 180, angles[1] / FastMath.PI * 180, angles[2] / FastMath.PI * 180));
        rotationAttribute.setValueChangeListener((newValue) -> {
            FoolContext.runJmeThread(() -> {
                Quaternion q = new Quaternion();
                q.fromAngles(newValue.x / 180 * FastMath.PI, newValue.y / 180 * FastMath.PI, newValue.z / 180 * FastMath.PI);
                spatial.setLocalRotation(q);
            });

        });
        titlePanlAttributeGroup.addAttribute(rotationAttribute);

        Vector3fAttribute scaleAttribute = Vector3fAttribute.newInstance();
        scaleAttribute.setTitle("scale:");
        Vector3f scale = spatial.getLocalScale();
        scaleAttribute.setValue(scale);
        scaleAttribute.setValueChangeListener((newValue) -> {
            FoolContext.runJmeThread(() -> {
                spatial.setLocalScale(newValue);
            });
        });
        titlePanlAttributeGroup.addAttribute(scaleAttribute);
        return titlePanlAttributeGroup;
    }

    private static AttributeGroup createMesh(Geometry geometry) {

        TitlePanlAttributeGroup group = TitlePanlAttributeGroup.newInstance();
        group.setTitle("Mesh");

        SelectAttribute<Mesh> attribute = SelectAttribute.newInstance();
        group.addAttribute(attribute);
        attribute.setTitle("网格：");
        attribute.addOption(geometry.getMesh(), geometry.getMesh().getClass().getSimpleName());
        attribute.setValue(geometry.getMesh());
        attribute.setValueChangeListener(newValue -> {

        });
        List<Attribute> attributes = new ArrayList<>();
        List<SphereMeshAttributeBuilder> meshAttributeBuilders = getAttributeBuilders(SphereMeshAttributeBuilder.class);
        for (SphereMeshAttributeBuilder meshAttributeBuilder : meshAttributeBuilders) {
            if (meshAttributeBuilder.isHandle(geometry.getMesh())) {
                meshAttributeBuilder.attribute(attributes, (Sphere) geometry.getMesh());
            }
        }
        for (Attribute att : attributes) {
            group.addAttribute(att);
        }
        return group;
    }


    public static <T extends AttributeBuilder> List<T> getAttributeBuilders(Class<T> c) {
        checkAttributeBuilders();
        List<T> list = new ArrayList<>();
        for (AttributeBuilder attributeBuilder : attributeBuilders) {
            if (attributeBuilder.getClass().isAssignableFrom(c)) {
                list.add((T) attributeBuilder);
            }
        }
        return list;
    }

    public static List<AttributeBuilder> getAttributeBuilders(Object o) {
        List<AttributeBuilder> list = new ArrayList<>();
        for (AttributeBuilder attributeBuilder : attributeBuilders) {
            if (attributeBuilder.isHandle(o)) {
                list.add(attributeBuilder);
            }
        }
        return list;
    }


    private static void checkAttributeBuilders() {
        if (attributeBuilders != null) {
            return;
        }
        List<Plugin> plugins = PluginManager.getPlugins();
        Set<String> packageName = new HashSet<>();
        for (Plugin plugin : plugins) {
            Class<? extends Plugin> aClass = plugin.getClass();
            String[] split = aClass.getPackageName().split("\\.");
            packageName.add(split[0]);
        }
        Set<Class<?>> allClass = new HashSet<>();
        for (String name : packageName) {
            Set<Class<?>> classes = ClassUtil.scanPackage(name);
            allClass.addAll(classes);
        }
        attributeBuilders = new ArrayList<>();
        for (Class<?> aClass : allClass) {
            if (AttributeBuilder.class.isAssignableFrom(aClass) && !Modifier.isInterface(aClass.getModifiers()) && !Modifier.isAbstract(aClass.getModifiers())) {
                try {
                    AttributeBuilder attributeBuilder = (AttributeBuilder) aClass.newInstance();
                    attributeBuilders.add(attributeBuilder);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}

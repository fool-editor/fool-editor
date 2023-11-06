package com.ooqn.assist.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.XmlUtil;
import com.sun.javafx.scene.shape.SVGPathHelper;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SvgUtil {

    public static Node getSvg(String path) {
        Group group = new Group();
        URL resource = ResourceUtil.getResource(path);
        try (InputStream inputStream = resource.openStream()) {
            Document document = XmlUtil.readXML(inputStream);
            Element root = document.getDocumentElement();
            NodeList pathElements = root.getElementsByTagName("path");
            int length = pathElements.getLength();
            for (int i = 0; i < length; i++) {
                SVGPath svgPath = new SVGPath();
                Element pathElement = (Element) pathElements.item(i);
                String dAttribute = pathElement.getAttribute("d");
                String fillAttribute = pathElement.getAttribute("fill");
                String fillRuleAttribute = pathElement.getAttribute("fill-rule");
                if (fillAttribute != null) {
                    Color color = Color.web(fillAttribute);
                    svgPath.setFill(color);
                }
                if (fillRuleAttribute != null) {
                    switch (fillRuleAttribute.toLowerCase()) {
                        case "evenodd":
                            svgPath.setFillRule(FillRule.EVEN_ODD);
                    }
                }
                svgPath.setContent(dAttribute);
                group.getChildren().add(svgPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return group;
    }

    public static void main(String[] args) {
    }
}

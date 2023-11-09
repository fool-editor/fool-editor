package com.ooqn.assist.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Scale;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SvgUtil {

    public static Node getSvg(String path) {
        return getSvg(path, 16);
    }

    public static Node getSvg(String path, int size) {
        Group group = new Group();
        URL resource = ResourceUtil.getResource(path);
        try (InputStream inputStream = resource.openStream()) {
            Document document = XmlUtil.readXML(inputStream);
            Element root = document.getDocumentElement();
            String widthStr = root.getAttribute("width");
            String heightStr = root.getAttribute("height");
            int width = Integer.parseInt(widthStr == null ? "16" : widthStr);
            int height = Integer.parseInt(heightStr == null ? "16" : heightStr);
            group.setLayoutX(0);
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
                // 创建缩放变换
                Scale scale = new Scale(size * 1.0f / width, size * 1.0f / height);
                svgPath.getTransforms().add(scale);
            }
            group.setAutoSizeChildren(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return group;
    }


    private static class SvgXml {
        private Document document;
        private Group group;
        private Element root;

        private int width;
        private int height;

        private List<Element> gElement = new ArrayList<>();

        public SvgXml(Document document) {
            this.document = document;
            group = new Group();
            root = document.getDocumentElement();
            String widthStr = root.getAttribute("width");
            String heightStr = root.getAttribute("height");
            width = Integer.parseInt(widthStr);
            height = Integer.parseInt(heightStr);
            loopNodeList(root.getChildNodes());
        }

        private void loopNodeList(NodeList nodeList){
            int length = nodeList.getLength();
            for (int i = 0; i < length; i++) {
                org.w3c.dom.Node item = nodeList.item(i);
                if(item instanceof Element){
                    Element element =(Element) item;
                    if (element.getNodeName().equals("g")) {
                        gElement.add(element);
                    }
                    readNode(element);
                    loopNodeList(element.getChildNodes());
                    if (element.getNodeName().equals("g")) {
                        gElement.remove(gElement.size()-1);
                    }
                }
            }
        }

        private void readNode(Element element) {
            String nodeName = element.getNodeName();
            if (nodeName.equals("path")) {
                readPath(element);
                return;
            }

        }
        private void readPath(Element element) {
            String dAttribute = getAttribute(element,"d");
            String fillAttribute =  getAttribute(element,"fill");
            String fillRuleAttribute =  getAttribute(element,"fill-rule");
            SVGPath svgPath = new SVGPath();
            svgPath.setContent(dAttribute);
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
            group.getChildren().add(svgPath);
        }


        private String getAttribute(Element element, String key) {
            String attribute = element.getAttribute(key);
            if ( StrUtil.isBlankIfStr(attribute)) {
                for (int i = gElement.size() - 1; i >= 0; i--) {
                    attribute = gElement.get(i).getAttribute(key);
                    if (!StrUtil.isBlankIfStr(attribute)) {
                        return attribute;
                    }
                }
            }
            return attribute;
        }
    }

    public static void main(String[] args) {
        URL resource = ResourceUtil.getResource("icon/camera.svg");
        try (InputStream inputStream = resource.openStream()) {
            Document document = XmlUtil.readXML(inputStream);

            SvgXml svgXml = new SvgXml(document);
            Group group = svgXml.group;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

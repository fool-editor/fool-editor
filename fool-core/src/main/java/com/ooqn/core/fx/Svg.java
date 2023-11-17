package com.ooqn.core.fx;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Svg extends Group {

    private int svgWidth;
    private int svgHeight;

    private static final List<String> attCommon = List.of("fill", "transform");

    public Svg(String url) {
        String svgXml = FileUtil.readString(url, "utf-8");
        Document document = XmlUtil.readXML(svgXml);
        init(document);
    }

    public Svg(int size, String url) {
        String svgXml = FileUtil.readString(url, "utf-8");
        Document document = XmlUtil.readXML(svgXml);
        init(document);
        setSize(size, size);
    }

    private void init(Document document) {
        Element root = document.getDocumentElement();
        String widthStr = root.getAttribute("width");
        String heightStr = root.getAttribute("height");

        String viewBox = root.getAttribute("viewBox");
        if(StrUtil.isBlankIfStr(viewBox)){
            throw new IORuntimeException("svg 缺失viewBox");
        }
        String[] viewBoxs = viewBox.split(" ");
        int x=Integer.parseInt(viewBoxs[0]);
        int y=Integer.parseInt(viewBoxs[1]);
        int w=Integer.parseInt(viewBoxs[2]);
        int h=Integer.parseInt(viewBoxs[3]);
        svgWidth = w-x;
        svgHeight = h-y;
        List<Element> gElement = new ArrayList<>();
        loopNodeList(root.getChildNodes(), gElement);
        if(widthStr!=null && heightStr!=null){
            setSize(Integer.parseInt(widthStr),Integer.parseInt(heightStr));
        }
    }

    private void setSize(int width, int height) {
        for (javafx.scene.Node child : getChildren()) {
            // 创建缩放变换
            Scale scale = new Scale(width * 1.0f / this.svgWidth, height * 1.0f / this.svgHeight);
            child.getTransforms().add(scale);
        }
    }

    private void loopNodeList(NodeList nodeList, List<Element> gElement) {
        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            org.w3c.dom.Node item = nodeList.item(i);
            if (item instanceof Element) {
                Element element = (Element) item;
                if (element.getNodeName().equals("g")) {
                    gElement.add(element);
                } else {
                    readNode(element, gElement);
                }
                loopNodeList(element.getChildNodes(), gElement);
                if (element.getNodeName().equals("g")) {
                    gElement.remove(gElement.size() - 1);
                }
            }
        }
    }

    private void readNode(Element element, List<Element> gElement) {
        String nodeName = element.getNodeName();
        Shape sphere;
        switch (nodeName) {
            case "path":
                sphere = readPath(element, gElement);
                break;
            case "circle":
                sphere = readCircle(element, gElement);
                break;
            case "polygon":
                sphere = readPolygon(element, gElement);
                break;
            default:
                throw new IORuntimeException("不支持的标签：" + nodeName);
        }
        getChildren().add(sphere);
    }

    private Set<String> getAttributes(String... att) {
        HashSet<String> set = new HashSet<>();
        set.addAll(attCommon);
        for (String s : att) {
            set.add(s);
        }
        return set;
    }

    private Polygon readPolygon(Element element, List<Element> gElement) {
        Set<String> attributes = getAttributes("points");
        Polygon polygon = new Polygon();
        checkElementAttribute(attributes, element);
        for (String attKey : attributes) {
            String value = getAttribute(element, attKey, gElement);
            if (value != null) {
                switch (attKey) {
                    case "points":
                        String[] split = value.split(" ");
                        for (String s : split) {
                            polygon.getPoints().add(Double.parseDouble(s));
                        }
                        break;
                    default: {
                        setCommonAttribute(value, attKey, polygon);
                    }
                }
            }
        }
        return polygon;
    }

    private Circle readCircle(Element element, List<Element> gElement) {
        Set<String> attributes = getAttributes("cx", "cy", "r");
        Circle circle = new Circle();
        checkElementAttribute(attributes, element);
        for (String attKey : attributes) {
            String value = getAttribute(element, attKey, gElement);
            if (value != null) {
                switch (attKey) {
                    case "cx":
                        circle.setCenterX(Double.parseDouble(value));
                        break;
                    case "cy":
                        circle.setCenterY(Double.parseDouble(value));
                        break;
                    case "r":
                        circle.setRadius(Double.parseDouble(value));
                        break;
                    default: {
                        setCommonAttribute(value, attKey, circle);
                    }
                }
            }
        }
        return circle;
    }

    private SVGPath readPath(Element element, List<Element> gElement) {
        SVGPath svgPath = new SVGPath();
        Set<String> attributes = getAttributes("d","fill-rule");
        checkElementAttribute(attributes, element);
        for (String attKey : attributes) {
            String value = getAttribute(element, attKey, gElement);
            if (value != null) {
                switch (attKey) {
                    case "d":
                        svgPath.setContent(value);
                        break;
                    case "fill-rule":
                        switch (value.toLowerCase()) {
                            case "evenodd":
                                svgPath.setFillRule(FillRule.EVEN_ODD);
                                break;
                            default:
                                throw new IORuntimeException(element.getNodeName() + " fill-rule不支持的属性值：" + value);
                        }
                        break;
                    default: {
                        setCommonAttribute(value, attKey, svgPath);
                    }
                }
            }
        }
        return svgPath;
    }


    private void setCommonAttribute(String value, String att, Shape shape) {
        switch (att) {
            case "fill":
                Color color = Color.web(value);
                shape.setFill(color);
                break;
            case "transform":
                Pattern pattern = Pattern.compile("translate\\((.*?)\\)");
                Matcher matcher = pattern.matcher(value);
                while (matcher.find()) {
                    String match = matcher.group(1);
                    String[] split = match.split(" ");
                    Translate translate = new Translate(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                    shape.getTransforms().add(translate);
                }
                break;
        }

    }

    private void checkElementAttribute(Set<String> att, Element element) {
        NamedNodeMap attributes = element.getAttributes();
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            Node item = attributes.item(i);
            if (!att.contains(item.getNodeName())) {
                throw new IORuntimeException(element.getNodeName() + "不支持的属性：" + item.getNodeName());
            }
        }
    }

    private String getAttribute(Element element, String key, List<Element> gElement) {
        String attribute = element.getAttribute(key);
        if (StrUtil.isNotBlank(attribute)) {
            return attribute;
        }
        for (int i = gElement.size() - 1; i >= 0; i--) {
            attribute = gElement.get(i).getAttribute(key);
            if (StrUtil.isNotBlank(attribute)) {
                return attribute;
            }
        }
        return null;
    }


    public static void main(String[] args) {

    }
}

package com.ooqn.core.plugin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.eventbus.EventBus;
import com.ooqn.core.event.EditorEventBus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
@Slf4j
public class PluginManager {
    private static final HashMap<String, Plugin> plugins = new HashMap<>();

    public static void init() throws IOException {
        ClassLoader classLoader = PluginManager.class.getClassLoader();
        Enumeration<URL> resources = classLoader.getResources("plugin.pg");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            List<String> classNames = FileUtil.readLines(url, Charset.forName("UTF-8"));
            for (String className : classNames) {
                if (!StrUtil.isBlankIfStr(className)) {
                    log.info("load plugin:{}",className);
                    loadPlugin(className, classLoader);
                }
            }
        }
        for (Plugin plugin : plugins.values()) {
            plugin.init(EditorEventBus.eventBus);
        }
    }

    public static List<Plugin> getPlugins(){
        return new ArrayList<>(plugins.values());
    }

    private static void loadPlugin(String className, ClassLoader classLoader) {
        try {
            Class<?> aClass = classLoader.loadClass(className);
            Object o = aClass.getDeclaredConstructor().newInstance();
            if (o instanceof Plugin) {
                plugins.put(className, (Plugin) o);
            } else {
                System.err.println(className + " is not plugin");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

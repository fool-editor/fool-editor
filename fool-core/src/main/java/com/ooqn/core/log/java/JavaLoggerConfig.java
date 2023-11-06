package com.ooqn.core.log.java;

import cn.hutool.core.io.FileUtil;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.logging.LogManager;

public class JavaLoggerConfig {

    public static void load(){
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("logging.properties");
            String configuration= FileUtil.readString(resource,"utf-8");
            LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(configuration.getBytes()));
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}

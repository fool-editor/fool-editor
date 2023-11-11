package com.ooqn.assist;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.ooqn.core.log.AnsiOutput;
import com.ooqn.core.log.java.JavaLoggerConfig;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        URL resource = ResourceUtil.getResource("banner.txt");
        String banner = FileUtil.readString(resource, "utf-8");
        System.out.printf(banner);

        //加载默认java  logger
        JavaLoggerConfig.load();
        //启用彩色日志,抄的sprinboot  looggin
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        App.run(args);
    }
}

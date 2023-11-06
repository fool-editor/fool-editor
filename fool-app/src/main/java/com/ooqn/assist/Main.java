package com.ooqn.assist;

import com.ooqn.core.log.AnsiOutput;
import com.ooqn.core.log.java.JavaLoggerConfig;

public class Main {
    public static void main(String[] args) {
        //加载默认java  logger
        JavaLoggerConfig.load();
        //启用彩色日志,抄的sprinboot  looggin
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        App2.run(args);
    }
}

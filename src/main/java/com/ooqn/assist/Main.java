package com.ooqn.assist;

import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {
        
    System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());
        App.run(args);
    }
}

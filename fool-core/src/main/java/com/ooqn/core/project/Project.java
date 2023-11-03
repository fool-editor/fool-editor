package com.ooqn.core.project;

import java.io.File;

/**
 * 一个jme 工程
 * 有Gradle,Maven 项目。
 */
public interface Project {

    /**
     * project 目录
     * @return
     */
    File getProjectDir();

    /**
     * java 源码目录
     * @return
     */
    File getSrcJavaDir();

    /**
     * java resources 目录
     * @return
     */
    File getSrcResourcesDir();

    /**
     * 构建
     */
    void build();

    /**
     * 重新构建
     */
    void reBuild();

    /**
     * 清理缓存
     */
    void clear();

    /**
     * 打包项目为桌面
     */
    void packageDesktop();

}

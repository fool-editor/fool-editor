package com.ooqn.assist.core;

import java.util.Map;

public interface Plugin {
    
    /**
     * 插件可以查看的文件格式 ， 插件的注入菜单
     * @param mainContext
     */
    public void init();

    /**
     * 插件删除
     */
    public void destroy();

    /**
     * 获取这个插件拥有的相关属性，模块。
     */
    public Map<String,Object> getData();

}

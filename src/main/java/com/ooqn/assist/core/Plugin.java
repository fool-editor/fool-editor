package com.ooqn.assist.core;

public interface Plugin {
    
    /**
     * 插件可以查看的文件格式 ， 插件的注入菜单
     * @param mainContext
     */
    public void init();

    /**
     * 插件删除
     */
    public void delete();

}

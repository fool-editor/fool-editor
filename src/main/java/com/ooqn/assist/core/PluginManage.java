package com.ooqn.assist.core;

import java.util.HashMap;
import java.util.Map;

public class PluginManage {

    private Map<String,Plugin> pluginMap = new HashMap<>();
    
    public void addPlugin(Plugin plugin){
        pluginMap.put(plugin.getClass().getName(), plugin);
        plugin.init();
    }

    public void removePlugin(Class<? extends Plugin> clasz){
        pluginMap.remove(clasz.getName());
    }

    @SuppressWarnings("unchecked")
    public <T extends Plugin> T getPlugin(Class<T> clasz){
        return (T) pluginMap.get(clasz.getName());
    }

}

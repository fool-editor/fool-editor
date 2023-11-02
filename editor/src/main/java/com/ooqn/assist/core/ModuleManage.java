package com.ooqn.assist.core;

import java.util.HashMap;
import java.util.Map;

public class ModuleManage {

    private Map<String,Module> moduleMap = new HashMap<>();
    
    public void addModule(Module module){
        moduleMap.put(module.getClass().getName(), module);
        module.init();
    }

    public void removeModule(Class<? extends Module> clasz){
        moduleMap.remove(clasz.getName());
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> clasz){
        return (T) moduleMap.get(clasz.getName());
    }

}

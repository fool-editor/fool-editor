package com.ooqn.core;

import java.util.HashMap;
import java.util.Map;

public class ComponentManage {

    private Map<String,Component> componentMap = new HashMap<>();
    
    public void addAssembly(Component component){
        componentMap.put(component.getClass().getName(), component);
        component.init();
    }

    public void removeAssembly(Class<? extends Component> clasz){
        componentMap.remove(clasz.getName());
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> clasz){
        return (T) componentMap.get(clasz.getName());
    }

    public static void main(String[] args) {
        System.out.println("sss");
    }

}

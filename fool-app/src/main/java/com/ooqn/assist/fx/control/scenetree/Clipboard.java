package com.ooqn.assist.fx.control.scenetree;

import lombok.Data;
import lombok.Getter;

@Getter
public class Clipboard<T> {
    private Type type;
    private T object;

    private Runnable pasteCallback;

    public void setPasteCallback(Runnable callback) {
        this.pasteCallback = callback;
    }

    public void set(Type type, T object){
            this.type=type;
            this.object=object;
    }

    public void clear(){
        this.type=null;
        this.object=null;
    }


    public static enum Type{
        Cut,
        Cope
    }
}

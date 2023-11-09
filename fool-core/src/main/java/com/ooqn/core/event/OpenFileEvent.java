package com.ooqn.core.event;

import lombok.Getter;

import java.io.File;

/**
 * 打开一个文件
 */
@Getter
public class OpenFileEvent extends Event{
    private File file;

    public OpenFileEvent(File file,Object source) {
        super(source);
        this.file = file;
    }
}

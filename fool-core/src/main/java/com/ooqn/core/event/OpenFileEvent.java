package com.ooqn.core.event;

import lombok.Getter;

import java.io.File;
@Getter
public class OpenFileEvent {
    private File file;

    public OpenFileEvent(File file) {
        this.file = file;
    }
}

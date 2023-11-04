package com.ooqn.assist.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ooqn.assist.domain.ProjectFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditorSaveData {
    /**
     * 编辑器保存的目录
     */
    public static final File EditorSaveDataDir = new File("./data");
    public static final File EditorProjectsFile = new File(EditorSaveDataDir,"projects.json");
    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        EditorSaveDataDir.mkdirs();
    }

    /**
     * 获取 项目目录
     * @return
     */
    public static List<ProjectFile> getProjectFiles() {
        try {
            if (EditorProjectsFile.exists()) {
                return objectMapper.readValue(EditorProjectsFile, new TypeReference<List<ProjectFile>>() {
                });
            }
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveFile(File file,Object o) throws IOException {
        objectMapper.writeValue(file,o);
    }
}

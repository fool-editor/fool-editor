package com.ooqn.assist.project;

import cn.hutool.core.io.FileUtil;
import com.ooqn.assist.project.gradle.GradleProject;
import com.ooqn.core.project.Project;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class ProjectBuilder {
    /**
     * 创建一个 jme Project项目
     * 从模版里创建
     */
    public static Project creatProject(File projectDir, String projectName, String groupId) throws IOException {
        File templateFileDir = new File("./fool-jme-template");
        File project = new File(projectDir, projectName);
        if (project.exists()) {
            throw new IOException(project + " is exists");
        }
        if (templateFileDir.exists()) {
            FileUtil.copyContent(templateFileDir.toPath(), project.toPath());
        } else {
            throw new UnsupportedEncodingException("not support prerelease");
        }

        File buildFile = new File(project,"build.gradle");
        String text = FileUtil.readString(buildFile, Charset.forName("utf-8"));

        text=text.replace("group = 'com.example'","group = '"+groupId+"'");
        FileUtil.writeBytes(text.getBytes("utf-8"),buildFile);

        File mainPack=new File(project,"src/main/java/"+groupId.replaceAll("\\.","/"));
        mainPack.mkdirs();
        File mainResources=new File(project,"src/main/resources");
        mainResources.mkdirs();
        File testJava=new File(project,"src/test/java");
        testJava.mkdirs();
        File testResources=new File(project,"src/test/resources");
        testResources.mkdirs();
        return new GradleProject(project);
    }

}

package com.ooqn.assist.project.gradle;

import com.ooqn.core.project.Project;

import java.io.File;

public class GradleProject implements Project {
    private final File projectRootDir;

    public GradleProject(File projectRootDir) {
        this.projectRootDir = projectRootDir;
    }

    @Override
    public File getProjectDir() {
        return projectRootDir;
    }

    @Override
    public File getSrcJavaDir() {
        return new File(projectRootDir,"src/main/java");
    }

    @Override
    public File getSrcResourcesDir() {
        return new File(projectRootDir,"src/main/resources");
    }

    @Override
    public void build() {
        throw new UnsupportedOperationException("build");
    }

    @Override
    public void reBuild() {
        throw new UnsupportedOperationException("reBuild");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear");
    }
    @Override
    public void compile() {
        throw new UnsupportedOperationException("compile");
    }

    @Override
    public void packageDesktop() {
        throw new UnsupportedOperationException("packageDesktop");
    }
}

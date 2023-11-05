package com.ooqn.assist.view;

import cn.hutool.core.io.file.PathUtil;
import com.ooqn.assist.App2;
import com.ooqn.assist.component.JmeComponent;
import com.ooqn.assist.core.EditorSaveData;
import com.ooqn.assist.core.FoolContext;
import com.ooqn.assist.core.FoolContextInitializable;
import com.ooqn.assist.domain.ProjectFile;
import com.ooqn.assist.handel.AlertHandel;
import com.ooqn.assist.project.ProjectBuilder;
import com.ooqn.assist.project.gradle.GradleProject;
import com.ooqn.core.project.Project;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectManagerViewController implements Initializable {

    @FXML
    VBox vBox;
    @FXML
    ScrollPane projectsScrollPane;
    @FXML
    AnchorPane rootAnchorPane;
    @FXML
    AnchorPane creatProjectAnchorPane;
    @FXML
    Button creatProjectButton;

    @FXML
    TextField projectPathTextField;
    @FXML
    TextField projectNameTextField;
    @FXML
    TextField projectGroupIdTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 创建一个Font对象并设置字体大小
        Font customFont = Font.font(null, 20);

        List<ProjectFile> projectFiles = EditorSaveData.getProjectFiles();
        for (ProjectFile projectFile : projectFiles) {
            VBox pane = new VBox();
            pane.setPrefHeight(50);
            pane.prefWidthProperty().bind(rootAnchorPane.prefWidthProperty());
            File file = new File(projectFile.getFilePath());
            Label name = new Label(file.getName());
            Label path = new Label(file.getPath());
            name.setFont(customFont);
            pane.getChildren().add(name);
            pane.getChildren().add(path);
            // 添加鼠标移入事件处理程序
            pane.setOnMouseEntered(event -> {
                pane.setStyle("-fx-background-color: rgba(52,152,219,0.25);");
            });

            // 添加鼠标移出事件处理程序
            pane.setOnMouseExited(event -> {
                pane.setStyle("-fx-background-color: #ffffff;");
            });
            vBox.getChildren().add(pane);

            pane.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    openProject(file);
                }
            });
        }

    }

    private void openProject(Project project) {
        Stage stage = (Stage) projectGroupIdTextField.getScene().getWindow();
        stage.close();

        Stage newStage = new Stage();
//        newStage.setMaximized(true);
        newStage.setTitle("fool-editor");
        try {
            // 加载FXML文件
            FXMLLoader fxmlLoader = new FXMLLoader(App2.class.getClassLoader().getResource("mainView.fxml"));
            //在FXMLLoader 加载之前先 初始化FoolContext
            fxmlLoader.setControllerFactory(param -> {
                try {
                    MainViewController mainViewController = (MainViewController)param.getDeclaredConstructor().newInstance();
                    FoolContext.init(project, mainViewController);
                    return mainViewController;
                } catch (Exception e) {
                    AlertHandel.exceptionHandel(e);
                }
                return null;
            });
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setWidth(1200);
            newStage.setHeight(720);
            newStage.show();
        } catch (IOException e) {
            AlertHandel.exceptionHandel(e);
        }
    }

    private void openProject(File file) {
        GradleProject gradleProject = new GradleProject(file);
        openProject(gradleProject);
    }

    /**
     * 确定创建项目
     */
    public void onCreatProjectButtonClicked() {
        String projectPath = projectPathTextField.getText();
        String projectName = projectNameTextField.getText();
        String groupId = projectGroupIdTextField.getText();
        try {
            Project project = ProjectBuilder.creatProject(new File(projectPath), projectName, groupId);
            ProjectFile projectFile = new ProjectFile();
            Path absNormal = PathUtil.toAbsNormal(project.getProjectDir().toPath());
            projectFile.setFilePath(absNormal.toString());
            List<ProjectFile> projectFiles = EditorSaveData.getProjectFiles();
            projectFiles.add(projectFile);
            EditorSaveData.saveFile(EditorSaveData.EditorProjectsFile, projectFiles);
            openProject(project);
        } catch (Exception e) {
            AlertHandel.exceptionHandel(e);
        }
    }

    public void onBeginCreatProjectButtonClicked() {
        if (creatProjectAnchorPane.isVisible()) {
            creatProjectButton.setText("创建项目");
            creatProjectAnchorPane.setVisible(false);
            projectsScrollPane.setVisible(true);
        } else {
            creatProjectButton.setText("取消创建");
            creatProjectAnchorPane.setVisible(true);
            projectsScrollPane.setVisible(false);
        }
    }

    public void selectProjectPath(MouseEvent mouseEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择项目路径");
        // Show the folder chooser dialog
        Stage stage = (Stage) projectGroupIdTextField.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        projectPathTextField.setText(selectedDirectory.getPath());
    }
}

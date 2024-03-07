package com.smnprn.simpleaudioplayer;

import com.smnprn.simpleaudioplayer.log.LogSetter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 850);

        setScene(scene, stage);
        setStage(stage, scene);

        stage.show();
    }

    public void setScene(Scene scene, Stage stage) {
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void setStage(Stage stage, Scene scene) {
        stage.setTitle("Simple Audio Player");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        LogSetter logSetter = new LogSetter();
        logSetter.configureLog();

        launch();
    }
}
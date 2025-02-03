package com.example.momentous.momentous_finalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {   // inheritance
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));

        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setTitle("Momentous");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/image_1.png")));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}

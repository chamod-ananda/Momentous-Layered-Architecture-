package com.example.momentous.momentous_finalproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardLoadController {

    @FXML
    private AnchorPane dashAnchorPane;

    @FXML
    private ImageView image_10;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView logOutIcon;

    @FXML
    void logOutIconOnClick(MouseEvent event) {
        navigateTo();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/image_1.png")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void navigateTo() {
        try {
            Stage stage = (Stage) dashAnchorPane.getScene().getWindow();
            stage.close();

            start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong. Failed to load page.").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

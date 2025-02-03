package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.UserDto;
import com.example.momentous.momentous_finalproject.model.UserModel;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterPage1Controller {

    @FXML
    private AnchorPane backgroundPane2;

    @FXML
    private AnchorPane bodyPane2;

    @FXML
    private TextField emailTxt;

    @FXML
    private TextField fNameTxt;

    @FXML
    private ImageView image_1;

    @FXML
    private Label logInLabel;

    @FXML
    private JFXButton nextButton;

    @FXML
    private Label registerLabel;

    @FXML
    private TextField sNameTxt;

    @FXML
    private Label errlabel2;

    public static UserDto registerUser = new UserDto();

    private UserModel userModel = new UserModel();

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

    @FXML
    public void initialize() {
        fNameTxt.requestFocus();
    }

    @FXML
    void logInLabelOnMouseClicked(MouseEvent event) {
        loadUI("/view/loginPage.fxml");

    }

    @FXML
    void nextButtonOnAction(ActionEvent event) throws SQLException {
        navigateToRegisterPage2();
    }

    private void navigateToRegisterPage2() throws SQLException {
        if (areFieldsEmpty()){
            showErrorMessage("*Required fields cannot be empty.");
        } else if (!isValidEmail(emailTxt.getText())) {
            showErrorMessage("*Invalid email format");
        } else {
            registerUser.setUserId(userModel.getNextUserId());
            registerUser.setEmail(emailTxt.getText());
            registerUser.setFirstName(fNameTxt.getText());
            registerUser.setLastName(sNameTxt.getText());

            loadUI("/view/registerPage2.fxml");
        }
    }

    private boolean areFieldsEmpty() {
        return emailTxt.getText().isEmpty() || fNameTxt.getText().isEmpty() || sNameTxt.getText().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    private void loadUI(String resource) {
        backgroundPane2.getChildren().clear();
        try {
           backgroundPane2.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String message) {
        errlabel2.setText(message);
        errlabel2.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> errlabel2.setText("")
        ));
        timeline.play();
    }

}

package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.model.UserModel;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterPage2Controller {

    @FXML
    private AnchorPane backgroundPane3;

    @FXML
    private AnchorPane bodyPane3;

    @FXML
    private PasswordField conPwdField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label logInLabel2;

    @FXML
    private PasswordField pwdField;

    @FXML
    private JFXButton registerButton;

    @FXML
    private Label registerLabel;

    @FXML
    private ImageView backImage;

    @FXML
    private TextField uNameTxt;

    private UserModel userModel = new UserModel();

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{5,15}$";

    private static final String PASSWORD_PATTEN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    @FXML
    public void initialize() {
        uNameTxt.requestFocus();
    }

    @FXML
    void logInLabel2OnMouseClicked(MouseEvent event) {
        loadUI("/view/loginPage.fxml");
    }

    @FXML
    void registerButtonOnAction(ActionEvent event) throws SQLException {
        saveUser();
    }

    @FXML
    void backImageOnMouseClicked(MouseEvent event) {
        loadUI("/view/registerPage1.fxml");
    }

    private void saveUser() throws SQLException {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty");
        } else if (!isValidUserName(uNameTxt.getText())) {
            showErrorMessage("*Username must be 5 - 15 characters, containing only letters, digits, or underscores.");
        } else if (!isValidPassword(pwdField.getText())) {
            showErrorMessage("*Password must be at least 8 characters long, contain a digit, a lowercase letter, an uppercase letter and a special character.");
        } else if (!pwdField.getText().equals(conPwdField.getText())) {
            showErrorMessage("*Passwords do not match.");
        } else {
            RegisterPage1Controller.registerUser.setUserName(uNameTxt.getText());
            RegisterPage1Controller.registerUser.setPassword(conPwdField.getText());

            if (userModel.saveUser(RegisterPage1Controller.registerUser)) {
                loadUI("/view/loginPage.fxml");
            } else {
                showErrorMessage("*User not saved.");
            }
        }
    }

    private boolean areFieldsEmpty() {
        return pwdField.getText().isEmpty() || conPwdField.getText().isEmpty() || uNameTxt.getText().isEmpty() || uNameTxt.getText().isEmpty();
    }

    private boolean isValidUserName(String userName) {
        return userName.matches(USERNAME_PATTERN);
    }

    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTEN);
    }

    private void loadUI(String resource) {
        backgroundPane3.getChildren().clear();
        try {
            backgroundPane3.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> errorLabel.setText("")
        ));
        timeline.play();
    }

}

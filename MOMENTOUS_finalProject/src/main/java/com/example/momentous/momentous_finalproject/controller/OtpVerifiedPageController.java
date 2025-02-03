package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.UserDto;
import com.example.momentous.momentous_finalproject.model.UserModel;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class OtpVerifiedPageController implements Initializable {

    @FXML
    private AnchorPane backgroundPane6;

    @FXML
    private AnchorPane bodyPane6;

    @FXML
    private Label errorLabel3;

    @FXML
    private ImageView eyeImage2;

    @FXML
    private ImageView eyeImage3;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private PasswordField passwordField3;

    @FXML
    private TextField passwordVisible2Txt;

    @FXML
    private TextField passwordVisible3Txt;

    @FXML
    private JFXButton resetPwdButton;

    @FXML
    private ImageView eyImage;

    @FXML
    private Label verifyLabel;

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    private UserModel userModel = new UserModel();

    private boolean isPasswordVisible = false;

    private boolean isConfirmPasswordVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bodyPane6.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                resetPwdButton.fire();
            }
        });
    }

    @FXML
    private void initialize() {
        passwordVisible2Txt.requestFocus();
    }

    @FXML
    void resetPwdButtonOnAction(ActionEvent event) throws SQLException {
        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty");
        } else if (!isValidPassword(passwordField2.getText())) {
            showErrorMessage("*Password must be at least 8 characters long, contain a digit, a lowercase letter, an uppercase letter and a special character.");
        } else if (!passwordField2.getText().equals(passwordField3.getText())) {
            showErrorMessage("*Passwords do not match");
        } else {
            if (updateUser()) {
                loadUI("/view/loginPage.fxml");
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Oops! Something went wrong").show();
            }
        }
    }

    @FXML
    void eyeImage2OnMouseClicked(MouseEvent event) {
        if (isPasswordVisible) {
            passwordField2.setText(passwordVisible2Txt.getText());
            passwordVisible2Txt.setVisible(false);
            passwordField2.setVisible(true);
        }else {
            passwordVisible2Txt.setText(passwordField2.getText());
            passwordVisible2Txt.setVisible(true);
            passwordField2.setVisible(false);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @FXML
    void eyeImage3OnMouseClicked(MouseEvent event) {
        if (isConfirmPasswordVisible) {
            passwordField3.setText(passwordVisible3Txt.getText());
            passwordVisible3Txt.setVisible(false);
            passwordField3.setVisible(true);
        }else {
            passwordVisible3Txt.setText(passwordField3.getText());
            passwordVisible3Txt.setVisible(true);
            passwordField3.setVisible(false);
        }
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
    }

    private boolean updateUser() throws SQLException {
        final List<UserDto> allUsers = userModel.getAllUsers();
        for (UserDto userDto : allUsers) {
            if (userDto.getEmail().equals(ForgotPasswordPageController.emailAddress)) {
                userDto.setPassword(passwordField2.getText());
                userModel.updateUser(userDto);
                return true;
            }
        }
        return false;
    }

    private boolean isValidPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }

    private boolean areFieldsEmpty() {
        return passwordField2.getText().isEmpty() && passwordField3.getText().isEmpty();
    }

    @FXML
    void eyeImageOnMouseClicked(MouseEvent event) {
        loadUI("/view/loginPage.fxml");
    }

    private void showErrorMessage(String message) {
        errorLabel3.setText(message);
        errorLabel3.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-alignment: center");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> errorLabel3.setText("")
        ));
        timeline.play();
    }

    private void loadUI(String resource) {
        backgroundPane6.getChildren().clear();
        try {
            backgroundPane6.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

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
import javafx.scene.Parent;
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

public class LoginPageController implements Initializable {

    @FXML
    private AnchorPane backgroundPane;

    @FXML
    private AnchorPane bodyPane;

    @FXML
    private Label forgotPwdLabel;

    @FXML
    private Label errLabel;

    @FXML
    private ImageView eyeImage1;

    @FXML
    private JFXButton logInButton;

    @FXML
    private Label newAccountLabel;

    @FXML
    private PasswordField pwdField;

    @FXML
    private TextField pwdVisibleTxt;

    @FXML
    private TextField userNameTxt;

    private UserModel userModel = new UserModel();

    private boolean isPasswordVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bodyPane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                logInButton.fire();
            }
        });
    }

    @FXML
    public void initialize() {
        userNameTxt.requestFocus();
    }

    @FXML
    void forgotPwdOnMouseClicked(MouseEvent event) {
        loadUI("/view/forgotPasswordPage.fxml");
    }

    @FXML
    void logInButtonOnAction(ActionEvent event) throws SQLException {
        if (checkUserNameAndPassword()){
            loadUI("/view/dashboardPage.fxml");
        }else {
            showErrorMessage("Incorrect username or password. Please try again");
        }
    }

    @FXML
    void eyeImage1OnMouseClicked(MouseEvent event) {
        if (isPasswordVisible) {
            pwdField.setText(pwdVisibleTxt.getText());
            pwdVisibleTxt.setVisible(false);
            pwdField.setVisible(true);
        } else {
            pwdVisibleTxt.setText(pwdField.getText());
            pwdVisibleTxt.setVisible(true);
            pwdField.setVisible(false);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    @FXML
    void newAccountOnMouseClicked(MouseEvent event) {
        loadUI("/view/registerPage1.fxml");
    }

    private boolean checkUserNameAndPassword() throws SQLException {
        List<UserDto> allUsers = userModel.getAllUsers();

        for (UserDto userDto : allUsers) {
            if (userDto.getUserName().equals(userNameTxt.getText()) && userDto.getPassword().equals(pwdField.getText())) {
                return true;
            }
        }
        return false;
    }

    private void loadUI(String resource) {
        backgroundPane.getChildren().clear();
        try {
            backgroundPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String message) {
        errLabel.setText(message);
        errLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> errLabel.setText("")
        ));
        timeline.play();
    }
}

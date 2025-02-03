package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.model.UserModel;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ForgotPasswordPageController implements Initializable {

    @FXML
    private ImageView backImage;

    @FXML
    private AnchorPane backgroundPane4;

    @FXML
    private AnchorPane bodyPane4;

    @FXML
    private TextField email2Txt;

    @FXML
    private JFXButton submitButton;

    @FXML
    private Label errLabel;

    public static String emailAddress = "";

    private UserModel userModel = new UserModel();
    SendMailController sendMailController = new SendMailController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bodyPane4.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                submitButton.fire();
            }
        });
    }

    @FXML
    public void initialize() {
        email2Txt.requestFocus();
    }


    @FXML
    void backImageOnMouseClicked(MouseEvent event) {
        loadUI("/view/loginPage.fxml");
    }

    public static String otpGenerated = "0000";

    @FXML
    void submitButtonOnAction(ActionEvent event) throws SQLException {

        if (areFieldsEmpty()) {
            showErrorMessage("*Required fields cannot be empty");
        } else if (!isValidEmailAddress()) {
            showErrorMessage("*Invalid email address");
        } else {
            emailAddress = email2Txt.getText();

            String recipientEmail = emailAddress;
            //String otp = generateOTP();
            otpGenerated = generateOTP();

            sendMailController.sendEmail(recipientEmail,"Your OTP Code", otpGenerated);
            loadUI("/view/otpPage.fxml");
        }
    }

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    private boolean isValidEmailAddress() throws SQLException {
        return userModel.isEmailExists(email2Txt.getText());
    }

    private boolean areFieldsEmpty() {
        return email2Txt.getText().isEmpty();
    }

    private void loadUI(String resource) {
        backgroundPane4.getChildren().clear();
        try {
            backgroundPane4.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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

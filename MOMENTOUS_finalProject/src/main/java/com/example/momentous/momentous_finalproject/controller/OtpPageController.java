package com.example.momentous.momentous_finalproject.controller;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class OtpPageController implements Initializable {

    @FXML
    private AnchorPane backgroundPane5;

    @FXML
    private AnchorPane bodyPane5;

    @FXML
    private Label errorLabel2;

    @FXML
    private TextField otp1Txt;

    @FXML
    private TextField otp2Txt;

    @FXML
    private TextField otp3Txt;

    @FXML
    private TextField otp4Txt;

    @FXML
    private Label resendLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private JFXButton verifyButton;

    @FXML
    private ImageView backImage;

    private int remainingSec = 120;

    private static final String CORRECT_OTP = ForgotPasswordPageController.otpGenerated;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bodyPane5.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER){
                verifyButton.fire();
            }
        });
    }

    @FXML
    public void initialize() {
        otp1Txt.requestFocus();
        resendLabel.setDisable(true);
        verifyButton.setDisable(true);
        startTimer();

        otp1Txt.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);
        otp2Txt.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);
        otp3Txt.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);
        otp4Txt.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);

        otp1Txt.textProperty().addListener((observable, oldValue, newValue) -> moveToNextField());
        otp2Txt.textProperty().addListener((observable, oldValue, newValue) -> moveToNextField());
        otp3Txt.textProperty().addListener((observable, oldValue, newValue) -> moveToNextField());
        otp4Txt.textProperty().addListener((observable, oldValue, newValue) -> moveToNextField());
    }

    private void handleKeyTyped(KeyEvent event) {
        if (!event.getCharacter().matches("\\d") || ((TextField) event.getSource()).getText().length() >= 1) {
            event.consume();
        }
    }

    @FXML
    void resendLabelOnMouseClicked(MouseEvent event) {
        resendLabel.setDisable(true);
        remainingSec = 120;
        startTimer();
    }

    @FXML
    void verifyButtonOnAction(ActionEvent event) {
        submitOtp();
    }

    @FXML
    void backImageOnMouseClicked(MouseEvent event) {
        loadUI("/view/forgotPasswordPage.fxml");
    }

    @FXML
    void moveToNextField() {
        if (!otp1Txt.getText().isEmpty()) otp2Txt.requestFocus();
        if (!otp2Txt.getText().isEmpty()) otp3Txt.requestFocus();
        if (!otp3Txt.getText().isEmpty()) otp4Txt.requestFocus();
        checkFieldsFilled();
    }

    @FXML
    void submitOtpOnEnter(KeyEvent event) {
        if (!otp4Txt.getText().isEmpty()) {
            submitOtp();
        }
    }

    @FXML
    void submitOtp() {
        if ((otp1Txt.getText() + otp2Txt.getText() + otp3Txt.getText() + otp4Txt.getText()).equals(CORRECT_OTP)) {
            loadUI("/view/otpVerifiedPage.fxml");
        } else {
            showErrorMessage("Incorrect OTP. Please try again");
        }
    }

    private void startTimer() {
        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingSec--;
            timerLabel.setText(String.format("Resend in %02d:%02d", remainingSec / 60, remainingSec % 60));

            if (remainingSec <= 0) {
                resendLabel.setDisable(false);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void checkFieldsFilled() {
        if (!otp1Txt.getText().isEmpty() &&
            !otp2Txt.getText().isEmpty() &&
            !otp3Txt.getText().isEmpty() &&
            !otp4Txt.getText().isEmpty()) {
            verifyButton.setDisable(false);
        } else {
            verifyButton.setDisable(true);
        }
    }

    private void showErrorMessage(String message) {
        errorLabel2.setText(message);
        errorLabel2.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-alignment: center");

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(2),
                ae -> errorLabel2.setText("")
        ));
        timeline.play();
    }

    private void loadUI(String resource) {
        backgroundPane5.getChildren().clear();
        try {
            backgroundPane5.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

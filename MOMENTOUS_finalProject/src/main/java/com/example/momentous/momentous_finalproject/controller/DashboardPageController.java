package com.example.momentous.momentous_finalproject.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    @FXML
    private AnchorPane backgroundPane7;

    @FXML
    private AnchorPane bodyPane7;

    @FXML
    private JFXButton bookEventButton;

    @FXML
    private JFXButton bookServiceButton;

    @FXML
    private JFXButton bookingButton;

    @FXML
    private JFXButton customerButton;

    @FXML
    private JFXButton employeeButton;

    @FXML
    private JFXButton eventButton;

    @FXML
    private AnchorPane interAnchorPane;

    @FXML
    private JFXButton itemButton;

    @FXML
    private Label nameLabel;

    @FXML
    private JFXButton paymentButton;

    @FXML
    private JFXButton serviceButton;

    @FXML
    private JFXButton supplierButton;

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView userIcon;

    @FXML
    private VBox vBox1;

    @FXML
    private VBox vBox2;

    @FXML
    private Label timeLabel;

    @FXML
    private Label dateLabelCurrent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/dashboardLoadPage.fxml");
        initializeButtonEffect(itemButton);
        initializeButtonEffect(supplierButton);
        initializeButtonEffect(employeeButton);
        initializeButtonEffect(eventButton);
        initializeButtonEffect(bookingButton);
        initializeButtonEffect(customerButton);
        initializeButtonEffect(serviceButton);
        initializeButtonEffect(paymentButton);

        onButtonClicked(itemButton);
        setButtonSizes();
        clock();
    }

    @FXML
    private void initializeButtonEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(100, 100, 100, 0.4));

        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));

        button.setOnMousePressed(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
            scaleTransition.setToX(0.95);
            scaleTransition.setToY(0.95);
            scaleTransition.play();
        });

        button.setOnMouseReleased(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
    }

    private void setButtonSizes() {
        double buttonWidth = 117;
        double buttonHeight = 32;

        itemButton.setPrefSize(buttonWidth, buttonHeight);
        supplierButton.setPrefSize(buttonWidth, buttonHeight);
        employeeButton.setPrefSize(buttonWidth, buttonHeight);
        eventButton.setPrefSize(buttonWidth, buttonHeight);
        bookingButton.setPrefSize(buttonWidth, buttonHeight);
        customerButton.setPrefSize(buttonWidth, buttonHeight);
        serviceButton.setPrefSize(buttonWidth, buttonHeight);
        paymentButton.setPrefSize(buttonWidth, buttonHeight);
    }

    private void setActiveButtonStyle(Button activeButton) {
        activeButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #EED3B1,  #C3B091);" +
                        "-fx-text-fill: #1e272e;" +
                        "-fx-border-color:  #FFDBB5;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(101, 100, 99, 0.4), 12, 0, 0, 3);"
        );
    }

    private void resetButtonStyles() {
        String resetStyle =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: linear-gradient(to right, #FFDBB5,  #7b7a78);" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-text-fill: #1e272e;";

        itemButton.setStyle(resetStyle);
        supplierButton.setStyle(resetStyle);
        employeeButton.setStyle(resetStyle);
        eventButton.setStyle(resetStyle);
        bookingButton.setStyle(resetStyle);
        customerButton.setStyle(resetStyle);
        serviceButton.setStyle(resetStyle);
        paymentButton.setStyle(resetStyle);
    }

    private void onButtonClicked(Button selectedButton) {
        resetButtonStyles();
        setActiveButtonStyle(selectedButton);
    }

    private void onButtonClickedImage(ImageView selectedButton) {
        resetButtonStyles();
    }

    @FXML
    void bookingButtonOnAction(ActionEvent event) {
        navigateTo("/view/bookingView.fxml");
        onButtonClicked(bookingButton);
    }

    @FXML
    void customerButtonOnAction(ActionEvent event) {
        navigateTo("/view/customerView.fxml");
        onButtonClicked(customerButton);
    }

    @FXML
    void employeeButtonOnAction(ActionEvent event) {
        navigateTo("/view/employeeView.fxml");
        onButtonClicked(employeeButton);
    }

    @FXML
    void eventButtonOnAction(ActionEvent event) {
        navigateTo("/view/eventView.fxml");
        onButtonClicked(eventButton);
    }

    @FXML
    void itemButtonOnAction(ActionEvent event) {
        navigateTo("/view/itemView.fxml");
        onButtonClicked(itemButton);
    }

    @FXML
    void paymentButtonOnAction(ActionEvent event) {
        navigateTo("/view/paymentView.fxml");
        onButtonClicked(paymentButton);
    }

    @FXML
    void serviceButtonOnAction(ActionEvent event) {
        navigateTo("/view/serviceView.fxml");
        onButtonClicked(serviceButton);
    }

    @FXML
    void supplierButtonOnAction(ActionEvent event) {
        navigateTo("/view/SupplierView.fxml");
        onButtonClicked(supplierButton);
    }

    @FXML
    void homeIconOnMouseClicked(MouseEvent event) {
        navigateTo("/view/dashboardLoadPage.fxml");
        onButtonClickedImage(homeIcon);
    }

    @FXML
    void userIconOnMouseClicked(MouseEvent event) {
        navigateTo("/view/userView.fxml");
        onButtonClickedImage(userIcon);
    }

    public void navigateTo(String fxmlPath) {
        try {
            interAnchorPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(interAnchorPane.widthProperty());
            load.prefHeightProperty().bind(interAnchorPane.heightProperty());
            interAnchorPane.getChildren().add(load);
        }
        catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page").show();
        }
    }

    private void clock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            timeLabel.setText(LocalTime.now().format(timeFormatter));

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            dateLabelCurrent.setText(LocalDate.now().format(dateFormatter));

        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
}

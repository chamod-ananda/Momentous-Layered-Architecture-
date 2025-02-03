package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.PaymentDto;
import com.example.momentous.momentous_finalproject.dto.tm.PayamentTM;
import com.example.momentous.momentous_finalproject.model.BookingModel;
import com.example.momentous.momentous_finalproject.model.PaymentModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentViewController implements Initializable {

    @FXML
    private TableColumn<PayamentTM, Double> amountColumn;

    @FXML
    private TextField amountTxtField;

    @FXML
    private AnchorPane backPane10;

    @FXML
    private TableColumn<PayamentTM, String> bookingIdColumn;

    @FXML
    private JFXComboBox<String> bookingIdComBox;

    @FXML
    private Label dateLabel;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<PayamentTM, Date> payedDateColumn;

    @FXML
    private TableColumn<PayamentTM, String> paymentIdColumn;

    @FXML
    private Label paymentIdLabel;

    @FXML
    private TableView<PayamentTM> paymentViewTable;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton updateButton;

    private static final String DATE_PATTERN = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private static final String AMOUNT_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final PaymentModel paymentModel = new PaymentModel();
    private final BookingModel bookingModel = new BookingModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try{
            refreshPage();
            loadBookingIds();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCellValues() {
        paymentIdColumn.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        payedDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
    }

    private void refreshPage() throws Exception{
        refreshTable();

        try{
            String paymentId = paymentModel.getNextPaymentId();
            paymentIdLabel.setText(paymentId);
        }catch (Exception e){
            e.printStackTrace();
        }

        dateLabel.setText(LocalDate.now().toString());
        amountTxtField.setText("");

        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDto> paymentDtos = paymentModel.getAllItems();
        ObservableList<PayamentTM> paymentTMS = FXCollections.observableArrayList();

        for(PaymentDto paymentDto : paymentDtos){
            PayamentTM paymentTM = new PayamentTM(
                    paymentDto.getPaymentId(),
                    paymentDto.getPaymentDate(),
                    paymentDto.getPaymentAmount(),
                    paymentDto.getBookingId()
            );
            paymentTMS.add(paymentTM);

        }
        paymentViewTable.setItems(paymentTMS);
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws Exception{
        String paymentId = paymentIdLabel.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentModel.deletePayment(paymentId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete payment").show();
            }
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) throws Exception{
        bookingIdComBox.setValue(null);
        bookingIdComBox.setPromptText("Select booking Id");

        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        PaymentDto paymentDto = getFieldValues();

        if(paymentDto != null) {
            try {
                boolean isSaved = paymentModel.savePayment(paymentDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save payment").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private PaymentDto getFieldValues() {
        String paymentId = paymentIdLabel.getText();
        String bookingId = bookingIdComBox.getSelectionModel().getSelectedItem();
        Date date = Date.valueOf(dateLabel.getText());
        Double amount = Double.valueOf(amountTxtField.getText());

        boolean isValidFields = validPaymentFields();

        if(isValidFields) {
            return new PaymentDto(paymentId,date,amount,bookingId);
        }
        return null;
    }

    private boolean validPaymentFields() {
        boolean isvalidDate = dateLabel.getText().matches(DATE_PATTERN);

        if(!isvalidDate) {
            dateLabel.setStyle(dateLabel.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidAmount = amountTxtField.getText().matches(AMOUNT_PATTERN);

        if(!isValidAmount) {
            amountTxtField.setStyle(amountTxtField.getStyle() + ";-fx-border-color: red;");
        }

        return isvalidDate && isValidAmount;
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        PaymentDto paymentDto = getFieldValues();

        if(paymentDto != null) {
            try {
                boolean isSaved = paymentModel.updatePayment(paymentDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment update successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update payment").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void loadBookingIds() throws ClassNotFoundException, SQLException {
        ArrayList<String> bookingIds = bookingModel.getAllBookingIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookingIds);
        bookingIdComBox.setItems(observableList);
    }

    @FXML
    void paymentViewTableOnClick(MouseEvent event) {
        PayamentTM selectedItem = paymentViewTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            paymentIdLabel.setText(selectedItem.getPaymentId());
            bookingIdComBox.setValue(selectedItem.getBookingId());
            dateLabel.setText(String.valueOf(selectedItem.getPaymentDate()));
            amountTxtField.setText(selectedItem.getPaymentAmount().toString());

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }
}

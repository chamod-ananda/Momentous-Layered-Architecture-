package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.SupplierDto;
import com.example.momentous.momentous_finalproject.dto.tm.SupplierTM;
import com.example.momentous.momentous_finalproject.model.SupplierModel;
import com.jfoenix.controls.JFXButton;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierViewController implements Initializable {

    @FXML
    private AnchorPane backPane2;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<SupplierTM, String> emailColumn;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTxtField;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TableColumn<SupplierTM, String> supplierIdColumn;

    @FXML
    private Label supplierIdLabel;

    @FXML
    private Label supplierIdLabelInfo;

    @FXML
    private TableColumn<SupplierTM, String> supplierNameColumn;

    @FXML
    private Label supplierNameLabel;

    @FXML
    private TextField supplierNameTxtField;

    @FXML
    private TableView<SupplierTM> supplierViewTable;

    @FXML
    private JFXButton updateButton;

    SupplierModel supplierModel = new SupplierModel();

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
    }

    private void setCellValues() {
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshPage() {
        try{
            refreshTable();
            String supplierId = supplierModel.getNextSupplierId();
            supplierIdLabelInfo.setText(supplierId);

        }catch (Exception e){
            e.printStackTrace();
        }

        supplierNameTxtField.setText("");
        emailTxtField.setText("");

        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);

    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDto> supplierDtos = supplierModel.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDto supplierDto : supplierDtos) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDto.getSupplierId(),
                    supplierDto.getSupplierName(),
                    supplierDto.getEmail()
            );
            supplierTMS.add(supplierTM);
        }
        supplierViewTable.setItems(supplierTMS);

    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String supplierId = supplierIdLabelInfo.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierModel.deleteSupplier(supplierId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "supplier deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier").show();
            }
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        SupplierDto supplierDto = getFieldValues();

        if(supplierDto != null) {
            try {
                boolean isSaved = supplierModel.saveSupplier(supplierDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save supplier").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void supplierViewTableOnClick(MouseEvent event) {
        SupplierTM selectedItem = supplierViewTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            supplierIdLabelInfo.setText(selectedItem.getSupplierId());
            supplierNameTxtField.setText(selectedItem.getSupplierName());
            emailTxtField.setText(selectedItem.getEmail());

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        SupplierDto supplierDto = getFieldValues();

        if(supplierDto != null) {
            try {
                boolean isUpdate = supplierModel.updateSupplier(supplierDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update supplier").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private SupplierDto getFieldValues() {
        String supplierId = supplierIdLabelInfo.getText();
        String supplierName = supplierNameTxtField.getText();
        String email = emailTxtField.getText();

        boolean isValidFields = validSupplierFields();

        if(isValidFields) {
            return new SupplierDto(supplierId,supplierName,email);
        }
        return null;

    }

    private boolean validSupplierFields() {
        boolean isvalidName = supplierNameTxtField.getText().matches(NAME_PATTERN);

        if(!isvalidName) {
            supplierNameTxtField.setStyle(supplierNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidEmail = emailTxtField.getText().matches(EMAIL_PATTERN);

        if(!isValidEmail) {
            emailTxtField.setStyle(emailTxtField.getStyle() + ";-fx-border-color: red;");
        }

        return isvalidName && isValidEmail;
    }
}

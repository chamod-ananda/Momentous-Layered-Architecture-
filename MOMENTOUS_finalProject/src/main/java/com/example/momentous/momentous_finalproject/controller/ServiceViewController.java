package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.ServiceDto;
import com.example.momentous.momentous_finalproject.dto.tm.ServiceTM;
import com.example.momentous.momentous_finalproject.model.ServiceModel;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ServiceViewController implements Initializable {

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<ServiceTM, Double> priceColumn;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField priceTxtField;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TableColumn<ServiceTM, String> serviceIdColumn;

    @FXML
    private Label serviceIdInfo;

    @FXML
    private Label serviceIdLabel;

    @FXML
    private TableColumn<ServiceTM, String> serviceTypeColumn;

    @FXML
    private JFXComboBox<String> serviceTypeComBox;

    @FXML
    private Label serviceTypeLabel;

    @FXML
    private TableView<ServiceTM> serviceViewTable;

    @FXML
    private JFXButton updateButton;

    private final ServiceModel serviceModel = new ServiceModel();

    private static final String PRICE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        serviceTypeComBox.getItems().addAll("Cleaning", "Catering", "Security", "Lighting", "Photography", "Decoration");

        try{
            refreshPage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setCellValues() {
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
    }

    private void refreshPage() throws Exception{
        refreshTable();

        try{
            String serviceId = serviceModel.getNextServiceId();
            serviceIdInfo.setText(serviceId);
        }catch(Exception e){
            e.printStackTrace();
        }
        priceTxtField.setText("");

        deleteButton.setDisable(true);
        saveButton.setDisable(false);
        updateButton.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {

        ArrayList<ServiceDto> serviceDtos = serviceModel.getAllServices();
        ObservableList<ServiceTM> serviceTMS= FXCollections.observableArrayList();

        for (ServiceDto serviceDto : serviceDtos) {
            ServiceTM serviceTM = new ServiceTM(
                    serviceDto.getServiceId(),
                    serviceDto.getServiceType(),
                    serviceDto.getPrice()
            );
            serviceTMS.add(serviceTM);
        }
        serviceViewTable.setItems(serviceTMS);

    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws Exception{
        String serviceId = serviceIdInfo.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = serviceModel.deleteService(serviceId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "service deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete service").show();
            }
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) throws Exception{
        serviceTypeComBox.setValue(null);
        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ServiceDto serviceDto = getFieldValues();

        if(serviceDto != null) {
            try {
                boolean isSaved = serviceModel.saveService(serviceDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Service saved successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save service").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ServiceDto getFieldValues(){
        String serviceId = serviceIdInfo.getText();
        String serviceType = serviceTypeComBox.getSelectionModel().getSelectedItem();
        Double price = Double.valueOf(priceTxtField.getText());

        boolean isValidFields = validServiceFields();

        if(isValidFields) {
            return new ServiceDto(serviceId,price,serviceType);
        }
        return null;
    }

    private boolean validServiceFields() {
        boolean isValidPrice = priceTxtField.getText().matches(PRICE_PATTERN);

        if(!isValidPrice) {
            priceTxtField.setStyle(priceTxtField.getStyle() + ";-fx-border-color: red;");
        }

        return isValidPrice;
    }

    @FXML
    void serviceViewTableOnClick(MouseEvent event) {
        ServiceTM selectedItem = serviceViewTable.getSelectionModel().getSelectedItem();

        if(selectedItem != null) {
            serviceIdInfo.setText(selectedItem.getServiceId());
            serviceTypeComBox.setValue(selectedItem.getServiceType());
            priceTxtField.setText(String.valueOf(selectedItem.getPrice()));

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        ServiceDto serviceDto = getFieldValues();

        if(serviceDto != null) {
            try {
                boolean isUpdate = serviceModel.updateService(serviceDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Service updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update service").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

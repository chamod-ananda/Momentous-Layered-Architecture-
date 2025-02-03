package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.bo.BOFactory;
import com.example.momentous.momentous_finalproject.bo.impl.CustomerBOImpl;
import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.CustomerDto;
import com.example.momentous.momentous_finalproject.view.tdm.CustomerTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CustomerViewController implements Initializable {

    @FXML
    private AnchorPane backPane7;

    @FXML
    private TableColumn<CustomerTM, String> customerIdColumn;

    @FXML
    private Label customerIdLabel;

    @FXML
    private Label customerIdLabelInfo;

    @FXML
    private JFXButton customerReportButton;

    @FXML
    private TableView<CustomerTM> customerViewTable;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dateLabelInfo;

    @FXML
    private TableColumn<CustomerTM, Date> dateColumn;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<CustomerTM, String> emailColumn;

    @FXML
    private JFXButton emailCustButton;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailLabelTxtField;

    @FXML
    private TableColumn<CustomerTM, String> fNameColumn;

    @FXML
    private Label fNameLabel;

    @FXML
    private TextField fNameTxtField;

    @FXML
    private JFXButton genReportButton;

    @FXML
    private TableColumn<CustomerTM, String> lNameColumn;

    @FXML
    private Label lNameLabel;

    @FXML
    private TextField lNameTxtField;

    @FXML
    private TableColumn<CustomerTM, String> nicColumn;

    @FXML
    private Label nicLabel;

    @FXML
    private TextField nicTxtField;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TableColumn<CustomerTM, String> titleColumn;

    @FXML
    private JFXComboBox<String> titleComBox;

    @FXML
    private Label titleLabel;

    @FXML
    private JFXButton updateButton;

    private final CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String NIC_PATTERN = "^[0-9]{9}[vVxX]||[0-9]{12}$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        titleComBox.getItems().addAll("Mr", "Ms", "Mrs", "Dr", "Prof","Miss");
    }

    private void setCellValues() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("customerTitle"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("nic"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("regDate"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() {
        try {
            refreshTable();
            String nextCustomerID = customerBO.getNextCustomerId();
            customerIdLabelInfo.setText(nextCustomerID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String formattedDate = today.format(formatter);

        fNameTxtField.setText("");
        lNameTxtField.setText("");
        nicTxtField.setText("");
        dateLabelInfo.setText(formattedDate);

        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
        customerReportButton.setDisable(false);
        //genReportButton.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> customerDtos = customerBO.getAllCustomer();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDto customerDto : customerDtos) {
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getCustomerId(),
                    customerDto.getCustomerTitle(),
                    customerDto.getFirstName(),
                    customerDto.getLastName(),
                    customerDto.getNic(),
                    customerDto.getEmail(),
                    customerDto.getRegDate()
            );
            customerTMS.add(customerTM);
        }
        customerViewTable.setItems(customerTMS);
    }

    @FXML
    void customerReportButtonOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Customer_Report.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data empty..!");
            e.printStackTrace();
        }

    }

    @FXML
    void customerViewTableOnClick(MouseEvent event) {
        CustomerTM selectedItem = customerViewTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            customerIdLabelInfo.setText(selectedItem.getCustomerId());
            titleComBox.setValue(selectedItem.getCustomerTitle());
            fNameTxtField.setText(selectedItem.getFirstName());
            lNameTxtField.setText(selectedItem.getLastName());
            nicTxtField.setText(selectedItem.getNic());
            emailLabelTxtField.setText(selectedItem.getEmail());
            dateLabelInfo.setText(selectedItem.getRegDate().toString());

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
            //customerReportButton.setDisable(false);
        }
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String customerId = customerIdLabelInfo.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = customerBO.deleteCustomer(customerId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer can't deleted").show();
            }
        }
    }

    @FXML
    void emailCustButtonOnAction(ActionEvent event) {
        CustomerTM selectedItem = customerViewTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "Please select a customer").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendMail.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send Mail");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = updateButton.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    void genReportButtonOnAction(ActionEvent event) {
//
//    }

    @FXML
    void resetButtonOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        CustomerDto customerDto = getFieldValues();

        if (customerDto != null) {
            try {
                boolean isSaved = customerBO.saveCustomer(customerDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer can't saved").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private CustomerDto getFieldValues() {
        String customerId = customerIdLabelInfo.getText();
        String customerTitle = titleComBox.getSelectionModel().getSelectedItem();
        String firstName = fNameTxtField.getText();
        String lastName = lNameTxtField.getText();
        String nic = nicTxtField.getText();
        String email = emailLabelTxtField.getText();
        LocalDate curDate = LocalDate.parse(dateLabelInfo.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Date regDate = Date.valueOf(curDate);

        boolean isValidFields = validCustomerFields(firstName, lastName, nic, email);

        if (isValidFields) {
            return new CustomerDto(customerId, customerTitle, firstName, lastName, nic, email, regDate);
        }
        return null;
    }

    private boolean validCustomerFields(String firstName, String lastName, String nic, String email) {
        boolean isValidName = firstName.matches(NAME_PATTERN) && lastName.matches(NAME_PATTERN);
        boolean isValidNic = nic.matches(NIC_PATTERN);
        boolean isValidEmail = email.matches(EMAIL_PATTERN);

        if (!isValidName) {
            fNameTxtField.setStyle(fNameTxtField.getStyle() + ";-fx-border-color: red;");
            lNameTxtField.setStyle(lNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidNic) {
            nicTxtField.setStyle(nicTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            emailLabelTxtField.setStyle(emailLabelTxtField.getStyle() + ";-fx-border-color: red;");
        }
        return isValidName && isValidNic && isValidEmail;
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        CustomerDto customerDto = getFieldValues();

        if (customerDto != null) {
            try {
                boolean isUpdate = customerBO.updateCustomer(customerDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer can't updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

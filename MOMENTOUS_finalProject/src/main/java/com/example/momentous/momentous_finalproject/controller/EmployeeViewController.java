package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.EmployeeDto;
import com.example.momentous.momentous_finalproject.dto.tm.EmployeeTM;
import com.example.momentous.momentous_finalproject.model.BookingModel;
import com.example.momentous.momentous_finalproject.model.EmployeeModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    private TableColumn<EmployeeTM, String> bookingIdColumn;

    @FXML
    private JFXComboBox<String> bookingIdComBox;

    @FXML
    private Label bookingIdLabel;

    @FXML
    private TableColumn<EmployeeTM, Date> dateColumn;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dateLabelInfo;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<EmployeeTM, String> emailColumn;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTxtField;

    @FXML
    private TableColumn<EmployeeTM, String> employeeIdColumn;

    @FXML
    private Label employeeIdLabel;

    @FXML
    private Label employeeIdInfo;

    @FXML
    private TableView<EmployeeTM> employeeViewTable;

    @FXML
    private TableColumn<EmployeeTM, String> fNameColumn;

    @FXML
    private Label fNameLabel;

    @FXML
    private TextField fNameTxtField;

    @FXML
    private TableColumn<?, ?> jobRoleColumn;

    @FXML
    private JFXComboBox<String> jobRoleComBox;

    @FXML
    private Label jobRoleLabel;

    @FXML
    private TableColumn<EmployeeTM, String> lNameColumn;

    @FXML
    private Label lNameLaabel;

    @FXML
    private TextField lNameTxtField;

    @FXML
    private JFXButton resetButton;

    @FXML
    private TableColumn<EmployeeTM, Double> salaryColumn;

    @FXML
    private Label salaryLabel;

    @FXML
    private TextField salaryTxtField;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton updateButton;

    private final EmployeeModel employeeModel = new EmployeeModel();
    private final BookingModel bookingModel = new BookingModel();

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String SALARY_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        jobRoleComBox.getItems().addAll("Event Coordinator", "Event Manager", "Event Planner", "Venue Manager", "Catering Manager", "Logistics Coordinator", "Technical Director", "Audio Visual Specialist",  "Marketing Coordinator", "Registration Coordinator","Client Relations Manager", "Event Designer");
    }

    private void setCellValues() {
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        fNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        jobRoleColumn.setCellValueFactory(new PropertyValueFactory<>("jobRole"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));

        try {
            loadBookingIds();
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() {
        try{
            refreshTable();
            String employeeId = employeeModel.getNextEmployeeId();
            employeeIdInfo.setText(employeeId);
        }catch (Exception e){
            e.printStackTrace();
        }

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        fNameTxtField.setText("");
        lNameTxtField.setText("");
        emailTxtField.setText("");
        salaryTxtField.setText("");
        dateLabelInfo.setText(formattedDate);

        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDto> employeeDtos = employeeModel.getAllEmployees();
        ObservableList<EmployeeTM> employeeTms = FXCollections.observableArrayList();

        for(EmployeeDto employeeDto : employeeDtos){
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDto.getEmployeeId(),
                    employeeDto.getFirstName(),
                    employeeDto.getLastName(),
                    employeeDto.getJobRole(),
                    employeeDto.getJoinedDate(),
                    employeeDto.getSalary(),
                    employeeDto.getEmail(),
                    employeeDto.getBookingId()
            );
            employeeTms.add(employeeTM);

        }
        employeeViewTable.setItems(employeeTms);

    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String employeeId = employeeIdInfo.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeModel.deleteEmployee(employeeId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Employee").show();
            }
        }
    }

    @FXML
    void employeeViewTableOnClick(MouseEvent event) {
        EmployeeTM selectedItem = employeeViewTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            employeeIdInfo.setText(selectedItem.getEmployeeId());
            fNameTxtField.setText(selectedItem.getFirstName());
            lNameTxtField.setText(selectedItem.getLastName());
            jobRoleComBox.setValue(selectedItem.getJobRole());
            dateLabelInfo.setText(selectedItem.getJoinedDate().toString());
            salaryTxtField.setText(String.valueOf(selectedItem.getSalary()));
            emailTxtField.setText(selectedItem.getEmail());
            bookingIdComBox.setValue(selectedItem.getBookingId());

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) {
        jobRoleComBox.setValue(null);
        jobRoleComBox.setPromptText("Select job position");

        bookingIdComBox.setValue(null);
        bookingIdComBox.setPromptText("Select booking id");

        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        EmployeeDto employeeDto = getFieldValues();

        if(employeeDto != null) {
            try {
                boolean isSaved = employeeModel.saveEmployee(employeeDto);

                if (isSaved) {
                    showAlert("Employee saved successfully", Alert.AlertType.INFORMATION);
                    refreshPage();
                } else {
                    //new Alert(Alert.AlertType.ERROR, "Failed to save employee.").show();
                    showAlert("Failed to save employee", Alert.AlertType.ERROR);

                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private EmployeeDto getFieldValues() {

        String employeeId = employeeIdInfo.getText();
        String firstName = fNameTxtField.getText();
        String lastName = lNameTxtField.getText();
        String role = jobRoleComBox.getValue();
        Date joinDate = Date.valueOf(dateLabelInfo.getText());
        Double salary = Double.valueOf(salaryTxtField.getText());
        String email = emailTxtField.getText();
        String bookingId = bookingIdComBox.getValue();


        boolean isValidFields = validEmployeeFields(firstName, lastName, salary, email);

        if(isValidFields) {
            return new EmployeeDto(employeeId,firstName,lastName,role,joinDate,salary,email,bookingId);
        }
        return null;
    }

    private boolean validEmployeeFields(String firstName, String lastName, Double salary, String email) {
        boolean isValidName = firstName.matches(NAME_PATTERN) && lastName.matches(NAME_PATTERN);
        boolean isValidSalary = String.valueOf(salary).matches(SALARY_PATTERN);
        boolean isValidEmail = email.matches(EMAIL_PATTERN);

        if (!isValidName) {
            fNameTxtField.setStyle(fNameTxtField.getStyle() + ";-fx-border-color: red;");
            lNameTxtField.setStyle(lNameTxtField.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidSalary) {
            salaryTxtField.setStyle(salaryTxtField.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            emailTxtField.setStyle(emailTxtField.getStyle() + ";-fx-border-color: red;");
        }

        return isValidName && isValidSalary && isValidEmail;
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        EmployeeDto employeeDto = getFieldValues();

        if(employeeDto != null) {
            try{
                boolean isUpdate = employeeModel.updateEmployee(employeeDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to update employee").show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBookingIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> bookingIds = bookingModel.getAllBookingIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookingIds);
        bookingIdComBox.setItems(observableList);

    }

    private void showAlert(String message , Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

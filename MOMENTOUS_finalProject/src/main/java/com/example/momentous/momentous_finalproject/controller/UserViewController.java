package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.UserDto;
import com.example.momentous.momentous_finalproject.dto.tm.UserTM;
import com.example.momentous.momentous_finalproject.model.UserModel;
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

public class UserViewController implements Initializable {

    @FXML
    private AnchorPane backPane11;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<UserTM, String> emailColumn;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailLabelTxtField;

    @FXML
    private TableColumn<UserTM, String> firstNameColumn;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameTxtField;

    @FXML
    private TableColumn<UserTM, String> lastNameColumn;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField lastNameTxtField;

    @FXML
    private TableColumn<UserTM, String> passwordColumn;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordLabelTxtField;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton updateButton;

    @FXML
    private TableColumn<UserTM, String> userIdColumn;

    @FXML
    private Label userIdLabel;

    @FXML
    private TextField userIdTxtField;

    @FXML
    private TableColumn<UserTM, String> userNameColumn;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userNameTxtField;

    @FXML
    private TableView<UserTM> userViewTable;

    UserModel userModel = new UserModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextUserID = userModel.getNextUserId();
        userIdTxtField.setText(nextUserID);

        firstNameTxtField.setText("");
        lastNameTxtField.setText("");
        userNameTxtField.setText("");
        emailLabelTxtField.setText("");
        passwordLabelTxtField.setText("");

        saveButton.setDisable(false);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
    }

    public void refreshTable() throws SQLException {
        ArrayList<UserDto> userDtos = userModel.getAllUsers();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDto userDto : userDtos) {
            UserTM userTM = new UserTM(
                    userDto.getUserId(),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    userDto.getUserName(),
                    userDto.getEmail(),
                    userDto.getPassword()
            );
            userTMS.add(userTM);
        }
        userViewTable.setItems(userTMS);
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws SQLException{
        String userId = userIdTxtField.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this user?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = userModel.deleteUser(userId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User Deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "User could not be deleted").show();
            }
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) throws SQLException{
        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) throws SQLException{
        String userId = userIdTxtField.getText();
        String firstName = firstNameTxtField.getText();
        String lastName = lastNameTxtField.getText();
        String userName = userNameTxtField.getText();
        String emai = emailLabelTxtField.getText();
        String password = passwordLabelTxtField.getText();

        String firstNamePattern = "^[A-Za-z ]+$";
        String lastNamePattern = "^[A-Za-z ]+$";
        String userNamePattern = "^[A-Za-z.,@_ ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

        boolean isValidFirstName = firstName.matches(firstNamePattern);
        boolean isValidLastName = lastName.matches(lastNamePattern);
        boolean isValidUserName = userName.matches(userNamePattern);
        boolean isValidEmail = emai.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        firstNameTxtField.setStyle(firstNameTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        lastNameTxtField.setStyle(lastNameTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        userNameTxtField.setStyle(userNameTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        emailLabelTxtField.setStyle(emailLabelTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        passwordLabelTxtField.setStyle(passwordLabelTxtField.getStyle() + ";-fx-border-color:  #84817a;");

        if (!isValidFirstName) {
            firstNameTxtField.setStyle(firstNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidLastName) {
            lastNameTxtField.setStyle(lastNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidUserName) {
            userNameTxtField.setStyle(userIdTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            emailLabelTxtField.setStyle(emailLabelTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPassword) {
            passwordLabelTxtField.setStyle(passwordLabelTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidFirstName && isValidLastName && isValidUserName && isValidEmail && isValidPassword) {
            UserDto userDto = new UserDto(userId, firstName, lastName, userName, emai, password);

            boolean isSaved = userModel.saveUser(userDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User saved...").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save User...").show();
            }
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) throws SQLException{
        String userId = userIdTxtField.getText();
        String firstName = firstNameTxtField.getText();
        String lastName = lastNameTxtField.getText();
        String userName = userNameTxtField.getText();
        String emai = emailLabelTxtField.getText();
        String password = passwordLabelTxtField.getText();

        String firstNamePattern = "^[A-Za-z ]+$";
        String lastNamePattern = "^[A-Za-z ]+$";
        String userNamePattern = "^[A-Za-z.,@_ ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

        boolean isValidFirstName = firstName.matches(firstNamePattern);
        boolean isValidLastName = lastName.matches(lastNamePattern);
        boolean isValidUserName = userName.matches(userNamePattern);
        boolean isValidEmail = emai.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        firstNameTxtField.setStyle(firstNameTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        lastNameTxtField.setStyle(lastNameTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        userNameTxtField.setStyle(userNameTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        emailLabelTxtField.setStyle(emailLabelTxtField.getStyle() + ";-fx-border-color:  #84817a;");
        passwordLabelTxtField.setStyle(passwordLabelTxtField.getStyle() + ";-fx-border-color:  #84817a;");

        if (!isValidFirstName) {
            firstNameTxtField.setStyle(firstNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidLastName) {
            lastNameTxtField.setStyle(lastNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidUserName) {
            userNameTxtField.setStyle(userIdTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            emailLabelTxtField.setStyle(emailLabelTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPassword) {
            passwordLabelTxtField.setStyle(passwordLabelTxtField.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidFirstName && isValidLastName && isValidUserName && isValidEmail && isValidPassword) {
            UserDto userDto = new UserDto(userId, firstName, lastName, userName, emai, password);

            boolean isUpdated = userModel.updateUser(userDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "User updated...").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update User...").show();
            }
        }
    }

    @FXML
    void userViewTableOnClick(MouseEvent event) {
        UserTM selectedItem = userViewTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            userIdTxtField.setText(selectedItem.getUserId());
            firstNameTxtField.setText(selectedItem.getFirstName());
            lastNameTxtField.setText(selectedItem.getLastName());
            userNameTxtField.setText(selectedItem.getUserName());
            emailLabelTxtField.setText(selectedItem.getEmail());
            passwordLabelTxtField.setText(selectedItem.getPassword());

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }
}

package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.BookingDto;
import com.example.momentous.momentous_finalproject.dto.BookingServiceDto;
import com.example.momentous.momentous_finalproject.dto.CustomerDto;
import com.example.momentous.momentous_finalproject.dto.ServiceDto;
import com.example.momentous.momentous_finalproject.dto.tm.BookingTM;
import com.example.momentous.momentous_finalproject.model.BookingModel;
import com.example.momentous.momentous_finalproject.model.CustomerModel;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class BookingViewController implements Initializable {

    @FXML
    private TableColumn<?, ?> actionColumn;

    @FXML
    private JFXButton addToBookingButton;

    @FXML
    private TableColumn<BookingTM, Date> bookingDateColumn;

    @FXML
    private TableColumn<BookingTM, String> bookingIdColumn;

    @FXML
    private Label bookingIdInfo;

    @FXML
    private Label bookingIdLabel;

    @FXML
    private TableView<BookingTM> bookingViewTable;

    @FXML
    private TableColumn<BookingTM, Integer> capacityColumn;

    @FXML
    private Label capacityLabel;

    @FXML
    private TextField capacityTxtField;

    @FXML
    private JFXButton completeBookingButton;

    @FXML
    private TableColumn<BookingTM, String> customerIdColumn;

    @FXML
    private JFXComboBox<String> customerIdComBox;

    @FXML
    private Label customerIdLabel;

    @FXML
    private Label customerNameInfo;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label dateInfo;

    @FXML
    private Label dateLabel;

    @FXML
    private JFXButton resetButton;

    @FXML
    private TableColumn<BookingTM, String> serviceIdColumn;

    @FXML
    private Label serviceTypeInfo;

    @FXML
    private Label serviceTypeLabel;

    @FXML
    private JFXComboBox<String> serviceIdComBox;


    @FXML
    private Label serviceIdLabel;

    @FXML
    private TableColumn<BookingTM, String> venueColumn;

    @FXML
    private Label venueLabel;

    @FXML
    private TextField venueTxtField;

    @FXML
    private JFXButton detailButton;

    private final BookingModel bookingModel = new BookingModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ServiceModel serviceModel = new ServiceModel();

    private final ObservableList<BookingTM> bookingTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    // polymor abstrac
        setCellValues();

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCellValues() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

        bookingViewTable.setItems(bookingTMS);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        bookingIdInfo.setText(bookingModel.getNextBookingId());
        dateInfo.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadServiceIds();

        customerIdComBox.getSelectionModel().clearSelection();
        serviceIdComBox.getSelectionModel().clearSelection();
        customerNameInfo.setText("");
        serviceTypeInfo.setText("");
        capacityTxtField.setText("");
        venueTxtField.setText("");

        bookingTMS.clear();
        bookingViewTable.refresh();
    }

    private void loadServiceIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> serviceIds = serviceModel.getAllServiceIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(serviceIds);
        serviceIdComBox.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        customerIdComBox.setItems(observableList);
    }

    @FXML
    void addToBookingButtonOnAction(ActionEvent event) {
        String selectedCustomerId = customerIdComBox.getValue();

        if (selectedCustomerId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select customer").show();
            return;
        }
        String selectedServiceId = serviceIdComBox.getValue();

        if (selectedServiceId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select service").show();
            return;
        }

        String capacityPattern = "^[0-9]+$";

        boolean isValidCapacity = capacityTxtField.getText().matches(capacityPattern);

        if (!isValidCapacity){
            new Alert(Alert.AlertType.ERROR,"Invalid capacity").show();
            return;
        }

        String venuePattern = "^[A-Za-z ]+$";

        boolean isValidVenue = venueTxtField.getText().matches(venuePattern);

        if (!isValidVenue){
            new Alert(Alert.AlertType.ERROR,"Invalid venue").show();
            return;
        }

        String customerName = customerNameInfo.getText();
        int capacity = Integer.parseInt(capacityTxtField.getText());
        String serviceType = serviceTypeLabel.getText();
        String venue = venueTxtField.getText();
        java.sql.Date date = java.sql.Date.valueOf((dateInfo.getText()));

        Button button = new Button("Remove");

        BookingTM newBookingTM = new BookingTM(
                bookingIdInfo.getText(),
                selectedCustomerId,
                selectedServiceId,
                capacity,
                venue,
                date,
                button
        );

        button.setOnAction(actionEvent -> {
            bookingTMS.remove(newBookingTM);
            bookingViewTable.refresh();
        });

        bookingTMS.add(newBookingTM);
    }

    @FXML
    void bookingViewTableOnClick(MouseEvent event) {

    }

    @FXML
    void completeBookingButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (bookingViewTable.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to table").show();
            return;
        }
        if (customerIdComBox.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place booking").show();
            return;
        }

        String bookingId = bookingIdInfo.getText();
        int capacity = Integer.parseInt(capacityTxtField.getText());
        String venue = venueTxtField.getText();
        java.sql.Date dateOfBooking = java.sql.Date.valueOf(dateInfo.getText());
        String customerId = customerIdComBox.getValue();
        String serviceId = serviceIdComBox.getValue();

        ArrayList<BookingServiceDto> bookingServiceDtos = new ArrayList<>();

        for (BookingTM bookingTM : bookingTMS) {
            BookingServiceDto bookingServiceDto = new BookingServiceDto(
                    bookingId,
                    bookingTM.getServiceId(),
                    dateOfBooking
            );
            bookingServiceDtos.add(bookingServiceDto);
        }

        BookingDto bookingDto = new BookingDto(
                bookingId,
                customerId,
                capacity,
                venue,
                dateOfBooking,
                bookingServiceDtos
        );

        boolean isSaved = bookingModel.saveBooking(bookingDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Booking saved").show();

            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Booking fail").show();
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void customerIdComBox(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedCustomerId = customerIdComBox.getSelectionModel().getSelectedItem();
        CustomerDto customerDto = customerModel.findByCustomerId(selectedCustomerId);

        if (customerDto != null) {

            customerNameInfo.setText(customerDto.concatName());
        }
    }

    @FXML
    void serviceIdComBoxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedServiceId = serviceIdComBox.getSelectionModel().getSelectedItem();
        ServiceDto serviceDto = serviceModel.findById(selectedServiceId);

        if (serviceDto != null) {

            serviceTypeInfo.setText(serviceDto.getServiceType());
        }
    }

    @FXML
    void detailButtonOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/BookingServiceReport.jrxml"));

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
}

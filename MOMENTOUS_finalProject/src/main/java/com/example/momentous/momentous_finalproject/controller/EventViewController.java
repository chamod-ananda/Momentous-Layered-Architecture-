package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.db.DBConnection;
import com.example.momentous.momentous_finalproject.dto.EventDto;
import com.example.momentous.momentous_finalproject.dto.EventSupplierDto;
import com.example.momentous.momentous_finalproject.dto.ItemDto;
import com.example.momentous.momentous_finalproject.dto.SupplierDto;
import com.example.momentous.momentous_finalproject.dto.tm.EventTM;
import com.example.momentous.momentous_finalproject.model.BookingModel;
import com.example.momentous.momentous_finalproject.model.EventModel;
import com.example.momentous.momentous_finalproject.model.ItemModel;
import com.example.momentous.momentous_finalproject.model.SupplierModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EventViewController implements Initializable {

    @FXML
    private TableColumn<?, ?> actionColumn;

    @FXML
    private TableColumn<?, ?> bookingIdColumn;

    @FXML
    private JFXComboBox<String> bookingIdComBox;

    @FXML
    private TableColumn<EventTM, Double> budgetColumn;

    @FXML
    private TextField budgetTxtField;

    @FXML
    private JFXButton comEventSetupButton;

    @FXML
    private TableColumn<EventTM, Date> dateColumn;

    @FXML
    private TextField dateTxtField;

    @FXML
    private TableColumn<EventTM, String> eventIdColumn;

    @FXML
    private Label eventIdLabel;

    @FXML
    private TableColumn<EventTM, String> eventNameColumn;

    @FXML
    private TextField eventNameTxtField;

    @FXML
    private TableColumn<EventTM, String> eventTypeColumn;

    @FXML
    private JFXComboBox<String> eventTypeComBox;

    @FXML
    private TableView<EventTM> eventViewTable;

    @FXML
    private JFXComboBox<String> itemIdComBox;

    @FXML
    private TableColumn<EventTM, String> itemNameColumn;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private TableColumn<EventTM, Integer> qtyColumn;

    @FXML
    private Label qtyOnHandLabel;

    @FXML
    private TextField qtyTxtField;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TableColumn<EventTM, String> supIdColumn;

    @FXML
    private JFXComboBox<String> supplierIdComBox;

    @FXML
    private Label supplierNameLabel;

    @FXML
    private JFXButton resetButton;

    @FXML
    private TableColumn<EventTM, Double> totalColumn;

    @FXML
    private TableColumn<EventTM, Double> unitPriceColumn;

    @FXML
    private TableColumn<EventTM, String> venueColumn;

    @FXML
    private TextField venueTxtField;

    @FXML
    private JFXButton detailButton;

    @FXML
    private DatePicker datePicker;

    private static final String VENUE_PATTERN = "^[A-Za-z0-9 ,.-]+$";
    private static final String QTY_PATTERN = "^[1-9][0-9]*$";
    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String PRICE_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";

    private final EventModel eventModel = new EventModel();
    private final BookingModel bookingModel = new BookingModel();
    private final ItemModel itemModel = new ItemModel();
    private final SupplierModel supplierModel = new SupplierModel();

    private final ObservableList<EventTM> eventTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        eventTypeComBox.getItems().addAll("Wedding", "Conference", "Birthday Party", "Corporate Event", "Workshop", "Concert", "Exhibition", "Seminar", "Fundraiser", "Festival", "Gala", "Product Launch", "Retreat", "Trade Show", "Networking Event", "Training Session");

        try{
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCellValues(){
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        eventTypeColumn.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<>("venue"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        supIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));

        eventViewTable.setItems(eventTMS);
    }

    private void refreshPage() throws Exception {
        eventIdLabel.setText(eventModel.getNextEventId());

        loadBookingIds();
        loadSupplierIds();
        //loadItemIds();

        eventTypeComBox.getSelectionModel().clearSelection();
        bookingIdComBox.getSelectionModel().clearSelection();
        itemIdComBox.getSelectionModel().clearSelection();
        supplierIdComBox.getSelectionModel().clearSelection();
        priceLabel.setText("");
        itemNameLabel.setText("");
        supplierNameLabel.setText("");
        qtyOnHandLabel.setText("");
        venueTxtField.setText("");
        datePicker.setValue(null);
        budgetTxtField.setText("");
        eventNameTxtField.setText("");
        qtyTxtField.setText("");

        eventTMS.clear();
        eventViewTable.refresh();
    }

    @FXML
    void eventViewTableOnClick(MouseEvent event) {

    }

    @FXML
    void resetButtonOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        String selectedBookingId = bookingIdComBox.getValue();

        if (selectedBookingId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a booking").show();
            return;
        }

        String selectedSupplierId = supplierIdComBox.getValue();

        if (selectedSupplierId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a supplier").show();
            return;
        }

        String selectedItemId = itemIdComBox.getValue();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select an item").show();
            return;
        }

        boolean isValidName = eventNameTxtField.getText().matches(NAME_PATTERN);

        if (!isValidName){
            eventNameTxtField.setStyle(eventNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidQty = qtyTxtField.getText().matches(QTY_PATTERN);

        if (!isValidQty){
            qtyTxtField.setStyle(qtyTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidVenue = venueTxtField.getText().matches(VENUE_PATTERN);

        if (!isValidVenue){
            venueTxtField.setStyle(venueTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidBudget = budgetTxtField.getText().matches(PRICE_PATTERN);

        if (!isValidBudget){
            budgetTxtField.setStyle(budgetTxtField.getStyle() + ";-fx-border-color: red;");
        }

        String eventType = eventTypeComBox.getValue();
        int quantityOnHand = Integer.parseInt(qtyOnHandLabel.getText());
        int quantity = Integer.parseInt(qtyTxtField.getText());
        Double budget = Double.valueOf(budgetTxtField.getText());
        String eventName = eventNameTxtField.getText();
        String venue = venueTxtField.getText();
        Date date = Date.valueOf(datePicker.getValue());
        String itemName = itemNameLabel.getText();
        Double price = Double.valueOf(priceLabel.getText());

        if (quantityOnHand < quantity) {
            new Alert(Alert.AlertType.ERROR, "Oops! Not enough items").show();
            return;
        }

        qtyTxtField.setText("");

        double unitPrice = Double.parseDouble(priceLabel.getText());
        double totalPrice = price * quantity;

        for (EventTM eventTM : eventTMS) {
            if (eventTM.getItemId().equals(selectedItemId)) {
                int newQuantity = eventTM.getQuantity() + quantity;
                eventTM.setQuantity(newQuantity);
                eventTM.setTotalPrice(unitPrice * newQuantity);

                eventViewTable.refresh();
                return;
            }
        }

        Button btn = new Button("Remove");

        EventTM newEventTm = new EventTM(
                eventIdLabel.getText(),
                selectedBookingId,
                selectedItemId,
                eventType,
                eventName,
                budget,
                venue,
                date,
                selectedSupplierId,
                itemName,
                quantity,
                unitPrice,
                totalPrice,
                btn
        );

        btn.setOnAction(actionEvent -> {
            eventTMS.remove(newEventTm);
            eventViewTable.refresh();
        });

        eventTMS.add(newEventTm);
    }

    private void loadSupplierIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierIds = supplierModel.getAllSupplierIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        supplierIdComBox.setItems(observableList);
    }

    private void loadBookingIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> bookingIds = bookingModel.getAllBookingIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookingIds);
        bookingIdComBox.setItems(observableList);
    }

    @FXML
    void itemIdComBoxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String selectedItemId = itemIdComBox.getSelectionModel().getSelectedItem();
        ItemDto itemDto = itemModel.findById(selectedItemId);

        if (itemDto != null) {
            itemNameLabel.setText(itemDto.getItemName());
            priceLabel.setText(String.valueOf(itemDto.getCost()));
            qtyOnHandLabel.setText(String.valueOf(itemDto.getQuantity()));
        }
    }

    @FXML
    void supplierIdComBoxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException{
        String selectedSupplierId = supplierIdComBox.getSelectionModel().getSelectedItem();
        SupplierDto supplierDto = supplierModel.findById(selectedSupplierId);

        if (supplierDto != null) {
            supplierNameLabel.setText(supplierDto.getSupplierName());
            loadItemIds(selectedSupplierId);
        }
    }

    private void loadItemIds(String supplierId) throws SQLException, ClassNotFoundException {
        ArrayList<String> itemIds = itemModel.getAllItemIds(supplierId);

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        itemIdComBox.setItems(observableList);
    }

    @FXML
    void comEventSetupButtonOnAction(ActionEvent event) throws Exception {
        if (eventViewTable.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to the table..!").show();
            return;
        }
        if (bookingIdComBox.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a booking to place the event..!").show();
            return;
        }

        String eventId = eventIdLabel.getText();
        String eventType = eventTypeComBox.getValue();
        Double budget = Double.valueOf(budgetTxtField.getText());
        String eventName = eventNameTxtField.getText();
        String venue = venueTxtField.getText();
        java.sql.Date date = java.sql.Date.valueOf(datePicker.getValue());

        ArrayList<EventSupplierDto> eventSupplierDtos = new ArrayList<>();

        for (EventTM eventTM : eventTMS) {
            if (eventTM.getSupplierId() == null || eventTM.getItemId() == null || eventTM.getQuantity() <= 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid data in the event table. Please check the inputs.").show();
                return;
            }

            EventSupplierDto eventSupplierDto = new EventSupplierDto(
                    eventId,
                    eventTM.getSupplierId(),
                    eventTM.getItemId(),
                    eventTM.getQuantity(),
                    eventTM.getTotalPrice()
            );
            eventSupplierDtos.add(eventSupplierDto);
        }

        EventDto eventDto = new EventDto(
                eventId,
                bookingIdComBox.getValue(),
                eventType,
                eventName,
                budget,
                venue,
                date,
                eventSupplierDtos
        );

        boolean isSaved = eventModel.saveEvent(eventDto);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Event saved successfully..!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save event. Please check item details.").show();
        }
    }

    @FXML
    void detailButtonOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/eventSupplierReport.jrxml"));

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

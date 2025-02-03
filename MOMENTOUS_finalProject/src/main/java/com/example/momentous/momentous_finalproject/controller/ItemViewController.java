package com.example.momentous.momentous_finalproject.controller;

import com.example.momentous.momentous_finalproject.dto.ItemDto;
import com.example.momentous.momentous_finalproject.dto.SupplierDto;
import com.example.momentous.momentous_finalproject.dto.tm.ItemTM;
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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemViewController implements Initializable {

    @FXML
    private AnchorPane backPane1;

    @FXML
    private TableColumn<ItemTM, Double> costColumn;

    @FXML
    private Label costLabel;

    @FXML
    private TextField costTxtField;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private TableColumn<ItemTM, String> descriptionColumn;

    @FXML
    private Label descriptionLabel;

    @FXML
    private TextField descriptionTxtField;

    @FXML
    private TableColumn<ItemTM, String> itemIdColumn;

    @FXML
    private Label itemIdLabel;

    @FXML
    private Label itemIdLabelInfo;

    @FXML
    private TableColumn<ItemTM, String> itemNameColumn;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextField itemNameTxtField;

    @FXML
    private TableView<ItemTM> itemViewTable;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabelInfo;

    @FXML
    private TableColumn<ItemTM, Integer> qtyColumn;

    @FXML
    private Label qtyLabel;

    @FXML
    private TextField qtyTxtField;

    @FXML
    private JFXButton resetButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXComboBox<String> supIdComBox;

    @FXML
    private TableColumn<ItemTM, String> supplierIdColumn;

    @FXML
    private Label supplierIdLabel;

    @FXML
    private JFXButton updateButton;

    private final ItemModel itemModel = new ItemModel();
    private final SupplierModel supplierModel = new SupplierModel();

    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    private static final String DESCRIPTION_PATTERN = "^[A-Za-z0-9 ,.-]+$";
    private static final String COST_PATTERN = "^[0-9]+(\\.[0-9]{1,2})?$";
    private static final String QTY_PATTERN = "^[1-9][0-9]*$";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
    }

    private void setCellValues() {
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        supplierIdColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

        try {
            loadSupplierIds();
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() {
        try {
            refreshTable();
            String itemId = itemModel.getNextItemId();
            itemIdLabelInfo.setText(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemNameTxtField.setText("");
        descriptionTxtField.setText("");
        costTxtField.setText("");
        qtyTxtField.setText("");

        saveButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void refreshTable() throws Exception, ClassNotFoundException {
        ArrayList<ItemDto> itemDtos = itemModel.getAllItems();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for (ItemDto itemDto : itemDtos) {
            ItemTM itemTM = new ItemTM(
                    itemDto.getItemId(),
                    itemDto.getItemName(),
                    itemDto.getItemDescription(),
                    itemDto.getCost(),
                    itemDto.getQuantity(),
                    itemDto.getSupplierId()
            );
            itemTMS.add(itemTM);
        }
        itemViewTable.setItems(itemTMS);
    }

    @FXML
    void deleteButtonOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = itemIdLabelInfo.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = itemModel.deleteItem(itemId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Item can't deleted").show();
            }
        }
    }

    @FXML
    void itemViewTableOnClick(MouseEvent event) {
        ItemTM selectedItem = itemViewTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            itemIdLabelInfo.setText(selectedItem.getItemId());
            itemNameTxtField.setText(String.valueOf(selectedItem.getItemName()));
            descriptionTxtField.setText(selectedItem.getItemDescription());
            costTxtField.setText(String.valueOf(selectedItem.getCost()));
            qtyTxtField.setText(String.valueOf(selectedItem.getQuantity()));
            supIdComBox.setValue(selectedItem.getSupplierId());

            saveButton.setDisable(true);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    @FXML
    void resetButtonOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ItemDto itemDto = getFieldValues();

        if (itemDto != null) {
            try {
                boolean isSaved = itemModel.saveItem(itemDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Item saved successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item can't saved").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        ItemDto itemDto = getFieldValues();

        if (itemDto != null) {
            try {
                boolean isUpdate = itemModel.updateItem(itemDto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Item updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item can't updated").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ItemDto getFieldValues() {
        String itemId = itemIdLabelInfo.getText();
        String itemname = itemNameTxtField.getText();
        String description = descriptionTxtField.getText();
        Double cost = Double.valueOf(costTxtField.getText());
        int quantity = Integer.parseInt(qtyTxtField.getText());
        String supplierId = supIdComBox.getValue();

        boolean isValidFields = validItemFields();

        if (isValidFields) {
            return new ItemDto(itemId, itemname, description, cost, quantity, supplierId);
        }
        return null;
    }

    private boolean validItemFields() {
        boolean isValidCost = costTxtField.getText().matches(COST_PATTERN);

        if (!isValidCost) {
            costTxtField.setStyle(costTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidQuantity = qtyTxtField.getText().matches(QTY_PATTERN);

        if (!isValidQuantity) {
            qtyTxtField.setStyle(qtyTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidName = itemNameTxtField.getText().matches(NAME_PATTERN);

        if (!isValidName) {
            itemNameTxtField.setStyle(itemNameTxtField.getStyle() + ";-fx-border-color: red;");
        }

        boolean isValidDescription = descriptionTxtField.getText().matches(DESCRIPTION_PATTERN);

        if (!isValidDescription) {
            descriptionTxtField.setStyle(descriptionTxtField.getStyle() + ";-fx-border-color: red;");
        }
        return isValidCost && isValidQuantity && isValidName && isValidDescription;
    }

    @FXML
    void supIdComBoxOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedSupplierId = supIdComBox.getSelectionModel().getSelectedItem();
        SupplierDto supplierDto = supplierModel.findById(selectedSupplierId);

        if (supplierDto != null) {
            nameLabel.setText(supplierDto.getSupplierName());
        }
    }

    private void loadSupplierIds() throws SQLException, ClassNotFoundException {
        ArrayList<String> supplierIds = supplierModel.getAllSupplierIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        supIdComBox.setItems(observableList);
    }
}

package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.dto.EventSupplierDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventSupplierModel {
    private final ItemModel itemModel = new ItemModel();

    public boolean saveEventSuppliersList(ArrayList<EventSupplierDto> eventSupplierDtos) throws SQLException, ClassNotFoundException {
        for (EventSupplierDto eventSupplierDto : eventSupplierDtos) {
            // Save the event supplier
            boolean isEventSupplierSaved = saveEventSuppliers(eventSupplierDto);
            if (!isEventSupplierSaved) { // If saving fails, return false
                System.out.println("Failed to save event supplier: " + eventSupplierDto);
                return false;
            }

            // Update the item quantity
            boolean isItemUpdated = itemModel.reduceQty(eventSupplierDto);
            if (!isItemUpdated) { // If updating item fails, return false
                System.out.println("Failed to update item quantity for: " + eventSupplierDto);
                return false;
            }
        }

        // All operations succeeded
        return true;
    }


    private boolean saveEventSuppliers(EventSupplierDto eventSupplierDto) throws SQLException {
        return CrudUtil.execute(
                "insert into EventSupplier values (?,?,?,?)",
                eventSupplierDto.getEventId(),
                eventSupplierDto.getSupplierId(),
                eventSupplierDto.getItemQuantity(),
                eventSupplierDto.getTotalPrice()
        );
    }
}

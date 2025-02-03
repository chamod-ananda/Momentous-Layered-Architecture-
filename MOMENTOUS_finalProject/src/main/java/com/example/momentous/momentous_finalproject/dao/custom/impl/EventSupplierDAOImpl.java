package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.EventSupplierDAO;
import com.example.momentous.momentous_finalproject.dao.custom.ItemDAO;
import com.example.momentous.momentous_finalproject.dto.EventSupplierDto;
import com.example.momentous.momentous_finalproject.entity.EventSupplier;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventSupplierDAOImpl implements EventSupplierDAO {
    ItemDAO itemDAO = new ItemDAOImpl();

    public boolean saveEventSuppliersList(ArrayList<EventSupplier> eventSuppliersList) throws SQLException, ClassNotFoundException {
        for (EventSupplier eventSupplier : eventSuppliersList) {
            // Save the event supplier
            boolean isEventSupplierSaved = saveEventSuppliers(eventSupplier);
            if (!isEventSupplierSaved) { // If saving fails, return false
                System.out.println("Failed to save event supplier: " + eventSupplier);
                return false;
            }

            // Update the item quantity
            boolean isItemUpdated = itemDAO.reduceQty(eventSupplier);
            if (!isItemUpdated) { // If updating item fails, return false
                System.out.println("Failed to update item quantity for: " + eventSupplier);
                return false;
            }
        }

        // All operations succeeded
        return true;
    }

    public boolean saveEventSuppliers(EventSupplier eventSupplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO EventSupplier (event_id, supplier_id, itemQty, price) VALUES (?,?,?,?)",
                eventSupplier.getEventId(),
                eventSupplier.getSupplierId(),
                eventSupplier.getItemQuantity(),
                eventSupplier.getTotalPrice()
        );
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<EventSupplier> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(EventSupplier entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(EventSupplier entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public EventSupplier findById(String selectedCustomerId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
